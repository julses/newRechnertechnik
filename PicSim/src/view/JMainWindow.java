package view;

import exceptions.NoInstructionException;
import exceptions.NoRegisterAddressException;
import view.update.GUIListener;
import view.update.UpdateGUIEvent;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static view.Objects.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 09.04.14
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */

public class JMainWindow implements ActionListener, GUIListener {

    private static final int DELAY = 50; // 1/100th second
    public static boolean running;
    public static Timer timer;
    private MenuBar menuBar;
    private ButtonListener buttonListener;
    private view.TableModel model;

    public JMainWindow(final MenuBar menuBar) {
        this.menuBar = menuBar;
        buttonListener = new ButtonListener(this, menuBar, running);
        hauptFenster = new JFrame("PicSim 0.1");
        container = hauptFenster.getContentPane();
        container.setLayout(new BorderLayout());

        GridLayout buttonLayout = new GridLayout(10,1,5,10);// Layout für das Buttonpanel /Spallten/Zeilen /xAbstand/yAbstand

        JPanel ra = new JPanel();
        JPanel rb = new JPanel();
        JPanel pone=new JPanel();//Panel für das Textfeld des Codes
        JPanel two = new JPanel();//Panel für Buttons
        JPanel reg = new JPanel();// Panel für Register Tabelle

        two.setPreferredSize(new Dimension(100,10));
        two.setLayout(buttonLayout);//Panel two bekommt layout für Buttons übergeben

        reg.setPreferredSize(new Dimension(250,300));

        ra.setPreferredSize(new Dimension(20,100));
        rb.setPreferredSize(new Dimension(20,100));
        JMenuBar();

        // Textfeld für Lst-File erzeugen

        lstFile = new JTextArea("Bitte wählen sie eine .LST Datei aus.");//Textarea mit Text erstellen
        JScrollPane scrollPane = new JScrollPane(lstFile);//Scrollbar mit oberfläche verknüpfen
        lstFile.setEnabled(false);//textfeld nich veraenderbar
        scrollPane.setPreferredSize(new Dimension(600, 200));//Scrollbar hinzufügen
        pone.add(scrollPane);//Texfeld mit Scrollbar dem
        scrollPane.setVisible(true);//Alles sichtbar machen

        //Scrollbar und Tabelle für Register

        model = new view.TableModel();
        tablereg = new JTable(model);
        tablereg.setEnabled(false);
        JScrollPane scrolltable = new JScrollPane(tablereg);
        scrolltable.setPreferredSize(new Dimension(250,300));
        scrolltable.setVisible(true);

        JTable ratable= new JTable(3,8);
        JTable rbtable= new JTable(3,8);


        labelpc =new JLabel("PC:");
        labelacc=new JLabel("Accu:");
        labelwreg=new JLabel("W:");

        pc = new JTextField("",5);
        pc.setEnabled(false);

        wreg=new JTextField("",5);
        wreg.setEnabled(false);

        //Panels dem Hauptfenster hinzufügen
        container.add(menueLeiste,BorderLayout.NORTH);
        container.add(ra,BorderLayout.CENTER);
        //container.add(rb,BorderLayout.CENTER);
        container.add(pone,BorderLayout.SOUTH);
        container.add(two,BorderLayout.EAST);
        container.add(reg,BorderLayout.WEST);

        // Buttons dem Panel hinzufügen
        two.add(stepButton);
        two.add(startStopButton);
        two.add(resetButton);
        two.add(stepButton);

        pone.add(scrollPane);//Texfeld mit Scrollbar dem

        //JPanel reg verschiedene Daten hinzufügen
        reg.add( scrolltable );
        reg.add(labelpc);
        reg.add(pc);
        reg.add(labelwreg);
        reg.add(wreg);

        //JPanel P bekommt verschiedene Pins zugewiesen
        ra.add(pinone);
        ra.add(pintwo);
        ra.add(pinthree);
        ra.add(pinfour);
        ra.add(pinfive);
        ra.add(pinsix);
        ra.add(pinseven);
        ra.add(pineight);
        ra.add(ratable);
        ra.add(rbtable);

        //Hauptfenster mit Attributen ausstatten
        hauptFenster.setSize(1024, 620);
        hauptFenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //hauptFenster.pack();//Passt Buttons an
        hauptFenster.setVisible(true);
        setwreg();
        setpcl();
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
        //startStopButton erzeugen und einem ActionListener zuweisen
        startStopButton = new JButton("Start");
        startStopButton.setToolTipText("Programm starten");
        startStopButton.addActionListener(this);
        //resetButton erzeugen und einem ActionListener zuweisen
        resetButton = new JButton("Reset");
        resetButton.setToolTipText("Setzt den PIC auf Standardwerte zurück");
        resetButton.addActionListener(this);
        //stepButton erzeugen und einem ActionListener zuweisen
        stepButton = new JButton("Step");
        stepButton.setToolTipText("Führt nächsten Schritt aus");
        stepButton.addActionListener(this);
        //pinoneButton erzeugen und einem ActionListener zuweisen
        pinone = new JButton("P1.0");
        pinone.setToolTipText("Pin 1.0");
        pinone.addActionListener(this);
        //pintwoButton erzeugen und einem ActionListener zuweisen
        pintwo = new JButton("P1.1");
        pintwo.setToolTipText("Pin 1.1");
        pintwo.addActionListener(this);
        //pinthreeButton erzeugen und einem ActionListener zuweisen
        pinthree = new JButton("P1.2");
        pinthree.setToolTipText("Pin 1.2");
        pinthree.addActionListener(this);
        //pinfourButton erzeugen und einem ActionListener zuweisen
        pinfour = new JButton("P1.3");
        pinfour.setToolTipText("Pin 1.3");
        pinfour.addActionListener(this);
        //pinfiveButton erzeugen und einem ActionListener zuweisen
        pinfive= new JButton("P1.4");
        pinfive.setToolTipText("Pin 1.4");
        pinfive.addActionListener(this);
        //pinsixButton erzeugen und einem ActionListener zuweisen
        pinsix = new JButton("P1.5");
        pinsix.setToolTipText("Pin 1.5");
        pinsix.addActionListener(this);
        //pinsevenButton erzeugen und einem ActionListener zuweisen
        pinseven = new JButton("P1.6");
        pinseven.setToolTipText("Pin 1.6");
        pinseven.addActionListener(this);
        //pineightButton erzeugen und einem ActionListener zuweisen
        pineight = new JButton("P1.7");
        pineight.setToolTipText("Pin 1.7");
        pineight.addActionListener(this);

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
        buttonListener.actionPerformed(object);
    }

    //Führt nächsten Befehl im Programm aus
    public void step() {
        try {
            menuBar.step();
            setwreg();
            setpcl();
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
        stepButton.setEnabled(false);
        resetButton.setEnabled(false);
    }

    //Stoppt das Programm
    public void stop() {
        this.running = false;
        this.timer.stop();
        startStopButton.setText("Start");
        startStopButton.setToolTipText("Programm starten");
        stepButton.setEnabled(true);
        resetButton.setEnabled(true);
    }

    public void loadwindow(){
        hauptFenster.repaint();
    }

    public void setwreg(){
        wreg.setText(String.valueOf(Integer.toHexString(menuBar.register.getW())));
    }

    public void setpcl(){
    pc.setText(String.valueOf(menuBar.register.getPC()));
    }

    @Override
    public void update(UpdateGUIEvent event) {
        int address = event.getAddress();
        int row = (address/8);
        int column = address%8;
        String value = Integer.toHexString(event.getValue());
        model.setValueAt(value, row, column);
    }
}