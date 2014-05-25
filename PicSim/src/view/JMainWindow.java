package view;

import exceptions.NoInstructionException;
import exceptions.NoRegisterAddressException;
import model.Register;
import sun.security.acl.GroupImpl;
import view.update.GUIListener;
import view.update.UpdateGUIEvent;

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
        GridLayout check=new GridLayout(8,4);
        JPanel ra = new JPanel();
        JPanel pone=new JPanel();//Panel für das Textfeld des Codes
        JPanel two = new JPanel();//Panel für Buttons
        JPanel reg = new JPanel();// Panel für Register Tabelle

        two.setPreferredSize(new Dimension(100, 10));
        two.setLayout(buttonLayout);//Panel two bekommt layout für Buttons übergeben

        reg.setPreferredSize(new Dimension(250, 300));
        ra.setLayout(check);
        ra.setPreferredSize(new Dimension(250,300));

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



        labelpc =new JLabel("PC:");
        labelSFR=new JLabel("SFR:");
        labelwreg=new JLabel("W:");

        SFR = new JTextField("",5);
        SFR.setEnabled(false);

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
        reg.add(labelSFR);
        reg.add(SFR);

        //JPanel P bekommt verschiedene Pins zugewiesen
        ra.add(zeroA);
        ra.add(oneA);
        ra.add(twoA);
        ra.add(threeA);
        ra.add(fourA);
        ra.add(zeroB);
        ra.add(oneB);
        ra.add(twoB);
        ra.add(threeB);
        ra.add(fourB);
        ra.add(fiveB);
        ra.add(sixB);
        ra.add(sevenB);


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
        zeroA = new JCheckBox("RA0");
        zeroA.addActionListener(this);
        //pinoneButton erzeugen und einem ActionListener zuweisen
        oneA = new JCheckBox("RA1");
        oneA.addActionListener(this);
       //pintwoButton erzeugen und einem ActionListener zuweisen
        twoA = new JCheckBox("RA2");
        twoA.addActionListener(this);
        //pinthreeButton erzeugen und einem ActionListener zuweisen
        threeA = new JCheckBox("RA3");
        threeA.addActionListener(this);
        //pinfourButton erzeugen und einem ActionListener zuweisen
        fourA = new JCheckBox("RA4");
        fourA.addActionListener(this);
        //pinfiveButton erzeugen und einem ActionListener zuweisen
        zeroB = new JCheckBox("RB0");
        zeroB.addActionListener(this);
        //pinsixButton erzeugen und einem ActionListener zuweisen
        oneB = new JCheckBox("RB1");
        oneB.addActionListener(this);
        //pinsevenButton erzeugen und einem ActionListener zuweisen
        twoB = new JCheckBox("RB2");
        twoB.addActionListener(this);

        threeB = new JCheckBox("RB3");
        threeB.addActionListener(this);
        //pineightButton erzeugen und einem ActionListener zuweisen
        fourB = new JCheckBox("RB4");
        fourB.addActionListener(this);

        fiveB = new JCheckBox("RB5");
        fiveB.addActionListener(this);

        sixB = new JCheckBox("RB6");
        sixB.addActionListener(this);

        sevenB = new JCheckBox("RB7");
        sevenB.addActionListener(this);


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
            setSFR();
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
    public void setSFR() throws NoRegisterAddressException {
        SFR.setText(String.valueOf(menuBar.register.getRegValue(Register.FSR)));
    }

    @Override
    public void update(UpdateGUIEvent event) {
        int address = event.getAddress();
        int value = event.getValue();
        if (event.getCheckIO()) {
            checkTris(address, value);
        } else {
            if(address == (Register.PORTA | Register.PORTB)) checkPorts(address, value);
            int row = (address/8);
            int column = (address%8)+1;
            String stringValue = Integer.toHexString(event.getValue());
            model.setValueAt(stringValue, row, column);
        }
    }

    private void checkTris(int address, int value) {
        if(address == Register.TRISA) {
            //TRIS A I/O change
            if(menuBar.register.testBit(value, 0)) zeroA.setEnabled(true);
            else zeroA.setEnabled(false);
            if(menuBar.register.testBit(value, 1)) oneA.setEnabled(true);
            else oneA.setEnabled(false);
            if(menuBar.register.testBit(value, 2)) twoA.setEnabled(true);
            else twoA.setEnabled(false);
            if(menuBar.register.testBit(value, 3)) threeA.setEnabled(true);
            else threeA.setEnabled(false);
            if(menuBar.register.testBit(value, 4)) fourA.setEnabled(true);
            else fourA.setEnabled(false);
        } else {
            //TRIS B I/O change
            if(menuBar.register.testBit(value, 0)) zeroB.setEnabled(true);
            else zeroB.setEnabled(false);
            if(menuBar.register.testBit(value, 1)) oneB.setEnabled(true);
            else oneB.setEnabled(false);
            if(menuBar.register.testBit(value, 2)) twoB.setEnabled(true);
            else twoB.setEnabled(false);
            if(menuBar.register.testBit(value, 3)) threeB.setEnabled(true);
            else threeB.setEnabled(false);
            if(menuBar.register.testBit(value, 4)) fourB.setEnabled(true);
            else fourB.setEnabled(false);
            if(menuBar.register.testBit(value, 4)) fourB.setEnabled(true);
            else fourB.setEnabled(false);
            if(menuBar.register.testBit(value, 4)) fourB.setEnabled(true);
            else fourB.setEnabled(false);
            if(menuBar.register.testBit(value, 4)) fourB.setEnabled(true);
            else fourB.setEnabled(false);
        }
    }

    private void checkPorts(int address, int value) {
        if(address == Register.PORTA) {
            //PORT A change checkBox
            if(menuBar.register.testBit(value, 0)) zeroA.setSelected(true);
            else zeroA.setSelected(false);
            if(menuBar.register.testBit(value, 1)) oneA.setSelected(true);
            else oneA.setSelected(false);
            if(menuBar.register.testBit(value, 2)) twoA.setSelected(true);
            else twoA.setSelected(false);
            if(menuBar.register.testBit(value, 3)) threeA.setSelected(true);
            else threeA.setSelected(false);
            if(menuBar.register.testBit(value, 4)) fourA.setSelected(true);
            else fourA.setSelected(false);
        } else {
            if(menuBar.register.testBit(value, 0)) zeroB.setSelected(true);
            else zeroB.setSelected(false);
            if(menuBar.register.testBit(value, 1)) oneB.setSelected(true);
            else oneB.setSelected(false);
            if(menuBar.register.testBit(value, 2)) twoB.setSelected(true);
            else twoB.setSelected(false);
            if(menuBar.register.testBit(value, 3)) threeB.setSelected(true);
            else threeB.setSelected(false);
            if(menuBar.register.testBit(value, 4)) fourB.setSelected(true);
            else fourB.setSelected(false);
            if(menuBar.register.testBit(value, 5)) fiveB.setSelected(true);
            else fiveB.setSelected(false);
            if(menuBar.register.testBit(value, 6)) sixB.setSelected(true);
            else sixB.setSelected(false);
            if(menuBar.register.testBit(value, 7)) sevenB.setSelected(true);
            else sevenB.setSelected(false);
        }
    }
}
