package model;

import exceptions.IllegalCarryOperationException;

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

    public Register() {
        init();
    }

    private void init() {
        cycles = 0;
        reg = new int[0xFF];
        valueOnReset();
    }


    //Power On Reset bei laden einer Datei
    public void valueOnReset(){
        w = 0;
        //Register Reset
        reg = new int[0xFF];
        System.out.println("RegisterArray erstellt, Wert von PCL: " + reg[PCL]);
        // Bank 0
        write(PCL, 0x00);
        write(STATUS, 0x18);
        write(PCLATH, 0x00);
        write(INTCON, 0x00);

        // Bank 1
        write(OPTION_REG, 0xFF);
        write(PCL + OFFSET, 0x00);
        write(STATUS + OFFSET, 0x18);
        write(TRISA, 0x1F);
        write(TRISB, 0xFF);
        write(EECON1, 0x00);
        write(PCLATH + OFFSET, 0x00);
        write(INTCON + OFFSET, 0x00);
    }

    //Schreibt in Adresse des Registers
    public void write(int addr, int value) {
        //Adressüberprüfung
        if (addr > REG_MAX) {
            System.out.println("Ungültige Adresse: " + addr);
        }
        reg[addr] = value;
    }

    //Überprüft ob es sich um GPR (General Purpose Registers) Adresse handelt
    public boolean isGPRAddr(int addr) {
        if (((addr & 0x007F) >= GPR_START) && (addr & 0x007F) <= GPR_END) {
            return true;
        }
        return false;
    }

    public int getPC() {
        return (reg[PCLATH] << 8) + reg[PCL];
    }

    public void setPC(int pc) {
        //Oberen 5 bit im PCLATH speichern
        write(PCLATH, (pc & 0x1F00) >> 8); //0b0001 1111 0000 0000
        //Unteren 8 bit im PCL speichern
        write(PCL, pc & 0x00FF);    //0b0000 0000 1111 1111
    }

    public void incPC() {
        System.out.println("PC++");
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

    public int getRegValue(int addr) {
        return reg[addr];
    }

    public void checkDC(int value, int value2, boolean add) {
        //If sub=true -> Subtraction
        if (add) {
            if((value & 0x000F) + value2 > 0x000F){
                    write(STATUS, 0x0002);
            }
        } else {
            if((value & 0x000F) - value2 < 0){
                write(STATUS, 0x0002);
            }
        }
    }

    public int checkCarry(int f, int w, boolean add) throws IllegalCarryOperationException {
        //If sub=true -> Subtraction
        int value = f & 0x00FF;
        if (add) {
            if(value + w > 0x00FF){
                write(STATUS, 0x0002);
                return ((value + w) - 0x00FF);
            }
        } else {
            if(value - w < 0){
                write(STATUS, 0x0002);
                return (value - w) + 0x00FF;
            }
        }
        throw new IllegalCarryOperationException(f, w);
    }

    public void incCycles() {
        this.cycles++;
    }


}