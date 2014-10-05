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
    public Watchdog watchdog;
    private int preScaler;

    public Prescaler(Register register, Watchdog watchdog) {
        this.register = register;
        this.watchdog = watchdog;
    }

    public void resetPreScaler() {
        preScaler = 0;
    }

    public void incPreScaler() {
        try{
            preScaler++;
            if (preScaler >= getMaxPreScaler()) {
                    if (watchdog.isWatchdog(register.getRegValue(OPTION_REG))) {
                        watchdog.setWdt(watchdog.getWdt()+1);
                    } else {
                        //TMR0++ und Overflow überprüfen
                        int value = (register.getRegValue(TMR0) + 1) & 0x0FF;
                        register.writeThrough(TMR0, value);

                        //check for TMR0 Overflow
                        if (value == 0x00) {
                            //interrupt occured, set T0IF Flag
                            register.setRegValue(INTCON, register.setBit(register.getRegValue(INTCON), 2));
                        }
                    }
                }
                resetPreScaler();
            } catch (NoRegisterAddressException e) {
            e.printStackTrace();
        }
    }


    public int getMaxPreScaler() throws NoRegisterAddressException {
        int value = (register.getRegValue(OPTION_REG) & 0x07);

        if (watchdog.isWatchdog(register.getRegValue(OPTION_REG))) {
            return (int) Math.pow(2, value);
        }
            return (int) Math.pow(2, value) * 2;
    }
}
