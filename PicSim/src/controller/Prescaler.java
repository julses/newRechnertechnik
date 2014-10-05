package controller;

import static controller.Register.RegisterAdresses.*;
import exceptions.NoRegisterAddressException;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 04.10.14
 * Time: 16:05
 * To change this template use File | Settings | File Templates.
 */
public class Prescaler {

    private Register register;
    private Watchdog watchdog;
    private int preScaler;

    public Prescaler(Register register, Watchdog watchdog) {
        this.register = register;
        this.watchdog = watchdog;
    }

    public void resetPreScaler() {
        preScaler = 0;
    }

    public void incPreScaler() {
            preScaler++;
            if (preScaler >= getMaxPreScaler()) {
                try {
                    if (!watchdog.isWatchdog(register.getRegValue(OPTION_REG))) {
                        //TMR0++ und Overflow überprüfen
                        int value = (register.getRegValue(TMR0) + 1) & 0x0FF;
                        register.setRegValue(TMR0, value);

                        //check for TMR0 Overflow
                        if (value == 0x00) {
                            //interrupt occured, set T0IF Flag
                            int intcon = register.getRegValue(Register.RegisterAdresses.INTCON);
                            intcon = register.setBit(intcon, 2);
                            register.setRegValue(INTCON, intcon);
                        }
                    } else {
                        watchdog.setWdt(watchdog.getWdt()+1);
                    }
                } catch (NoRegisterAddressException e) {
                    e.printStackTrace();
                }
                resetPreScaler();
            }
    }


    public int getMaxPreScaler() {
        int option = 0;
        try {
            option = register.getRegValue(OPTION_REG);
        } catch (NoRegisterAddressException e) {
            e.printStackTrace();
        }
        int val = option & 0x07;

            if (watchdog.isWatchdog(option)) {
                return (int) Math.pow(2, val);
            } else {
                return (int) Math.pow(2, val) * 2;
            }
    }
}
