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

    private static Register register;
    private static Operations operations;
    private static Interrupts interrupts;
    private static Scan scanner;
    private static Pars parser;
    private static MenuBar menubar;


    public static void main(String[] args) throws IOException {
        register = new Register();
        operations = new Operations(register);
        interrupts = new Interrupts(register);
        scanner = new Scan(register);
        parser = new Pars(operations);
        menubar = new MenuBar(parser, scanner);
        new JMainWindow(menubar);
    }

}
