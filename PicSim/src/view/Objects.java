package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 20.05.14
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class Objects {
    public static String[][] rowData;
    public static String[] columnNames;

    //Hauptelemente
    public static JFrame hauptFenster;
    public static Container container;
    // Buttonliste
    public static JButton stepButton;
    public static JButton startStopButton;
    public static JButton resetButton;

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

    public static JTextField pcl;
    public static JTextField acc;
    public static JTextField wreg;

    public static JLabel labelpcl;
    public static JLabel labelacc;
    public static JLabel labelwreg;
}
