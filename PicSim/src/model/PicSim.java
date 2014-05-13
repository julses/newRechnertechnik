package model;

import view.JMainWindow;
import view.MenuBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 17.04.14
 * Time: 00:16
 * To change this template use File | Settings | File Templates.
 */
public class PicSim {

    private static List<String> hexCode;     // Hexcode Liste
    private static List<String> binaryCode;  // Binärcode Liste
    private static Register register;
    private static Operations operations;
    private static Scan scanner;
    private static Pars parser;
    private static MenuBar menubar;


    public static void main(String[] args) throws IOException {
        hexCode = new ArrayList<String>();
        binaryCode = new ArrayList<String>();
        register = new Register();
        operations = new Operations(register);
        scanner = new Scan(binaryCode, hexCode);
        parser = new Pars(operations);
        menubar = new MenuBar(parser, scanner);
        new JMainWindow(menubar);
    }

}
