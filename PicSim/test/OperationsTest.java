import model.Operations;
import model.Pars;
import model.Register;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 12.05.14
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */

public class OperationsTest {
    Register register = new Register();
    Operations oper = new Operations(register);
    Pars pars = new Pars(oper);


    @Test
    public void testAddwf() throws Exception {
        int instruction = 0x784;
        register.setW(0x17);
        register.setRegValue(Register.FSR, 0xC2);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        Assert.assertEquals(0xD9, register.getRegValue(Register.FSR));
        Assert.assertEquals(0x17, register.getW());
    }


    @Test
    public void testAndwf() throws Exception {
        int instruction = 0x0584;
        register.setW(0x17);
        register.setRegValue(register.FSR, 0x0C2);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        Assert.assertEquals(0x02, register.getRegValue(Register.FSR));
        Assert.assertEquals(0x17, register.getW());
    }

    @Test
    public void testClrf() throws Exception {
        int instruction = 0x0184;
        register.setRegValue(register.FSR, 0x0C2);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        Assert.assertEquals(0, register.getRegValue(Register.FSR));
        Assert.assertTrue(register.getZeroBit());
    }

    @Test
    public void testClrw() throws Exception {
        int instruction = 0x0104;
        register.setW(0x0C2);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        Assert.assertEquals(0, register.getW());
        Assert.assertTrue(register.getZeroBit());
    }

    @Test
    public void testComf() throws Exception {
        int instruction = 0x0950;
        register.setRegValue(0x50, 0x13);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        Assert.assertEquals(0x13, register.getRegValue(0x50));
        Assert.assertEquals(0xEC, register.getW());
    }

    @Test
    public void testDecf() throws Exception {
        int instruction = 0x03D0;
        register.setRegValue(0x50, 0x1);
        pars.exec(instruction, pars.decode(instruction));
        Assert.assertEquals(0x0, register.getRegValue(0x50));
        Assert.assertTrue(register.getZeroBit());
    }

    @org.junit.Test
    public void testDecfsz() throws Exception {
        int instruction = 0x0BD0;
        //Wert von 'f' decrementieren und in 'f' speichern, NOP() ausführen
        register.setRegValue(0x50, 1);
        register.setW(0x3);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        Assert.assertEquals(0x0, register.getRegValue(0x50));
        Assert.assertEquals(0x3, register.getW());
        Assert.assertEquals(2, register.getPC());
        //Wert von 'f' decrementieren und in 'f' speichern, unterlauf simmulieren, NOP nicht ausführen
        pars.exec(instruction, opcode);
        Assert.assertEquals(0xFF, register.getRegValue(0x50));
        Assert.assertEquals(0x3, register.getW());
        Assert.assertEquals(3, register.getPC());
        //Wert von 'f' decrementieren und in 'w' speichern, NOP() nicht ausführen
        instruction = 0x0B50;
        pars.exec(instruction, opcode);
        Assert.assertEquals(0xFF, register.getRegValue(0x50));
        Assert.assertEquals(0xFE, register.getW());
        Assert.assertEquals(4, register.getPC());
    }

    @Test
    public void testIncf() throws Exception {
        int instruction = 0x0aDe;
        register.setRegValue(0x5e, 0xFF);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        //Value of f incremented?
        Assert.assertEquals(0x0, register.getRegValue(0x5e));
        //Zero Bit affected?
        Assert.assertTrue(register.getZeroBit());
    }

    @org.junit.Test
    public void testIncfsz() throws Exception {
        int instruction = 0x0FDc;
        //Wert von 'f' incrementieren und in 'f' speichern, überlauf simulieren, NOP() ausführen
        register.setRegValue(0x5c, 0xFF);
        register.setW(0x3);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        Assert.assertEquals(0x0, register.getRegValue(0x5c));
        Assert.assertEquals(0x3, register.getW());
        Assert.assertEquals(2, register.getPC());
        //Wert von 'f' decrementieren und in 'f' speichern, NOP nicht ausführen
        pars.exec(instruction, opcode);
        Assert.assertEquals(0x1, register.getRegValue(0x5c));
        Assert.assertEquals(0x3, register.getW());
        Assert.assertEquals(0x3, register.getPC());
        //Wert von 'f' decrementieren und in 'w' speichern, NOP() nicht ausführen
        instruction = 0x0F5C;
        pars.exec(instruction, opcode);
        Assert.assertEquals(0x1, register.getRegValue(0x5c));
        Assert.assertEquals(0x2, register.getW());
        Assert.assertEquals(4, register.getPC());
    }
/*
    @org.junit.Test
    public void testIorwf() throws Exception {

    }
*/
    @Test
    public void testMovf() throws Exception {
        int instruction = 0x0884;
        register.setRegValue(Register.FSR, 0x0);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        Assert.assertEquals(0x0, register.getRegValue(Register.FSR));
        Assert.assertTrue(register.getZeroBit());
    }

    @org.junit.Test
    public void testMovwf() throws Exception {
    //TODO : Problem with decoding addresses of Bank 1
        int instruction = 0x00D0;
        register.setRegValue(0x50, 0xFF);
        register.setW(0x4F);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        Assert.assertEquals(0x4F, register.getRegValue(0x50));
    }

    @Test
    public void testRlf() throws Exception {
        /*Case 1:
         * Before Instruction        After Instruction
         * REG1 = 1110 0110          REG1 = 1110 0110
         * C = 0                     W = 1100 1100
         *                           C = 1
         */
        int instruction = 0x0d50;
        register.setRegValue(0x50, 0xe6);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        Assert.assertEquals(0xe6, register.getRegValue(0x50));
        Assert.assertEquals(0xcc, register.getW());
        Assert.assertTrue(register.testBit(register.getRegValue(Register.STATUS), 0));
    }

    @Test
    public void testRrf() throws Exception {
        int instruction = 0x0c51;
        register.setRegValue(0x51, 0xe6);
        int opcode = pars.decode(instruction);
        pars.exec(instruction, opcode);
        Assert.assertEquals(0xe6, register.getRegValue(0x51));
        Assert.assertEquals(0x73, register.getW());
        Assert.assertFalse(register.testBit(register.getRegValue(Register.STATUS), 0));
    }

    @Test
    public void testSubwf() throws Exception {
        int instruction;
        int addressf = 0x52;

        /*Case 1
         * Before Instruction       After Instruction
         * REG1 = 3                 REG1 = 1
         * W = 2                    W = 2
         * C = ?                    C = 1; result is positive
         * Z = ?                    Z = 0
         */
        register.valueOnReset();
        instruction = 0x02d2;
        register.setRegValue(addressf, 3);
        register.setW(2);
        pars.exec(instruction, pars.decode(instruction));
        Assert.assertEquals(0x01, register.getRegValue(addressf));
        Assert.assertEquals(0x02, register.getW());
        Assert.assertEquals(1 , (Register.STATUS & 0x0001));
        Assert.assertFalse(register.getZeroBit());

        /*Case 2:
         * Before Instruction       After Instruction
         * REG1 = 2                 REG1 = 0
         * W = 2                    W = 2
         * C = ?                    C = 1; result is zero
         * Z = ?                    Z = 1
         */
        register.valueOnReset();
        instruction = 0x02d2;
        register.setRegValue(addressf, 2);  //REG1 = 2
        register.setW(2);                   //W = 2
        pars.exec(instruction, pars.decode(instruction));
        Assert.assertEquals(0, register.getRegValue(addressf));
        Assert.assertEquals(2, register.getW());
        Assert.assertEquals(1 , (Register.STATUS & 0x0001));
        Assert.assertTrue(register.getZeroBit());

        /*Case 3:
         * Before Instruction       After Instruction
         * REG1 = 1                 REG1 = 0xFF
         * W = 2                    W = 2
         * C = ?                    C = 0; result is negative
         * Z = ?                    Z = 0
         */
        register.valueOnReset();
        instruction = 0x02d2;
        register.setRegValue(addressf, 1);
        register.setW(2);
        pars.exec(instruction, pars.decode(instruction));
        Assert.assertEquals(0xFF, register.getRegValue(addressf));
        Assert.assertEquals(2, register.getW());
        Assert.assertEquals(1 , (Register.STATUS & 0x0001));
        Assert.assertFalse(register.getZeroBit());
    }

    @Test
    public void testSwapf() throws Exception {
        /*Case 1:
         * Before Instruction:      After Instruction:
         * REG1 = 0xA5              REG1 = 0xA5
         *                          W = 0x5A
         */
        register.setRegValue(0x53, 0xA5);
        pars.exec(0x0e53, pars.decode(0x0e53));
        Assert.assertEquals(0xA5, register.getRegValue(0x53));
        Assert.assertEquals(0x5A, register.getW());
        /*Case 2:
         * Before Instruction:      After Instruction:
         * REG1 = 0xA5              REG1 = 0x5A
         */
        pars.exec(0x0ed3, pars.decode(0x0ed3));
        Assert.assertEquals(0x5A, register.getRegValue(0x53));
    }

    @Test
    public void testXorwf() throws Exception {
        register.setRegValue(0x54, 0xAF);
        register.setW(0xB5);
        pars.exec(0x06d4, pars.decode(0x06d4));
        Assert.assertEquals(0x1A, register.getRegValue(0x54));
        Assert.assertEquals(0xB5, register.getW());
    }

    @Test
    public void testBcf() throws Exception {
        register.setRegValue(0x55, 0xC7);
        pars.exec(0x13d5, pars.decode(0x13d5));
        Assert.assertEquals(0x47, register.getRegValue(0x55));
    }

    @org.junit.Test
    public void testBsf() throws Exception {
        register.setRegValue(0x55, 0x0A);
        pars.exec(0x17d5, pars.decode(0x17d5));
        Assert.assertEquals(0x8A, register.getRegValue(0x55));
    }

    @org.junit.Test
    public void testBtfsc() throws Exception {
        //Kein NOP() ausführen
        register.setRegValue(0x56, 0xFF);
        pars.exec(0x18D6, pars.decode(0x18D6));
        Assert.assertEquals(1, register.getPC());
        //NOP() ausführen
        register.setRegValue(0x56, 0x00);
        pars.exec(0x18D6, pars.decode(0x18D6));
        Assert.assertEquals(3, register.getPC());
    }

    @org.junit.Test
    public void testBtfss() throws Exception {
        //NOP() ausführen
        register.setRegValue(0x57, 0xFF);
        pars.exec(0x1FD7, pars.decode(0x1FD7));
        Assert.assertEquals(2, register.getPC());
        //Kein NOP() ausführen
        register.setRegValue(0x57, 0x00);
        pars.exec(0x1DD7, pars.decode(0x1DD7));
        Assert.assertEquals(3, register.getPC());
    }

    @Test
    public void testAddlw() throws Exception {
        register.setW(0x10);
        pars.exec(0x3E15, pars.decode(0x3E15));
        Assert.assertEquals(0x25, register.getW());
    }

    @org.junit.Test
    public void testAndlw() throws Exception {
        register.setW(0xA3);
        pars.exec(0x395F, pars.decode(0x395F));
        Assert.assertEquals(0x03, register.getW());
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
        register.setW(0x9A);
        pars.exec(0x3835, pars.decode(0x3835));
        Assert.assertEquals(0xBF, register.getW());
        // TODO : Why has the Bit to be true? - Example is from PIC-Manual
        //Assert.assertTrue(register.getZeroBit());
    }

    @Test
    public void testMovlw() throws Exception {
        pars.exec(0x305A, pars.decode(0x305A));
        Assert.assertEquals(0x5A, register.getW());
    }
/*
    @org.junit.Test
    public void testRetlw() throws Exception {

    }

    @org.junit.Test
    public void testSublw() throws Exception {
        /*
         *Case 1:
         * Before Instruction       After Instruction
         * W = 1                    W = 1
         * C = ?                    C = 1; result is positive
         * Z = ?                    Z = 0
         */

        /*
         *Case 2:
         * Before Instruction       After Instruction
         * W = 2                    W = 0
         * C = ?                    C = 1; result is zero
         * Z = ?                    Z = 1
         */


        /*
         *Case 3:
         * Before Instruction       After Instruction
         * W = 3                    W = 0xFF
         * C = ?                    C = 0; result is negative
         * Z = ?                    Z = 0
         */
/*
    }

    @org.junit.Test
    public void testXorlw() throws Exception {

    }

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
