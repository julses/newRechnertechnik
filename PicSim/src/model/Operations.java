package model;

import exceptions.IllegalCarryOperationException;


/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 20.04.14
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class Operations {
    Register register;
    public Operations(Register register) {
        this.register = register;
    }



    /*Add the contents of the W register with
     *register 'f'. If 'd' is 0 the result is stored
     *in the W register. If 'd' is 1 the result is
     *stored back in register 'f'.
     */
    public void addwf(int instruction) throws IllegalCarryOperationException {
        System.out.println("addwf with: " + instruction);
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int w = register.getW();
        int value = f + w;
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.checkDC(f, w, true);
        register.checkCarry(f, w, true);
        register.checkZeroBit(value);
        register.incPC();
        register.incCycles();
    }


    /*AND the W register with register 'f'. If 'd'
     *is 0 the result is stored in the W register.
     *If 'd' is 1 the result is stored back in
     *register 'f'.
     */
    public void andwf(int instruction) {
        System.out.println("andwf with: " + instruction);
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
    public void clrf_clrw(int instruction){
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
    public void comf(int instruction){
        System.out.println("comf with: " + instruction);
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int value = (~f & 0xFF);
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
    public void decf(int instruction) {
        System.out.println("decf with: " + instruction);
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
    public void decfsz(int instruction) {
        System.out.println("decfsz with: " + instruction);
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
    public void incf(int instruction) {
        System.out.println("incf with: " + instruction);
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
    public void incfsz(int instruction) {
        System.out.println("incfsz with: " + instruction);
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
    public void iorwf(int instruction) {
        System.out.println("iorwf with: " + instruction);
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
    public void movf(int instruction) {
        System.out.println("movf with: " + instruction);
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
    public void movwf_nop(int instruction) {
        System.out.println("movwf_nop with: " + instruction);
        if ((instruction & 0x0080) == 0x0080) {
            System.out.println("movwf");
            int address = instruction & 0x007F;
            int value = register.getW();
            register.setRegValue(address, value);
        }
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
    public void rlf(int instruction) {
        System.out.println("rlf with: " + instruction);
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        // Carrybit auslesen
        int c = (register.getRegValue(Register.STATUS)) & 0x01;
        // neues Carrybit setzen
        register.setRegValue(Register.STATUS, (f >>> 7) & 0x0001);
        // nach links shiften und c anhängen
        int value = (f << 1) | c;
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
    public void rrf(int instruction) {
        System.out.println("rrf with: " + instruction);
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        // aktuelles Carrybit speichern
        int c = (register.getRegValue(Register.STATUS)) & 0x01;
        // neues Carrybit setzen
        register.setRegValue(Register.STATUS, (f >>> 0) & 0x0001);
        // nach links shiften und c um 7 stellen nach rechts geshiftet anhängen
        int value = (f >> 1) | (c << 7);
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
    public void subwf(int instruction) {
        System.out.println("subwf with: " + instruction);
        instruction = instruction & 0x00FF;
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int w = register.getW();
        int status = register.getRegValue(Register.STATUS);
        //process data
        int value;
        if (w > f) {
            //Überlauf
            int diff = w - f;
            value = 256 - diff;
            //CarryBit = 0 da negatives Ergebnis
            //status = setBitAt(status, 0, 0);
        } else {
            value = f - w;
            // CarryBit = 1 da Ergebnis 0 oder positiv
            //status = setBitAt(status, 0, 1);
        }
        register.setRegValue(Register.STATUS, status);
        //falls value pos vorzeichen entfernen
        value = value & 0x1FF;
        if(instruction < 0x007F) {
            register.setW(value);
        } else {
            register.setRegValue(address, value);
        }
        register.checkZeroBit(value);
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
    public void swapf(int instruction) {
        System.out.println("swapf with: " + Integer.toHexString(instruction));
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
    public void xorwf(int instruction) {
        System.out.println("xorwf with: " + Integer.toHexString(instruction));
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
    public void bcf(int instruction) {
        System.out.println("bcf with: " + instruction);
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
    public void bsf(int instruction) {
        System.out.println("bsf with: " + instruction);
        int address = instruction & 0x007F;
        int f = register.getRegValue(address);
        int pos = (instruction & 0x0380) >> 7;
        int value = f | (1 << pos);
        register.setRegValue(address, value);
        register.incPC();
        register.incCycles();
    }

    public void btfsc(int instruction) {
        System.out.println("btfsc with: " + instruction);
    }

    public void btfss(int instruction) {
        System.out.println("btfss with: " + instruction);
    }

    /*
     *The contents of the W register are
     *added to the eight bit literal 'k' and the
     *result is placed in the W register.
     */
    public void addlw(int instruction) {
        //Maskieren
        int value = instruction & 0x00FF;
        //Werte addieren
        register.setW(register.getW() + value);
        register.incPC();
        register.incCycles();
    }

    /*
     *The contents of W register are
     *AND’ed with the eight bit literal 'k'.The
     *result is placed in the W register.
     */
    public void andlw(int instruction) {
        //Maskieren
        int value = instruction & 0x00FF;
        //Werte ver'UND'en
        register.setW(register.getW() & value);
        register.incPC();
        register.incCycles();
    }

    public void call(int instruction) {
        System.out.println("call with: " + instruction);
    }

    public void goTo(int instruction) {
        System.out.println("goTo with: " + instruction);
    }

    /*
     *The contents of the W register is
     *OR’ed with the eight bit literal 'k'. The
     *result is placed in the W register.
     */
    public void iorlw(int instruction) {
        System.out.println("iorlw with: " + instruction);
        //Maskieren
        int k = instruction & 0x00FF;
        //Werte ver'ODER'n
        int value = register.getW() | k;
        register.setW(value);
        register.checkZeroBit(value);
        register.incPC();
        register.incCycles();
    }

    public void movlw(int instruction) {
        System.out.println("movlw with: " + instruction);
    }

    public void retlw(int instruction) {
        System.out.println("retlw with: " + instruction);
    }

    public void sublw(int instruction) {
        System.out.println("sublw with: " + instruction);
    }

    public void xorlw(int instruction) {
        System.out.println("xorlw with: " + instruction);
    }

    public void clrwdt(int instruction) {
        System.out.println("clrwdt with: " + instruction);
    }

    public void retfie(int instruction) {
        System.out.println("retfie with: " + instruction);
    }

    public void ret(int instruction) {
        System.out.println("ret with: " + instruction);
    }

    public void sleep(int instruction) {
        System.out.println("sleep with: " + instruction);
    }
}