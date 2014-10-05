package controller;

import exceptions.NoRegisterAddressException;

import static controller.Register.RegisterAdresses.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 17.05.14
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class Interrupts {

    private int prevPortA;
    private int prevPortB;
    private Register register;
    private Prescaler prescaler;
    private Stack stack;

    public Interrupts(Register register, Prescaler preScaler, Stack stack) {
        this.register = register;
        this.prescaler = preScaler;
        this.stack = stack;
    }

    //Taster und TMR0
    public void next() throws NoRegisterAddressException {
        int portA = register.getRegValue(PORTA);
        int portB = register.getRegValue(PORTB);
        int prevPortA = this.prevPortA;
        int prevPortB = this.prevPortB;

        //RA4
        if(prevPortA > -1) {
            int RA4 = (register.testBit(portA, 4)) ? 1 : 0;
            int prevRA4 = (register.testBit(prevPortA, 4)) ? 1 : 0;

            if (RA4 != prevRA4) {
                PortRA4Interrupt();
            }
        }
        //RB0
        if (prevPortB > -1) {
            INTInterrupt();
            PortRBInterrupt();
        }
        checkINTInterrupt();
        checkPortRBInterrupt();
        checkTMR0Interrupt();
        this.prevPortA = portA;
        this.prevPortB = portB;
    }

    private void PortRA4Interrupt() throws NoRegisterAddressException {
        //T0CS = 1 -> RA4 T0CKI aktive
        if (register.testBit(register.getRegValue(OPTION_REG), 5)) {
            //edge prüfen
            if (register.testBit(register.getRegValue(OPTION_REG), 4) != register.testBit(register.getRegValue(PORTA), 4)) {
                if (!register.testBit(register.getRegValue(OPTION_REG), 3)) {
                    //TMR0 PreScaler
                    prescaler.incPreScaler();
                } else {
                    //wdt prescaler
                    //TMR0++
                    int value = (register.getRegValue(TMR0) + 1) & 0x0FF;
                    register.setRegValue(TMR0, value);

                    //TMR0 Overflow?
                    if (register.getRegValue(TMR0) == 0x00) {
                        //iT0IF = 1
                        register.setRegValue(INTCON, register.setBit(register.getRegValue(INTCON), 2));
                    }
                }
            }
        }
    }

    private void INTInterrupt() throws NoRegisterAddressException {
        if (register.testBit(this.prevPortB, 0) != register.testBit(register.getRegValue(PORTB), 0)) {
            //steigende/ fallende edge prüfen (if INTEDG == RB0)
            if (register.testBit(register.getRegValue(OPTION_REG), 6) == register.testBit(register.getRegValue(PORTB), 0)) {
                //INTF = 1
                register.setRegValue(INTCON, register.setBit(register.getRegValue(INTCON), 1));
            }
        }
    }

    /**
     * Port RB Interrupt
     */
    private void PortRBInterrupt() throws NoRegisterAddressException {
        if (register.testBit(register.getRegValue(INTCON), 3)) {
            //Port B Pin 4-7
            for (int i = 4; i <= 7; i++) {
                if (register.testBit(this.prevPortB, i) != register.testBit(register.getRegValue(PORTB), i)) {
                    // RBIF = 1
                    register.setRegValue(INTCON, register.setBit(register.getRegValue(INTCON), 0));
                    break;
                }
            }
        }
    }


    /*
     * Prüfen ob ein INT Interrupt aufgetreten ist
     */
    private void checkINTInterrupt() throws NoRegisterAddressException {
        //GIE = 1, INTE = 1 und INTf = 1?
        if (register.testBit(INTCON, 7) && register.testBit(INTCON, 4) && register.testBit(INTCON, 1)) {
            executeInterrupt();
        }
    }

    /*
     * Prüfen ob ein Port RB Interrupt aufgetreten ist
     */
    private void checkPortRBInterrupt() throws NoRegisterAddressException {
        if (register.testBit(INTCON, 0)) {
            //GIE = 1 und RBIE = 1?
            if (register.testBit(INTCON, 7) && register.testBit(INTCON, 3)) {
                executeInterrupt();
            }
        }
    }

    /*
     * Prüfen ob ein TMR0 Interrupt aufgetreten ist
     */
    private void checkTMR0Interrupt() throws NoRegisterAddressException {
        //T0CS != 1 -> timer mode aktive
        if (!register.testBit(register.getRegValue(OPTION_REG), 5)) {
            // TMR0++
            int value = (register.getRegValue(TMR0) + 1) & 0x0FF;
            register.setRegValue(TMR0, value);

            // TMR0 Overflow?
            //register.checkZeroBit(register.getRegValue(TMR0));
            if (register.getRegValue(TMR0) == 0x00) {
                // T0IF = 1
                register.setRegValue(INTCON, register.setBit(register.getRegValue(INTCON), 2));
            }
        }

        // GIE = 1, T0IE = 1 und TOIF = 1
        if (register.testBit(register.getRegValue(INTCON), 7) && register.testBit(register.getRegValue(INTCON), 5) && register.testBit(register.getRegValue(INTCON), 2)) //execute the interrupt
        {
            executeInterrupt();
        }
    }

    /*
     * Interrupt ausführen
     */
    private void executeInterrupt() throws NoRegisterAddressException {
        int intcon = register.getRegValue(INTCON);
        //set GIE to 0
        intcon = register.clearBit(intcon, 7);

        register.setRegValue(INTCON, intcon);
        stack.push(register.getPC());
        register.setPC(0x04);
    }

}
