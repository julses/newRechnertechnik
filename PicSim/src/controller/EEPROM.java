package controller;

import exceptions.NoRegisterAddressException;

import static controller.Register.RegisterAdresses.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 05.10.14
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public class EEPROM {

    private final int WRITE_TIME = 500;
    private int eeprom[];
    private int state;
    private Register register;

    public EEPROM(Register register) {
        this.register = register;
        this.eeprom = new int[0x3F + 1];
    }

    public int read(int addr) {
        return eeprom[addr];
    }

    public void write(int addr, int val) {
        Thread writer = new Thread(new Writer(addr, val));
        writer.start();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /*
     * EEPROM Writer, wartet <code>WRITE_TIME</code> bis ins EEPROM geschrieben
     * wird.
     */
    private class Writer implements Runnable {

        private int addr;
        private int val;

        public Writer(int addr, int val) {
            this.addr = addr;
            this.val = val;
        }

        public void run() {
            try {
                Thread.sleep(WRITE_TIME);
                eeprom[addr] = val;
                // EEIF setzen
                register.setRegValue(EECON1, register.setBit(register.getRegValue(EECON1), 4));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NoRegisterAddressException e) {
                e.printStackTrace();
            }
        }
    };
}

