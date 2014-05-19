package model;


import exceptions.NoRegisterAddressException;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 02.05.14
 * Time: 10:23
 * To change this template use File | Settings | File Templates.
 */
public class Register {

    //***********************************
    //Register Adressen
    public static final int REG_MAX = 0xFF;
    //Bank 0 Adressen
    public static final int INDF = 0x00;
    public static final int TMR0 = 0x01;
    public static final int PCL = 0x02;
    public static final int STATUS = 0x03;
    public static final int FSR = 0x04;
    public static final int PORTA = 0x05;
    public static final int PORTB = 0x06;
    public static final int UNIMPLEMENTED = 0x07;
    public static final int EEDATA = 0x08;
    public static final int EEADR = 0x09;
    public static final int PCLATH = 0x0A;
    public static final int INTCON = 0x0B;
    //Bank 1 Adressen
    public static final int OFFSET = 0x80;
    public static final int OPTION_REG = 0x81;
    public static final int TRISA = 0x85;
    public static final int TRISB = 0x86;
    public static final int EECON1 = 0x88;
    //GPR Adressen
    public static final int GPR_START = 0x0C;
    public static final int GPR_END = 0x2F;
    //***********************************

    private int cycles;
    private int w; //W-Register
    private int[] reg;
    private int latchPortA;
    private int latchPortB;

    public Register() {
        cycles = 0;
        reg = new int[0xFF];

        valueOnReset();
    }

    //Power On Reset bei laden einer Datei
    public void valueOnReset(){
        try {
            w = 0;
            //Register Reset
            reg = new int[0xFF];
            System.out.println("RegisterArray erstellt, Wert von PCL: " + reg[PCL]);
            // Bank 0
            setRegValue(PCL, 0x00);
            setRegValue(STATUS, 0x18);
            setRegValue(PCLATH, 0x00);
            setRegValue(INTCON, 0x00);

            // Bank 1
            setRegValue(OPTION_REG, 0xFF);
            setRegValue(PCL + OFFSET, 0x00);
            setRegValue(STATUS + OFFSET, 0x18);
            setRegValue(TRISA, 0x1F);
            setRegValue(TRISB, 0xFF);
            setRegValue(EECON1, 0x00);
            setRegValue(PCLATH + OFFSET, 0x00);
            setRegValue(INTCON + OFFSET, 0x00);
        } catch (NoRegisterAddressException e) {
            e.printStackTrace();
        }
    }

    //Schreibt in Adresse des Registers
    public void setRegValue(int address, int value) throws NoRegisterAddressException{
        //Adressüberprüfung
        if (address > REG_MAX) {
            throw new NoRegisterAddressException(address);
        }

        if (isGPRAddr(address)) {
            //writeGPR(address, value);
        } else {
            writeSFR(address, value);
        }
    }


    public void writeThough(int addr, int value) throws NoRegisterAddressException {
        switch (addr) {
            case PORTA:
                reg[PORTA] = value;
                latchPortA = value;
                //TODO : Ausgabe
                break;

            case PORTB:
                reg[PORTB] = value;
                latchPortB = value;
                //TODO : Ausgabe
                break;
            default:
                setRegValue(addr, value);
        }
    }

    //Überprüft ob es sich um GPR (General Purpose Registers) Adresse handelt
    public boolean isGPRAddr(int addr) {
        if (((addr & 0x007F) >= GPR_START) && (addr & 0x007F) <= GPR_END) {
            return true;
        }
        return false;
    }

    private void writeSFR(int addr, int value) {
        // Bank auswählen
        addr = selectBank(addr);
        // Wert schreiben
        switch (addr) {
            //INDF spiegeln
            case INDF:
            case INDF + OFFSET:
                //indirekte Adressierung
                //TODO : GPR Addressen schreiben
                //writeGPR(reg[FSR], value);
                break;

            //PCL spiegeln
            case PCL:
            case PCL + OFFSET:
                reg[PCL] = value;
                reg[PCL + OFFSET] = value;
                break;

            //STATUS spiegeln
            case STATUS:
            case STATUS + OFFSET:
                //value = value & 0xE7; //0b1110 0111 --> TO & PD nur lesbar
                reg[STATUS] = value;
                reg[STATUS + OFFSET] = value;
                break;

            //FSR spiegeln
            case FSR:
            case FSR + OFFSET:
                reg[FSR] = value;
                reg[FSR + OFFSET] = value;
                break;

            //PORT A Ausgänge schreiben (TRIS A Reg = 0 --> ausgang)
            case PORTA:
                latchPortA = value;
                reg[PORTA] = (value & 0x1F) & ~reg[TRISA];
                break;

            //PORT B Ausgänge schreiben (TRIS B Reg = 0 --> ausgang)
            case PORTB:
                latchPortB = value;
                reg[PORTB] = value & ~reg[TRISB];
                break;

            case TRISA:
                value = value & 0x1F;
                reg[TRISA] = value;
                reg[PORTA] = latchPortA & ~reg[TRISA];
                //TODO : GUI benachrichtigen
                break;

            case TRISB:
                reg[TRISB] = value;
                reg[PORTB] = latchPortB & ~reg[TRISB];
                //TODO : GUI benachrichtigen
                break;

            //PCLATH spiegeln
            case PCLATH:
            case PCLATH + OFFSET:
                reg[PCLATH] = value;
                reg[PCLATH + OFFSET] = value;
                break;

            //INTCON spiegeln
            case INTCON:
            case INTCON + OFFSET:
                reg[INTCON] = value;
                reg[INTCON + OFFSET] = value;
                break;

            default:
                //nicht spiegeln
                reg[addr] = value;
        }
        //TODO : GUI benachrichtigen
    }

    //Addresse an gewählte Bank anpassen
    private int selectBank(int address) {
        int bankMask = ((reg[STATUS] & 0x20) << 2);
        return bankMask | address;
    }

    //Gibt den PC zurück
    public int getPC() {
        return (reg[PCLATH] << 8) + reg[PCL];
    }

    //Setzt den PC auf übergebenen Wert
    public void setPC(int pc) throws NoRegisterAddressException {
        //Oberen 5 bit im PCLATH speichern
        setRegValue(PCLATH, (pc & 0x1F00) >> 8); //0b0001 1111 0000 0000 >> 0b0000 0000 0001 1111
        //Unteren 8 bit im PCL speichern
        setRegValue(PCL, pc & 0x00FF);    //0b0000 0000 1111 1111
    }

    //Inkrementiert den PC
    public void incPC() throws NoRegisterAddressException {
        //System.out.println("PC++");
        int pc = getPC();
        setPC(++pc);
    }

    //Setzt W-Register auf gegebenen 8-bit Wert
    public void setW(int value) {
        this.w = value & 0x00FF;
    }

    //Gibt den 8-bit Wert des W-Registers zurück
    public int getW() {
        return this.w & 0x00FF;
    }

    //Gibt den Wert einer Addresse zurück
    public int getRegValue(int addr) {
        return reg[addr];
    }

    public void checkDC(int value, int value2, boolean add) throws NoRegisterAddressException {
        //If add=true -> Addition
        if (add) {
            if((value & 0x000F) + value2 > 0x000F){
                    setRegValue(STATUS, 0x0002);
            }
        } else {
            if((value & 0x000F) - value2 < 0){
                setRegValue(STATUS, 0x0002);
            }
        }
    }

    //Prüft ob ein Bit Carry vorliegt und setzt je nachdem das Carry Bit
    public void checkCarry(int f, int w, boolean add) throws NoRegisterAddressException {
        //If add=true -> Addition
        int status = getRegValue(STATUS);
        if (add==true) {
            if(f + w > 0xFF){
                setBit(status, 0);
                setRegValue(STATUS, status);
            }
        } else {
            if(f - w < 0){
                clearBit(status, 0);
                setRegValue(STATUS, status);
            }
        }
    }

    //Inkrementiert Cycles
    public void incCycles() {
        this.cycles++;
    }

    //Testet bei einer Operation ob der Wert 0 ist und setzt je nachdem das Zero Bit
    public void checkZeroBit(int value) throws NoRegisterAddressException {
        int status = getRegValue(STATUS);
        if (value == 0){
            status = setBit(status, 2);
        } else {
            status = clearBit(status, 2);
        }
        setRegValue(STATUS, status);
    }

    //Gibt Zero Bit als booleschen Wert zurück
    public boolean getZeroBit() {
        return testBit(getRegValue(STATUS), 2);
    }

    //Setzt das Bit auf 1
    static int setBit( int n, int pos ) {
        return n | (1 << pos);
    }

    //Setzt das Bit auf 0
    static int clearBit(int n, int pos) {
        return n & ~(1 << pos);
    }

    //Invertiert das angegebene Bit
    static int flipBit( int n, int pos ) {
        return n ^ (1 << pos);
    }

    //Testet ein Bit und gibt booleschen Wert zurück
    public boolean testBit(int instruction, int pos) {
        return (instruction & 1<<pos) != 0;
    }

}