package model;

import view.JMainWindow;

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

    public static List<String> hexCode;
    public static List<String> binaryCode;  // Binärcode Liste
    public static Iterator<String> iterator;
    private static Scan scanner;
    private Pars parser;


    public static void main(String[] args) throws IOException {
        hexCode = new ArrayList<String>();
        binaryCode = new ArrayList<String>();
        new JMainWindow();
    }

}
