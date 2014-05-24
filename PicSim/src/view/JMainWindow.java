package view;

import exceptions.NoInstructionException;
import exceptions.NoRegisterAddressException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static view.Objects.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 09.04.14
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */

public class JMainWindow implements ActionListener {

    private static final int DELAY = 100; // 1/100th second
    public static boolean running;
    public static Timer timer;
    private MenuBar menuBar;
    private Listener listener;

    {
        columnNames = new String[]{
                "  ","00", "01","02","03","04","05","06","07"
        };
        rowData = new String[][]{

                { "00","0F","1","2","3","4","5","6","7" },
                { "08","00","1","2","3","4","5","6","7" },
                { "10","00","1","2","3","4","5","6","7" },
                { "18","00","1","2","3","4","5","6","7" },
                { "20","00","1","2","3","4","5","6","7" },
                { "28","00","1","2","3","4","5","6","7" },
                { "30","00","1","2","3","4","5","6","7" },
                { "38","00","1","2","3","4","5","6","7" },
                { "40","00","1","2","3","4","5","6","7" },
                { "48","00","1","2","3","4","5","6","7" },
                { "50","0F","1","2","3","4","5","6","7" },
                { "00","00","1","2","3","4","5","6","7" },
                { "00","00","1","2","3","4","5","6","7" },
                { "00","00","1","2","3","4","5","6","7" },

                { "00","0F","1","2","3","4","5","6","7" },
                { "00","0F","1","2","3","4","5","6","7" },
                { "00","0F","1","2","3","4","5","6","7" },
                { "00","0F","1","2","3","4","5","6","7" },
                { "00","0F","1","2","3","4","5","6","7" },
                { "00","0F","1","2","3","4","5","6","7" },
                { "00","0F","1","2","3","4","5","6","7" },
                { "00","0F","1","2","3","4","5","6","7" },
                { "00","0F","1","2","3","4","5","6","7" },
                { "00","0F","1","2","3","4","5","6","7" },
                { "00","0F","1","2","3","4","5","6","7" },
                { "00","0F","1","2","3","4","5","6","7" },
                { "00","0F","1","2","3","4","5","6","7" },


        };
    }

    public JMainWindow(final MenuBar menuBar) {
        this.menuBar = menuBar;
        listener = new Listener(this, menuBar, running);
        hauptFenster = new JFrame("PicSim 0.1");
        container = hauptFenster.getContentPane();
        container.setLayout(new BorderLayout());

        GridLayout buttonLayout = new GridLayout(10,1,5,10);// Layout für das Buttonpanel /Spallten/Zeilen /xAbstand/yAbstand

        JPanel p = new JPanel();
        JPanel pone=new JPanel();//Panel für das Textfeld des Codes
        JPanel two = new JPanel();//Panel für Buttons
                JPanel reg = new JPanel();// Panel für Register Tabelle
        two.setPreferredSize(new Dimension(100,10));
        two.setLayout(buttonLayout);//Panel two bekommt layout für Buttons übergeben

        reg.setPreferredSize(new Dimension(250,300));
        JMenuBar();

        // Textfeld für Lst-File erzeugen

        lstFile = new JTextArea("Bitte wählen sie eine .LST Datei aus.");//Textarea mit Text erstellen
        JScrollPane scrollPane = new JScrollPane(lstFile);//Scrollbar mit oberfläche verknüpfen
        lstFile.setEnabled(false);//textfeld nich veraenderbar
        scrollPane.setPreferredSize(new Dimension(600, 200));//Scrollbar hinzufügen
        pone.add(scrollPane);//Texfeld mit Scrollbar dem
        scrollPane.setVisible(true);//Alles sichtbar machen

        //Scrollbar und Tabelle für Register

        JTable tablereg = new JTable( rowData, columnNames );
        tablereg.setEnabled(false);
        JScrollPane scrolltable = new JScrollPane(tablereg);
        scrolltable.setPreferredSize(new Dimension(250,300));
        scrolltable.setVisible(true);

        labelpcl=new JLabel("Pcl:");
        labelacc=new JLabel("Accu:");
        labelwreg=new JLabel("W:");

        pcl= new JTextField("",5);
        pcl.setEnabled(false);

        wreg=new JTextField("",5);
        wreg.setEnabled(false);

        //Panels dem Hauptfenster hinzufügen
        container.add(menueLeiste,BorderLayout.NORTH);
        container.add(p,BorderLayout.CENTER);
        container.add(pone,BorderLayout.SOUTH);
        container.add(two,BorderLayout.EAST);
        container.add(reg,BorderLayout.WEST);

        // Buttons dem Panel hinzufügen
        two.add(stepButton);
        two.add(startStopButton);
        two.add(resetButton);
        two.add(stepButton);

        pone.add(scrollPane);//Texfeld mit Scrollbar dem

        //JPanel P verschiedene Daten hinzufügen
        reg.add( scrolltable );
        reg.add(labelpcl);
        reg.add(pcl);
        reg.add(labelwreg);
        reg.add(wreg);


        //labelpcl.setLabelFor(pcl);
        //Hauptfenster mit Attributen ausstatten
        hauptFenster.setSize(1024, 620);
        hauptFenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //hauptFenster.pack();//Passt Buttons an
        hauptFenster.setVisible(true);

        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                step();
            }
        });
    }

    private void JMenuBar() {

        // Menüleiste erzeugen
        menueLeiste = new JMenuBar();

        // Menüelemente erzeugen
        datei = new JMenu("Datei");
        optionen = new JMenu("Optionen");
        hilfe = new JMenu("Hilfe");


        // Untermenüelemente erzeugen
        oeffnen = new JMenuItem("Öffnen");
        oeffnen.addActionListener(this);
        beenden = new JMenuItem("Beenden");
        beenden.addActionListener(this);
        step = new JMenuItem("Step");
        step.addActionListener(this);
        reset = new JMenuItem("Reset PIC");
        reset.addActionListener(this);
        einstellungen = new JMenuItem("Einstellungen");
        einstellungen.addActionListener(this);
        doku = new JMenuItem("Dokumentation");
        doku.addActionListener(this);
        about = new JMenuItem("Über");
        about.addActionListener(this);
        startStopButton = new JButton("Start");
        startStopButton.setToolTipText("Programm starten");
        startStopButton.addActionListener(this);
        resetButton = new JButton("Reset");
        resetButton.setToolTipText("Setzt den PIC auf Standardwerte zurück");
        resetButton.addActionListener(this);
        stepButton = new JButton("Step");
        stepButton.setToolTipText("Führt nächsten Schritt aus");
        stepButton.addActionListener(this);

        // Menüelemente hinzufügen
        menueLeiste.add(datei);
        menueLeiste.add(optionen);
        menueLeiste.add(hilfe);

        // Untermenüelemente hinzufügen
        datei.add(oeffnen);
        datei.add(beenden);
        optionen.add(step);
        optionen.add(reset);
        optionen.add(einstellungen);
        hilfe.add(doku);
        hilfe.add(about);
    }

    public void actionPerformed(ActionEvent object) {
        listener.actionPerformed(object);
    }

    //Führt nächsten Befehl im Programm aus
    public void step() {
        try {
            menuBar.step();
        } catch (NoInstructionException e) {
            e.printStackTrace();
        } catch (NoRegisterAddressException e) {
            e.printStackTrace();
        }
    }

    //Prüft ob Programm läuft. Wenn nicht startet es...
    public void toggleRunning() {
        if (this.running) {
            stop();
        } else {
            start();
        }
    }

    //Startet das Programm
    public void start() {
        this.running = true;
        this.timer.restart();
        startStopButton.setText("Stop");
        startStopButton.setToolTipText("Programm anhalten");
    }

    //Stoppt das Programm
    public void stop() {
        this.running = false;
        this.timer.stop();
        startStopButton.setText("Start");
        startStopButton.setToolTipText("Programm starten");
    }
}
