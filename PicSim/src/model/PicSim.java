package model;

import view.JMainWindow;
import view.MenuBar;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 17.04.14
 * Time: 00:16
 * To change this template use File | Settings | File Templates.
 */
public class PicSim {


    public static void main(String[] args) throws IOException {
        Stack stack = new Stack();
        Register register = new Register(stack);
        Operations operations = new Operations(register, stack);
        Interrupts interrupts = new Interrupts(register);
        Scan scanner = new Scan(register);
        Pars parser = new Pars(operations);
        MenuBar menubar = new MenuBar(parser, scanner, register);
        new JMainWindow(menubar);
    }

}
