import controller.Instructions;
import controller.Pars;
import controller.Register;
import static controller.Register.RegisterAdresses.*;
import controller.Stack;
import org.junit.Test;

import javax.swing.event.EventListenerList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 12.05.14
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */

public class OperationsTest {
    EventListenerList guiListener = new EventListenerList();
    Stack stack = new Stack(guiListener);
    Register register = new Register(stack, guiListener);
    Instructions oper = new Instructions(register, stack);
    Pars pars = new Pars(oper);


    @Test
    public void testAddwf() throws Exception {
        int instruction, opcode;

        register.setW(0x1);
        instruction = 0x0782;
        opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        assertEquals(0x1, register.getW());
        assertEquals(0x2, register.getPC());

        instruction = 0x784;
        register.setW(0x17);
        register.setRegValue(FSR, 0xC2);
        opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        assertEquals(0xD9, register.getRegValue(FSR));
        assertEquals(0x17, register.getW());
    }


    @Test
    public void testAndwf() throws Exception {
        int instruction = 0x0584;
        register.setW(0x17);
        register.setRegValue(FSR, 0x0C2);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        assertEquals(0x02, register.getRegValue(FSR));
        assertEquals(0x17, register.getW());
    }

    @Test
    public void testClrf() throws Exception {
        int instruction = 0x0184;
        register.setRegValue(FSR, 0x0C2);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        assertEquals(0, register.getRegValue(FSR));
        assertTrue(register.getZeroBit());
    }

    @Test
    public void testClrw() throws Exception {
        int instruction = 0x0104;
        register.setW(0x0C2);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        assertEquals(0, register.getW());
        assertTrue(register.getZeroBit());
    }

    @Test
    public void testComf() throws Exception {
        int instruction = 0x090c;
        register.setRegValue(0x0c, 0x13);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        assertEquals(0x13, register.getRegValue(0x0c));
        assertEquals(0xEC, register.getW());
    }

    @Test
    public void testDecf() throws Exception {
        int instruction = 0x038c;
        register.setRegValue(0x0c, 0x1);
        pars.exec(instruction, pars.decode(instruction));
        assertEquals(0x0, register.getRegValue(0x0c));
        assertTrue(register.getZeroBit());
    }

    @org.junit.Test
    public void testDecfsz() throws Exception {
        int instruction = 0x0B8c;
        //Wert von 'f' decrementieren und in 'f' speichern, NOP() ausführen
        register.setRegValue(0x0c, 1);
        register.setW(0x3);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        assertEquals(0x0, register.getRegValue(0x0c));
        assertEquals(0x3, register.getW());
        assertEquals(2, register.getPC());
        //Wert von 'f' decrementieren und in 'f' speichern, unterlauf simmulieren, NOP nicht ausführen
        pars.exec(instruction, opcode);
        assertEquals(0xFF, register.getRegValue(0x0c));
        assertEquals(0x3, register.getW());
        assertEquals(3, register.getPC());
        //Wert von 'f' decrementieren und in 'w' speichern, NOP() nicht ausführen
        instruction = 0x0B0c;
        pars.exec(instruction, opcode);
        assertEquals(0xFF, register.getRegValue(0x0c));
        assertEquals(0xFE, register.getW());
        assertEquals(4, register.getPC());
    }

    @Test
    public void testIncf() throws Exception {
        int instruction = 0x0a8c;
        register.setRegValue(0x0c, 0xFF);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        //Value of f incremented?
        assertEquals(0x0, register.getRegValue(0x0c));
        //Zero Bit affected?
        assertTrue(register.getZeroBit());
    }

    @org.junit.Test
    public void testIncfsz() throws Exception {
        int instruction = 0x0F8c;
        //Wert von 'f' incrementieren und in 'f' speichern, überlauf simulieren, NOP() ausführen
        register.setRegValue(0x0c, 0xFF);
        register.setW(0x3);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        assertEquals(0x0, register.getRegValue(0x0c));
        assertEquals(0x3, register.getW());
        assertEquals(2, register.getPC());
        //Wert von 'f' decrementieren und in 'f' speichern, NOP nicht ausführen
        pars.exec(instruction, opcode);
        assertEquals(0x1, register.getRegValue(0x0c));
        assertEquals(0x3, register.getW());
        assertEquals(0x3, register.getPC());
        //Wert von 'f' decrementieren und in 'w' speichern, NOP() nicht ausführen
        instruction = 0x0F0C;
        pars.exec(instruction, opcode);
        assertEquals(0x1, register.getRegValue(0x0c));
        assertEquals(0x2, register.getW());
        assertEquals(4, register.getPC());
    }

    @org.junit.Test
    public void testIorwf() throws Exception {
        /*
         *Before Instruction    After Instruction
         * RESULT = 0x13        RESULT = 0x13
         * W = 0x91             W = 0x93
         *                      Z = 1
         */
        int instruction = 0x040c;
        register.setRegValue(0x0c, 0x13);
        register.setW(0x91);
        pars.exec(instruction, pars.decode(instruction));
        assertEquals(0x13, register.getRegValue(0x0c));
        assertEquals(0x93, register.getW());
        //Why has the Bit to be true? - Example is from PIC-Manual
        //Assert.assertTrue(register.getZeroBit());
    }

    @Test
    public void testMovf() throws Exception {
        int instruction = 0x0884;
        register.setRegValue(FSR, 0x0);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        assertEquals(0x0, register.getRegValue(FSR));
        assertTrue(register.getZeroBit());
    }

    @org.junit.Test
    public void testMovwf() throws Exception {
        int instruction = 0x008c;
        register.setRegValue(0x0c, 0xFF);
        register.setW(0x4F);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        assertEquals(0x4F, register.getRegValue(0x0c));
    }

    @Test
    public void testRlf() throws Exception {
        /*Case 1:
         * Before Instruction        After Instruction
         * REG1 = 1110 0110          REG1 = 1110 0110
         * C = 0                     W = 1100 1100
         *                           C = 1
         */
        int instruction = 0x0d0c;
        register.setRegValue(0x0c, 0xe6);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        assertEquals(0xe6, register.getRegValue(0x0c));
        assertEquals(0xcc, register.getW());
        assertTrue(register.testBit(register.getRegValue(STATUS), 0));
    }

    @Test
    public void testRrf() throws Exception {
        int instruction = 0x0c0c;
        register.setRegValue(0x0c, 0xe6);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        assertEquals(0xe6, register.getRegValue(0x0c));
        assertEquals(0x73, register.getW());
        assertFalse(register.testBit(register.getRegValue(STATUS), 0));
    }

    @Test
    public void testSubwf() throws Exception {
        int instruction;
        int addressf = 0x0c;

        /*Case 1
         * Before Instruction       After Instruction
         * REG1 = 3                 REG1 = 1
         * W = 2                    W = 2
         * C = ?                    C = 1; result is positive
         * Z = ?                    Z = 0
         */
        register.valueOnReset();
        instruction = 0x028c;
        register.setRegValue(addressf, 3);
        register.setW(2);
        pars.exec(instruction, pars.decode(instruction));
        assertEquals(0x01, register.getRegValue(addressf));
        assertEquals(0x02, register.getW());
        assertTrue(register.testBit(register.getRegValue(STATUS), 0));
        assertFalse(register.getZeroBit());

        /*Case 2:
         * Before Instruction       After Instruction
         * REG1 = 2                 REG1 = 0
         * W = 2                    W = 2
         * C = ?                    C = 1; result is zero
         * Z = ?                    Z = 1
         */
        register.valueOnReset();
        instruction = 0x028c;
        register.setRegValue(addressf, 2);  //REG1 = 2
        register.setW(2);                   //W = 2
        pars.exec(instruction, pars.decode(instruction));
        assertEquals(0, register.getRegValue(addressf));
        assertEquals(2, register.getW());
        assertTrue(register.testBit(register.getRegValue(STATUS), 0));
        assertTrue(register.getZeroBit());

        /*Case 3:
         * Before Instruction       After Instruction
         * REG1 = 1                 REG1 = 0xFF
         * W = 2                    W = 2
         * C = ?                    C = 0; result is negative
         * Z = ?                    Z = 0
         */
        register.valueOnReset();
        instruction = 0x028c;
        register.setRegValue(addressf, 1);
        register.setW(2);
        pars.exec(instruction, pars.decode(instruction));
        assertEquals(0xFF, register.getRegValue(addressf));
        assertEquals(2, register.getW());
        assertFalse(register.testBit(register.getRegValue(STATUS), 0));
        assertFalse(register.getZeroBit());
    }

    @Test
    public void testSwapf() throws Exception {
        /*Case 1:
         * Before Instruction:      After Instruction:
         * REG1 = 0xA5              REG1 = 0xA5
         *                          W = 0x5A
         */
        register.setRegValue(0x0c, 0xA5);
        pars.exec(0x0e0c, pars.decode(0x0e0c));
        assertEquals(0xA5, register.getRegValue(0x0c));
        assertEquals(0x5A, register.getW());
        /*Case 2:
         * Before Instruction:      After Instruction:
         * REG1 = 0xA5              REG1 = 0x5A
         */
        pars.exec(0x0e8c, pars.decode(0x0e8c));
        assertEquals(0x5A, register.getRegValue(0x0c));
    }

    @Test
    public void testXorwf() throws Exception {
        register.setRegValue(0x0c, 0xAF);
        register.setW(0xB5);
        pars.exec(0x068c, pars.decode(0x068c));
        assertEquals(0x1A, register.getRegValue(0x0c));
        assertEquals(0xB5, register.getW());
    }

    @Test
    public void testBcf() throws Exception {
        register.setRegValue(0x0c, 0xC7);
        pars.exec(0x138c, pars.decode(0x138c));
        assertEquals(0x47, register.getRegValue(0x0c));
    }

    @org.junit.Test
    public void testBsf() throws Exception {
        register.setRegValue(0x0c, 0x0A);
        pars.exec(0x178c, pars.decode(0x178c));
        assertEquals(0x8A, register.getRegValue(0x0c));
    }

    @org.junit.Test
    public void testBtfsc() throws Exception {
        //Kein NOP() ausführen
        register.setRegValue(0x0c, 0xFF);
        pars.exec(0x188c, pars.decode(0x188c));
        assertEquals(1, register.getPC());
        //NOP() ausführen
        register.setRegValue(0x0d, 0x00);
        pars.exec(0x188d, pars.decode(0x188d));
        assertEquals(3, register.getPC());
    }

    @org.junit.Test
    public void testBtfss() throws Exception {
        //NOP() ausführen
        register.setRegValue(0x0c, 0xFF);
        pars.exec(0x1F8c, pars.decode(0x1F8c));
        assertEquals(2, register.getPC());
        //Kein NOP() ausführen
        register.setRegValue(0x0d, 0x00);
        pars.exec(0x1D8d, pars.decode(0x1D8d));
        assertEquals(3, register.getPC());
    }

    @Test
    public void testAddlw() throws Exception {
        register.setW(0x10);
        pars.exec(0x3E15, pars.decode(0x3E15));
        assertEquals(0x25, register.getW());
    }

    @org.junit.Test
    public void testAndlw() throws Exception {
        register.setW(0xA3);
        pars.exec(0x395F, pars.decode(0x395F));
        assertEquals(0x03, register.getW());
    }
/*
    @org.junit.Test
    public void testCall() throws Exception {

    }

    @org.junit.Test
    public void testGoTo() throws Exception {

    }
*/
    @Test
    public void testIorlw() throws Exception {
        /*
         *Before Instruction    After Instruction
         * W = 0x9A              W = 0xBF
         *                       Z = 1
         */
        register.setW(0x9A);
        pars.exec(0x3835, pars.decode(0x3835));
        assertEquals(0xBF, register.getW());
        //Why has the Bit to be true? - Example is from PIC-Manual
        //Assert.assertTrue(register.getZeroBit());
    }

    @Test
    public void testMovlw() throws Exception {
        pars.exec(0x305A, pars.decode(0x305A));
        assertEquals(0x5A, register.getW());
    }
/*
    @org.junit.Test
    public void testRetlw() throws Exception {

    }
*/
    @org.junit.Test
    public void testSublw() throws Exception {
        int instruction = 0x3c02;

        /*
         *Case 1:
         * Before Instruction       After Instruction
         * W = 1                    W = 1
         * C = ?                    C = 1; result is positive
         * Z = ?                    Z = 0
         */
        register.setW(1);
        pars.exec(instruction, pars.decode(instruction));
        assertEquals(1, register.getW());
        assertTrue(register.testBit(register.getRegValue(STATUS), 0));
        assertFalse(register.getZeroBit());

        /*
         *Case 2:
         * Before Instruction       After Instruction
         * W = 2                    W = 0
         * C = ?                    C = 1; result is zero
         * Z = ?                    Z = 1
         */
        register.setW(2);
        pars.exec(instruction, pars.decode(instruction));
        assertEquals(0, register.getW());
        assertTrue(register.testBit(register.getRegValue(STATUS), 0));
        assertTrue(register.getZeroBit());

        /*
         *Case 3:
         * Before Instruction       After Instruction
         * W = 3                    W = 0xFF
         * C = ?                    C = 0; result is negative
         * Z = ?                    Z = 0
         */
        instruction = 0x3c02;
        register.setW(3);
        pars.exec(instruction, pars.decode(instruction));
        assertEquals(0xFF, register.getW());
        assertFalse(register.testBit(register.getRegValue(STATUS), 0));
        assertFalse(register.getZeroBit());

    }

    @org.junit.Test
    public void testXorlw() throws Exception {
        /*
         *Before Instruction    After Instruction
         * W = 0xB5              W = 0x1A
         */
        int instruction = 0x3aaf;
        register.setW(0xB5);
        pars.exec(instruction, pars.decode(instruction));
        assertEquals(0x1A, register.getW());
    }
/*
    @org.junit.Test
    public void testClrwdt() throws Exception {

    }

    @org.junit.Test
    public void testRetfie() throws Exception {

    }

    @org.junit.Test
    public void testRet() throws Exception {

    }

    @org.junit.Test
    public void testSleep() throws Exception {

    }
*/
}
