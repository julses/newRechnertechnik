package view;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 20.05.14
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class Objects {
    public static Object[] rowData;
    public static String[] columnNames;


    //Hauptelemente
    public static JFrame hauptFenster;
    public static Container container;
    // Buttonliste
    public static JButton stepButton;
    public static JButton startStopButton;
    public static JButton resetButton;

    public static JCheckBox zeroA;
    public static JCheckBox oneA;
    public static JCheckBox twoA;
    public static JCheckBox threeA;
    public static JCheckBox fourA;

    public static JCheckBox zeroB;
    public static JCheckBox oneB;
    public static JCheckBox twoB;
    public static JCheckBox threeB;
    public static JCheckBox fourB;
    public static JCheckBox fiveB;
    public static JCheckBox sixB;
    public static JCheckBox sevenB;



    // Menüleiste
    public static JMenuBar menueLeiste;
    // Menüleiste Elemente
    public static JMenu datei;
    public static JMenu optionen;
    public static JMenu hilfe;
    // Datei
    public static JMenuItem oeffnen;
    public static JMenuItem beenden;
    // Optionen
    public static JMenuItem step;
    public static JMenuItem reset;
    public static JMenuItem einstellungen;
    // Hilfe
    public static JMenuItem doku;
    public static JMenuItem about;
    // Textfeld
    public static JTextArea lstFile;

    public static JTextField pc;
    public static JTextField SFR;
    public static JTextField wreg;

    public static JLabel labelpc;
    public static JLabel labelSFR;
    public static JLabel labelwreg;

    public static AbstractTableModel model;
    public static JTable tablereg;
}
