package model;

import exceptions.NoInstructionException;
import exceptions.NoRegisterAddressException;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 14.04.14
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */

public class Pars {

    // ByteToken
    public static final int TOK_ADDWF = 0x0700;
    public static final int TOK_ANDWF = 0x0500;
    //public static final int TOK_CLRF = 0x0180; Nicht nötig, siehe 2 drunter
    //public static final int TOK_CLRW = 0x0100; Nicht nötig, siehe 1 drunter
    public static final int TOK_CLRF_CLRW = 0x0100;
    public static final int TOK_COMF = 0x0900;
    public static final int TOK_DECF = 0x0300;
    public static final int TOK_DECFSZ = 0x0B00;
    public static final int TOK_INCF = 0x0A00;
    public static final int TOK_INCFSZ = 0x0F00;
    public static final int TOK_IORWF = 0x0400;
    public static final int TOK_MOVF = 0x0800;
    //public static final int TOK_MOVWF = 0x0080; Nicht nötig, siehe 2 drunter
    //public static final int TOK_NOP = 0x0000;   Nicht nötig, siehe 1 drunter
    public static final int TOK_MOVWF_NOP = 0x0000;
    public static final int TOK_RLF = 0x0D00;
    public static final int TOK_RRF = 0x0C00;
    public static final int TOK_SUBWF = 0x0200;
    public static final int TOK_SWAPF = 0x0E00;
    public static final int TOK_XORWF = 0x0600;

    // BitToken
    public static final int TOK_BCF = 0x1000;
    public static final int TOK_BSF = 0x1400;
    public static final int TOK_BTFSC = 0x1800;
    public static final int TOK_BTFSS = 0x1C00;

    // BitConstToken
    public static final int TOK_CLRWDT = 0x0064;
    public static final int TOK_RETFIE = 0x0009;
    public static final int TOK_RETURN = 0x0008;
    public static final int TOK_SLEEP = 0x0063;

    // Literal and Control Token
    public static final int TOK_ADDLW = 0x3E00;
    public static final int TOK_ANDLW = 0x3900;
    public static final int TOK_CALL = 0x2000;
    public static final int TOK_GOTO = 0x2800;
    public static final int TOK_IORLW = 0x3800;
    public static final int TOK_MOVLW = 0x3000;
    public static final int TOK_RETLW = 0x3400;
    public static final int TOK_SUBLW = 0x3C00;
    public static final int TOK_XORLW = 0x3A00;

    private Operations oper;

    public Pars(Operations operations) {
        this.oper = operations;
    }

    //Instruction decodieren
    public int decode(int instruction) throws NoInstructionException {

        //Bit Constant Instruction
        if (isBitConstInst(instruction)){
            System.out.println("isBitConstInt");
            return instruction;
        }

        switch (instruction & 0xF000) {
            //Byte-Instruction
            case 0x0000:
                return instruction & 0xFF00;
            //Bit-Instruction
            case 0x1000:
                return instruction & 0xFC00;
            //GOTO oder CALL
            case 0x2000:
                return instruction & 0xFC00;
            //Literal-Instruction
            case 0x3000:
                return instruction & 0xFF00;
            //Keine Zuweisung gefunden -> Fehler ausgeben
            default:
                throw new NoInstructionException(instruction);
        }
    }


    //Prüfen ob instruction bit-constant ist (ein Case trifft zu -> true)
    //Dies ist ein sogenanntes Fall-Through
    private boolean isBitConstInst(int instruction) {
        switch (instruction) {
            //RETURN-Befehl
            case 0x0008:
                //SLEEP-Befehl  (Go into standby mode)
            case 0x0063:
                //RETFIE-Befehl (Return from interrupt)
            case 0x0009:
                //CLRWDT-Befehl (Clear watchdog timer)
            case 0x0064:
                return true;
            default:
                return false;
        }
    }


    //Befehlszuweisung
    public void exec(int instruction, int opcode) throws NoRegisterAddressException {
        switch (opcode) {
            case TOK_ADDWF:
                oper.addwf(instruction);
                break;
            case TOK_ANDWF:
                oper.andwf(instruction);
                break;
            case TOK_CLRF_CLRW:
                oper.clrf_clrw(instruction);
                break;
            case TOK_COMF:
                oper.comf(instruction);
                break;
            case TOK_DECF:
                oper.decf(instruction);
                break;
            case TOK_DECFSZ:
                oper.decfsz(instruction);
                break;
            case TOK_INCF:
                oper.incf(instruction);
                break;
            case TOK_INCFSZ:
                oper.incfsz(instruction);
                break;
            case TOK_IORWF:
                oper.iorwf(instruction);
                break;
            case TOK_MOVF:
                oper.movf(instruction);
                break;
            case TOK_MOVWF_NOP:
                oper.movwf_nop(instruction);
                break;
            case TOK_RLF:
                oper.rlf(instruction);
                break;
            case TOK_RRF:
                oper.rrf(instruction);
                break;
            case TOK_SUBWF:
                oper.subwf(instruction);
                break;
            case TOK_SWAPF:
                oper.swapf(instruction);
                break;
            case TOK_XORWF:
                oper.xorwf(instruction);
                break;

            /*BIT-ORIENTED FILE REGISTER OPERATIONS*/
            case TOK_BCF:
                oper.bcf(instruction);
                break;

            case TOK_BSF:
                oper.bsf(instruction);
                break;

            case TOK_BTFSC:
                oper.btfsc(instruction);
                break;

            case TOK_BTFSS:
                oper.btfss(instruction);
                break;

            /*LITERAL AND CONTROL OPERATIONS*/
            case TOK_ADDLW:
                oper.addlw(instruction);
                break;

            case TOK_ANDLW:
                oper.andlw(instruction);
                break;

            case TOK_CALL:
                oper.call(instruction);
                break;

            case TOK_GOTO:
                oper.goTo(instruction);
                break;

            case TOK_IORLW:
                oper.iorlw(instruction);
                break;

            case TOK_MOVLW:
                oper.movlw(instruction);
                break;

            case TOK_RETLW:
                oper.retlw(instruction);
                break;

            case TOK_SUBLW:
                oper.sublw(instruction);
                break;

            case TOK_XORLW:
                oper.xorlw(instruction);
                break;

            /*BIT CONSTANT OPERATIONS*/
            case TOK_CLRWDT:
                oper.clrwdt(instruction);
                break;

            case TOK_RETFIE:
                oper.retfie(instruction);
                break;

            case TOK_RETURN:
                oper.ret(instruction);
                break;

            case TOK_SLEEP:
                oper.sleep(instruction);
                break;
        }
    }

}