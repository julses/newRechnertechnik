package controller;

import exceptions.NoRegisterAddressException;
import static controller.Register.RegisterAdresses.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 20.04.14
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class Instructions {

    private Stack stack;
    private Register register;

    public Instructions(Register register, Stack stack) {
        this.register = register;
        this.stack = stack;
    }


    /*Add the contents of the W register with
     *register 'f'. If 'd' is 0 the result is stored
     *in the W register. If 'd' is 1 the result is
     *stored back in register 'f'.
     */
    public void addwf(int instruction) throws NoRegisterAddressException {
        System.out.println("addwf with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int w = register.getW();
        int value = f + w;
        System.out.println("ADDWF, value: " + Integer.toHexString(value));
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.checkCarry(f, w, true);
        register.checkDC(f, w, true);
        register.checkZeroBit(value);
        register.incPC();
        register.incCycles();
    }


    /*AND the W register with register 'f'. If 'd'
     *is 0 the result is stored in the W register.
     *If 'd' is 1 the result is stored back in
     *register 'f'.
     */
    public void andwf(int instruction) throws NoRegisterAddressException {
        System.out.println("andwf with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int value = f & register.getW();
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.checkZeroBit(value);
        register.incPC();
        register.incCycles();
    }


    /*The contents of register 'f' are cleared
     *if 'd' is 1, else 'W' and the Z bit is set.
     */
    public void clrf_clrw(int instruction) throws NoRegisterAddressException {
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        if(instruction < 0x007F) {
            System.out.println("clrw value: 0x" + Integer.toHexString(address));
            register.setW(0);
            register.checkZeroBit(register.getW());
        } else {
            System.out.println("clrf Adresse: 0x" + Integer.toHexString(address));
            register.setRegValue(address, 0);
            register.checkZeroBit(register.getRegValue(address));
        }
        register.incPC();
        register.incCycles();
    }


    /*The contents of register 'f' are complemented.
     *If 'd' is 0 the result is stored in
     *W. If 'd' is 1 the result is stored back in
     *register 'f'.
     */
    public void comf(int instruction) throws NoRegisterAddressException {
        System.out.println("comf with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int value = (~f & 0xFF);
        System.out.println("comf value = " + value);
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.checkZeroBit(value);
        register.incPC();
        register.incCycles();
    }


    /*
     *Decrement register ’f’. If ’d’ is 0,
     *the result is stored in the W register.
     *If ’d’ is 1, the result is stored
     *back in register ’f’.
     */
    public void decf(int instruction) throws NoRegisterAddressException {
        System.out.println("decf with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        System.out.println("Addresse: " + Integer.toHexString(address));
        int f = register.getRegValue(address);
        int value = --f;
        //Wert auf MAX wenn Wert negativ -> "Unterlauf"
        if (value == -1) value = 255;
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.checkZeroBit(value);
        register.incPC();
        register.incCycles();
    }


    /*
     *The contents of register 'f' are decremented.
     *If 'd' is 0 the result is placed in the
     *W register. If 'd' is 1 the result is placed
     *back in register 'f'.
     *If the result is 1, the next instruction, is
     *executed. If the result is 0, then a NOP is
     *executed instead making it a 2TCY instruction.
     */
    public void decfsz(int instruction) throws NoRegisterAddressException {
        System.out.println("decfsz with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int value = --f;
        //Wert auf MAX wenn Wert negativ -> "Unterlauf"
        if (value == -1) value = 255;
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        //nop
        if (value == 0) {
            register.incPC();       //PC++
            register.incCycles();   //Cycle
        }
        register.incPC();
        register.incCycles();
    }


    /*
     *The contents of register ’f’ are
     *incremented. If ’d’ is 0, the result
     *is placed in the W register. If ’d’ is
     *1, the result is placed back in
     *register ’f’.
     */
    public void incf(int instruction) throws NoRegisterAddressException {
        System.out.println("incf with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int value = ++f;
        if (value > 255) value=0;
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.checkZeroBit(value);
        register.incPC();
        register.incCycles();
    }


    /*
     *The contents of register 'f' are incremented.
     *If 'd' is 0 the result is placed in
     *the W register. If 'd' is 1 the result is
     *placed back in register 'f'.
     *If the result is 1, the next instruction is
     *executed. If the result is 0, a NOP is executed
     *instead making it a 2TCY instruction.
     */
    public void incfsz(int instruction) throws NoRegisterAddressException {
        System.out.println("incfsz with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        //Incrementierung mit Maskierung falls Wert > 0xFF
        int value = ++f & 0xFF;
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        //nop
        if (value == 0) {
            register.incPC();       //PC++
            register.incCycles();   //Cycle
        }
        register.incPC();
        register.incCycles();
    }


    /*
     *Inclusive OR the W register with register
     *'f'. If 'd' is 0 the result is placed in the
     *W register. If 'd' is 1 the result is placed
     *back in register 'f'.
     */
    public void iorwf(int instruction) throws NoRegisterAddressException {
        System.out.println("iorwf with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int w = register.getW();
        int value = f | w;
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.checkZeroBit(value);
        register.incPC();
        register.incCycles();
    }


    /*
     *The contents of register f is moved to a
     *destination dependant upon the status
     *of d. If d = 0, destination is W register. If
     *d = 1, the destination is file register f
     *itself. d = 1 is useful to test a file register
     *since status flag Z is affected.
     */
    public void movf(int instruction) throws NoRegisterAddressException {
        System.out.println("movf with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        if(instruction < 0x007F) {
            register.setW(f);
        } else {
            register.setRegValue(address, f);
        }
        register.checkZeroBit(f);
        register.incPC();
        register.incCycles();
    }


    /*
     *Move data from W register to register
     *'f' or do nothing.
     */
    public void movwf_nop(int instruction) throws NoRegisterAddressException {
        if ((instruction & 0x0080) == 0x0080) {
            movwf(instruction);
        } else {
            nop(instruction);
        }
    }


    /*
     *Move data from W register to register
     *'f'.
     */
    public void movwf (int instruction) throws NoRegisterAddressException {
        System.out.println("movwf with: 0x" + Integer.toHexString(instruction));
        int address = instruction & 0x007F;
        int value = register.getW();
        register.setRegValue(address, value);
        register.incPC();
        register.incCycles();
    }


    /*
     *No operation.
     */
    public void nop (int instruction) throws NoRegisterAddressException {
        System.out.println("nop with: 0x" + Integer.toHexString(instruction));
        register.incPC();
        register.incCycles();
    }


    /*
     *The contents of register 'f' are rotated
     *one bit to the left through the Carry
     *Flag. If 'd' is 0 the result is placed in the
     *W register. If 'd' is 1 the result is stored
     *back in register 'f'.
     */
    public void rlf(int instruction) throws NoRegisterAddressException {
        System.out.println("rlf with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        // Carrybit auslesen
        int c = ((register.getRegValue(STATUS)) & 0x01);
        // neues Carrybit setzen
        register.setRegValue(STATUS, (f >>> 7) & 0x0001);
        // nach links shiften und c anhängen
        int value = (f << 1) + c;
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.incPC();
        register.incCycles();
    }


    /*
     *The contents of register 'f' are rotated
     *one bit to the right through the Carry
     *Flag. If 'd' is 0 the result is placed in the
     *W register. If 'd' is 1 the result is placed
     *back in register 'f'.
     */
    public void rrf(int instruction) throws NoRegisterAddressException {
        System.out.println("rrf with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        // aktuelles Carrybit speichern
        int c = (register.getRegValue(STATUS)) & 0x01;
        // neues Carrybit setzen
        register.setRegValue(STATUS, (f >>> 0) & 0x0001);
        // nach rechts shiften und c um 7 stellen nach links geshiftet addieren
        int value = (f >> 1) + (c << 7);
        //setRegValue to destination
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.incPC();
        register.incCycles();
    }


    /*
     *Subtract (2’s complement method) W register
     *from register 'f'. If 'd' is 0 the result is
     *stored in the W register. If 'd' is 1 the
     *result is stored back in register 'f'.
     */
    public void subwf(int instruction) throws NoRegisterAddressException {
        System.out.println("subwf with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int w = register.getW();
        int status = register.getRegValue(STATUS);
        //process data
        int value;
        if (w > f) {
            //Überlauf
            int diff = w - f;
            value = 256 - diff;
            //CarryBit = 0 da negatives Ergebnis
            //register.setRegValue(Register.STATUS, register.clearBit(status, 1));
        } else {
            value = f - w;
            // CarryBit = 1 da Ergebnis 0 oder positiv
            //register.setRegValue(Register.STATUS, register.setBit(status, 1));
        }
        //falls value pos vorzeichen entfernen
        value = value & 0x1FF;
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.checkZeroBit(value);
        register.checkCarry(f, w, false);
        register.checkDC(f, w, false);
        register.incPC();
        register.incCycles();
    }


    /*
     *The upper and lower nibbles of register
     *'f' are exchanged. If 'd' is 0 the result is
     *placed in W register. If 'd' is 1 the result
     *is placed in register 'f'.
     */
    public void swapf(int instruction) throws NoRegisterAddressException {
        System.out.println("swapf with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int upperBits = f & 0x00F0;
        int lower = f & 0x000F;
        int value = ((lower << 4) + (upperBits >> 4));
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.incPC();
        register.incCycles();
    }


    /*
     *Exclusive OR the contents of the
     *W register with register 'f'. If 'd' is
     *0, the result is stored in the W
     *register. If 'd' is 1, the result is
     *stored back in register 'f'.
     */
    public void xorwf(int instruction) throws NoRegisterAddressException {
        System.out.println("xorwf with: 0x" + Integer.toHexString(instruction));
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int w = register.getW();
        int value = w ^ f;
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.checkZeroBit(value);
        register.incPC();
        register.incCycles();
    }


    /*
     *Bit 'b' in register 'f' is cleared.
     */
    public void bcf(int instruction) throws NoRegisterAddressException {
        System.out.println("bcf with: 0x" + Integer.toHexString(instruction));
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int pos = ((instruction & 0x0380) >> 7);
        int value = f & ~(1 << pos);
        register.setRegValue(address, value);
        register.incPC();
        register.incCycles();
    }


    /*
     *Bit 'b' in register 'f' is set.
     */
    public void bsf(int instruction) throws NoRegisterAddressException {
        System.out.println("bsf with: 0x" + Integer.toHexString(instruction));
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int pos = (instruction & 0x0380) >> 7;
        int value = f | (1 << pos);
        register.setRegValue(address, value);
        register.incPC();
        register.incCycles();
    }


    /*
     *If bit 'b' in register 'f' is '1' then the next
     *instruction is executed.
     *If bit 'b', in register 'f', is '0' then the next
     *instruction is discarded, and a NOP is
     *executed instead, making this a 2TCY
     *instruction.
     */
    public void btfsc(int instruction) throws NoRegisterAddressException {
        System.out.println("btfsc with: 0x" + Integer.toHexString(instruction));
        int address = instruction & 0x007F;
        int pos = (instruction & 0x0380) >>> 7;
        int f = register.getRegValue(address);
        System.out.println("Value of btfsc: " + f + "  Position: " + pos);
        //Testet bit 'b' von f, wenn false dann nop()
        if(!register.testBit(f, pos)) {
            nop(instruction);
        }
        register.incPC();
        register.incCycles();
    }


    /*
     *If bit 'b' in register 'f' is '0' then the next
     *instruction is executed.
     *If bit 'b' is '1', then the next instruction is
     *discarded and a NOP is executed
     *instead, making this a 2TCY instruction.
     */
    public void btfss(int instruction) throws NoRegisterAddressException {
        System.out.println("btfss with: 0x" + Integer.toHexString(instruction));
        int address = instruction & 0x007F;
        int pos = (instruction & 0x0380) >>> 7;
        int f = register.getRegValue(address);
        //Testet bit 'b' von f, wenn true dann nop(), nächste Anweisung wird übersprungen
        if(register.testBit(f, pos)) {
            nop(instruction);
        }
        register.incPC();
        register.incCycles();
    }


    /*
     *The contents of the W register are
     *added to the eight bit literal 'k' and the
     *result is placed in the W register.
     */
    public void addlw(int instruction) throws NoRegisterAddressException {
        System.out.println("addlw with: 0x" + Integer.toHexString(instruction));
        //Maskieren
        int k = instruction & 0x00FF;
        int w = register.getW();
        int value = w+k;
        //ZeroBit, CarryBit, DigitalCarryBit
        register.checkZeroBit(value);
        register.checkCarry(w, k, true);
        register.checkDC(w, k, true);
        //Werte addieren
        register.setW(value);
        register.incPC();
        register.incCycles();
    }


    /*
     *The contents of W register are
     *AND’ed with the eight bit literal 'k'.The
     *result is placed in the W register.
     */
    public void andlw(int instruction) throws NoRegisterAddressException {
        System.out.println("andlw with: 0x" + Integer.toHexString(instruction));
        //Maskieren
        int value = instruction & 0x00FF;
        //Werte ver'UND'en
        register.setW(register.getW() & value);
        register.checkZeroBit(value);
        register.incPC();
        register.incCycles();
    }


    /*
     *Call Subroutine. First, return address
     *(PC+1) is pushed onto the stack.The
     *eleven bit immediate address is loaded
     *into PC bits <10:0>. The upper bits of
     *the PC are loaded from PCLATH. CALL
     *is a two cycle instruction.
     */
    public void call(int instruction) throws NoRegisterAddressException {
        System.out.println("call with: 0x" + Integer.toHexString(instruction));
        int k = instruction & 0x07FF;
        //(PC+1) is pushed onto the stack
        stack.push((register.getPC()+1));
        System.out.println("Auf Stack gepushed: " + Integer.toHexString(register.getPC()+1));

        //0b---4 3xxx
        int pcLath = register.getRegValue(PCLATH);
        //pcLath<4:3>
        pcLath = (pcLath >> 3) & 0x03;
        //0tes und 1tes bit wird an 11te und 12te Stelle gerückt
        pcLath = pcLath << 11;

        //pc setzen
        register.setPC(pcLath+k);
        System.out.println("ProgrammCounter: " + Integer.toHexString(pcLath + k));
        //2 Cycles
        register.incCycles();
        register.incCycles();
    }


    /*
     *GOTO is an unconditional branch. The
     *eleven bit immediate value is loaded
     *into PC bits <10:0>. The upper bits of
     *PC are loaded from PCLATH<4:3>.
     *GOTO is a two cycle instruction.
     */
    public void goTo(int instruction) throws NoRegisterAddressException {
        System.out.println("goTo with: 0x" + Integer.toHexString(instruction));
        int k = instruction & 0x00FF;

        //0b---4 3xxx
        int pcLath = register.getRegValue(PCLATH);
        //pcLath<4:3>
        pcLath = (pcLath >> 3) & 0x03;
        // 0tes und 1tes bit wird an 11te und 12te Stelle gerückt
        pcLath = pcLath << 11;

        // pc setzen
        register.setPC(pcLath + k);
        // 2 Cycles
        register.incCycles();
        register.incCycles();
    }


    /*
     *The contents of the W register is
     *OR’ed with the eight bit literal 'k'. The
     *result is placed in the W register.
     */
    public void iorlw(int instruction) throws NoRegisterAddressException {
        System.out.println("iorlw with: 0x" + Integer.toHexString(instruction));
        //Maskieren
        int k = instruction & 0x00FF;
        //Werte ver'ODER'n
        int value = register.getW() | k;
        register.setW(value);
        register.checkZeroBit(value);
        register.incPC();
        register.incCycles();
    }


    /*
     *The eight bit literal 'k' is loaded into W
     *register. The don’t cares will assemble
     *as 0’s.
     */
    public void movlw(int instruction) throws NoRegisterAddressException {
        System.out.println("movlw with: 0x" + Integer.toHexString(instruction));
        int k = instruction & 0x00FF;
        register.setW(k);
        register.incPC();
        register.incCycles();
    }

    /*
     *The W register is loaded with the eight
     *bit literal 'k'. The program counter is
     *loaded from the top of the stack (the
     *return address). This is a two cycle
     *instruction.
     */
    public void retlw(int instruction) throws NoRegisterAddressException {
        System.out.println("retlw with: 0x" + Integer.toHexString(instruction));
        int k = instruction & 0x00FF;
        // write to register w
        register.setW(k);

        //TOS in PC laden
        int pc = stack.pop();
        register.setPC(pc);
        System.out.println("PC von Stack gepoped: " + Integer.toHexString(pc));

        // 2 Cycles
        register.incCycles();
        register.incCycles();
    }


    /*
     *The W register is subtracted (2’s complement
     *method) from the eight bit literal 'k'.
     *The result is placed in the W register.
     */
    public void sublw(int instruction) throws NoRegisterAddressException {
        System.out.println("sublw with: 0x" + Integer.toHexString(instruction));
        int k = instruction & 0x00FF;
        int w = register.getW();
        int value;
        // process data
        if (w > k) {
            // CarryBit = 0, da negatives Ergebnis
            //Überlauf
            int diff = w - k;
            value = 256 - diff;
        } else {
            //CarryBit = 1 da Ergebnis positiv oder 0
            value = k - w;
        }

        //Check DC
        int status = register.getRegValue(STATUS);
        if ((w & 0x0F) > (k & 0x0F)) {
            //DC = 0 da negatives Ergebnis
            register.clearBit(status, 1);
        } else {
            // DC = 1 da Ergebnis 0 oder positiv
            register.setBit(status, 1);
        }
        register.setRegValue(STATUS, status);

        //Check CarryBit
        register.checkCarry(k, w, false);
        register.checkZeroBit(value);
        //write to destinaton
        register.setW(value);
        register.incPC();
        register.incCycles();
    }

    /*
     *The contents of the W register are
     *XOR’ed with the eight bit literal 'k'.
     *The result is placed in the W register.
     */
    public void xorlw(int instruction) throws NoRegisterAddressException {
        System.out.println("xorlw with: 0x" + Integer.toHexString(instruction));
        int k = instruction & 0x00FF;
        int w = register.getW();
        int value = w ^ k;
        register.setW(value);
        register.checkZeroBit(value);
        register.incPC();
        register.incCycles();
    }

    /*
     *Clear WatchdogTimer
     */
    public void clrwdt(int instruction) throws NoRegisterAddressException {
        System.out.println("clrwdt with: 0x" + Integer.toHexString(instruction));
        register.resetWdt();
        if(register.testBit(OPTION_REG, 3)){
            register.resetPreScaler();
        }
        register.setRegValue(STATUS, register.getRegValue(STATUS) | 0x18);
        //Cycle
        register.incCycles();
    }

    /*
     *Return from Interrupt
     */
    public void retfie(int instruction) throws NoRegisterAddressException {
        System.out.println("retfie with: 0x" + Integer.toHexString(instruction));
        //PC von Stack holen und setzen
        register.setPC(stack.pop());
        //1-->GIE
        register.setBit(INTCON, 7);
        // 2 Cycles
        register.incCycles();
        register.incCycles();
    }


    public void ret(int instruction) throws NoRegisterAddressException {
        System.out.println("return with: 0x" + Integer.toHexString(instruction));
        //PC vom stack
        int pc = stack.pop();
        register.setPC(pc);
        register.incCycles();
    }


    public void sleep(int instruction) throws NoRegisterAddressException {
        System.out.println("sleep with: 0x" + Integer.toHexString(instruction));
        register.incCycles();
    }
}