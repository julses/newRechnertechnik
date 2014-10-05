package controller;

import exceptions.NoRegisterAddressException;
import static controller.Register.RegisterAdresses.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 04.10.14
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public class Watchdog {

    private Register register;
    private int wdt;
    private boolean wdtEnable = false;

    public Watchdog(Register register) {
        this.register = register;
    }

    public void setWdtEnable(boolean wdtEnable) {
        this.wdtEnable = wdtEnable;
    }

    public int getWdt() {
        return wdt;
    }

    public void setWdt(int value) {
        wdt = value;
    }

    public void resetWdt() {
        wdt = 0;
    }

    /*
     * WDT erhöhen
     */
    private void incWdt() {
        if (!wdtEnable) {
            return;
        }
        try {
            if (isWatchdog(register.getRegValue(OPTION_REG))) {
                register.incPreScaler();
                } else {
                    wdt++;
                }
            if (wdt == 18000) {
                wdt = 0;
                WdtReset();
            }
        } catch (NoRegisterAddressException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

   /*
    * Prüft ob der WDT dem PreScaler zugewiesen ist
    */
    public boolean isWatchdog(int option) {
        if (register.testBit(option, 3)) {
            return true;
        }
        return false;
    }

    /*
     * Reset der durch den WDT ausgeführt wird
     */
    private void WdtReset() throws NoRegisterAddressException {
        register.setPC(0x0000);
        register.setRegValue(STATUS, register.getRegValue(STATUS) & 0x1F);
        register.setRegValue(INTCON, register.getRegValue(INTCON) & 0xFE);
        register.setRegValue(OPTION_REG, 0xFF);
        register.setRegValue(TRISA, 0xFF);
        register.setRegValue(TRISB, 0xFF);
        register.setRegValue(EECON1, 0x00);
    }

}
