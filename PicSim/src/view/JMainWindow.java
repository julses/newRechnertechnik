package view;

import exceptions.NoInstructionException;
import exceptions.NoInstructionFoundException;
import exceptions.NoRegisterAddressException;
import view.update.*;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static controller.Register.RegisterAdresses.*;
import static view.Objects.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 09.04.14
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */

public class JMainWindow implements ActionListener, GUIListener {

    private static final Color Grau = new Color(238, 238, 238);//Grau fuer die Tabellenspalte 1 + Register
    private static int DELAY = 50; // 1/100th second
    public static boolean running;
    public static Timer timer;
    private MenuBar menuBar;
    private ButtonListener buttonListener;
    private RegisterTable model;
    private LstTableModel lstmodel;
    private boolean stepp=false;
    private String[] frequenz={"32,76800 kHz","500,0000 kHz","1,000000 MHz","2,000000 MHz","2,457600 MHz","3,000000 MHz","3,276800 MHz","3,680000 MHz","3,686411 MHz","4,000000 MHz","4,096000 MHz","4,194304 MHz","4,433619 MHz","4,915200 MHz","5,000000 MHz","6,000000 MHz","6,144000 MHz","6,250000 MHz","6,553600 MHz","8,000000 MHz","10,00000 MHz","12,00000 MHz","16,00000 MHz","20,00000 MHz","24,00000 MHz","32,00000 MHz","40,00000 MHz","80,00000 MHz"};
    private double[] frequency={32768, 500000, 1000000, 2000000, 2457600,3000000, 3276800, 3680000, 3686411, 4000000, 4096000, 4194304, 4433619, 4915200, 5000000, 6000000, 6144000, 6250000, 655360, 8000000, 10000000, 12000000, 16000000, 20000000, 24000000, 32000000, 40000000, 80000000};

    public JMainWindow(final MenuBar menuBar) {
        this.menuBar = menuBar;
        buttonListener = new ButtonListener(this, menuBar, running);
        hauptFenster = new JFrame("PicSim 0.1");
        container = hauptFenster.getContentPane();
        container.setLayout(new BorderLayout());
        //GridBagLayout
        GridBagLayout testbag =new GridBagLayout();

        ra = new JPanel();
        ra.setBorder(BorderFactory.createTitledBorder("Benutzerdaten"));

        reg = new JPanel();// Panel für Register Tabelle
        reg.setBorder(BorderFactory.createTitledBorder("Register Übersicht"));
        reg.setPreferredSize(new Dimension(250, 600));

        ra.setLayout(testbag);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.insets=new Insets(2,2,2,2);

        initMenuBar();
        initButtons();
        initPorts();
        initTextfields();


        //Zuordung der Panels dem Container
        container.add(menueLeiste,BorderLayout.PAGE_START);
        container.add(ra,BorderLayout.CENTER);
        container.add(reg,BorderLayout.LINE_START);

        // Textfeld für Lst-File erzeugen
        lstmodel = new view.LstTableModel();
        tablelst = new JTable(lstmodel);

        //
        //Im folgenden wird die Spaltenbreite der Lst-Tabelle zugewiesen;
        //Spalte "BR"= 100Pixel
        //Spalte "Prgrammcode" = 500 Pixel
        //
        tablelst.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int INDEX_COLUMN1 = 0;
        TableColumn col = tablelst.getColumnModel().getColumn(INDEX_COLUMN1);
        col.setPreferredWidth(50);

        INDEX_COLUMN1 = 6;
        col = tablelst.getColumnModel().getColumn(INDEX_COLUMN1);
        col.setPreferredWidth(250);
        tablelst.setDefaultRenderer(Object.class, new LSTTabelrenderer());


        //tablelst.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(tablelst);
        scrollPane.setPreferredSize(new Dimension(700,200));
        scrollPane.setVisible(true);
        setzePos(gbc,0,12,0,0,0,1);
        testbag.setConstraints(scrollPane,gbc);
        ra.add( scrollPane );

        /* Stack-Jtextarea mit Grenze "Stack" dem Scrollfeld stackpane hinzugefügt.
         Danach mit dem JPanel "ra" verknüpft.
         setzePos() legt die Position des Textfeldes in der Anzeige Fest.

        */

        stack= new JTextArea();
        //JScrollPane stackPane = new JScrollPane(stack);
        stack.setEditable(false);
        stack.setPreferredSize(new Dimension(50,160));
        stack.setBorder(BorderFactory.createTitledBorder("Stack"));
        setzePos(gbc,4,0,1,6,0,0);
        testbag.setConstraints(stack,gbc);
        ra.add(stack);
        stack.setVisible(true);

        // Buttons dem Panel hinzufügen
        startStopButton.setPreferredSize(new Dimension(100,20));
        setzePos(gbc,0,0,1,1,0,0);
        testbag.setConstraints(startStopButton, gbc);
        ra.add(startStopButton);

        resetButton.setPreferredSize(new Dimension(100,20));
        setzePos(gbc,0,1,1,1,0,0);
        testbag.setConstraints(resetButton,gbc);
        ra.add(resetButton);

        stepButton.setPreferredSize(new Dimension(100,20));
        setzePos(gbc,0,2,1,1,0,0);
        testbag.setConstraints(stepButton,gbc);
        ra.add(stepButton);

        setzePos(gbc,1,0,1,1,0,0);
        testbag.setConstraints(zeroA,gbc);
        ra.add(zeroA);


        setzePos(gbc,1,1,1,1,0,0);
        testbag.setConstraints(oneA,gbc);
        ra.add(oneA);

        setzePos(gbc,1,2,1,1,0,0);
        testbag.setConstraints(twoA,gbc);
        ra.add(twoA);

        setzePos(gbc,1,3,1,1,0,0);
        testbag.setConstraints(threeA,gbc);
        ra.add(threeA);

        setzePos(gbc,1,4,1,1,0,0);
        testbag.setConstraints(fourA,gbc);
        ra.add(fourA);

        setzePos(gbc,2,0,1,1,0,0);
        testbag.setConstraints(zeroB,gbc);
        ra.add(zeroB);

        setzePos(gbc,2,1,1,1,0,0);
        testbag.setConstraints(oneB,gbc);
        ra.add(oneB);

        setzePos(gbc,2,2,1,1,0,0);
        testbag.setConstraints(twoB,gbc);
        ra.add(twoB);

        setzePos(gbc,2,3,1,1,0,0);
        testbag.setConstraints(threeB,gbc);
        ra.add(threeB);

        setzePos(gbc,2,4,1,1,0,0);
        testbag.setConstraints(fourB,gbc);
        ra.add(fourB);

        setzePos(gbc,2,5,1,1,0,0);
        testbag.setConstraints(fiveB,gbc);
        ra.add(fiveB);

        setzePos(gbc,2,6,1,1,0,0);
        testbag.setConstraints(sixB,gbc);
        ra.add(sixB);


        setzePos(gbc,2,7,1,1,0,0);
        testbag.setConstraints(sevenB,gbc);
        ra.add(sevenB);

        PortA = new JCheckBox[]{zeroA, oneA, twoA, threeA, fourA};
        PortB = new JCheckBox[]{zeroB, oneB, twoB, threeB, fourB, fiveB, sixB, sevenB};

        setzePos(gbc,0,5,1,1,0,0);
        testbag.setConstraints(labelpc,gbc);
        ra.add(labelpc);


        pc = new JTextField("",4);
        pc.setEditable(false);
        pc.setForeground(Color.BLACK);
        pc.setBackground(Grau);
        setzePos(gbc,1,5,1,1,0,0);
        testbag.setConstraints(pc,gbc);
        ra.add(pc);

        setzePos(gbc,0,6,1,1,0,0);
        testbag.setConstraints(labelwreg,gbc);
        ra.add(labelwreg);

        wreg=new JTextField("",4);
        wreg.setEditable(false);
        wreg.setForeground(Color.BLACK);
        wreg.setBackground(Grau);
        setzePos(gbc,1,6,1,1,0,0);
        testbag.setConstraints(wreg,gbc);
        ra.add(wreg);

        setzePos(gbc,0,7,1,1,0,0);
        testbag.setConstraints(labelZ,gbc);
        ra.add(labelZ);

        zerobit = new JTextField("",4);
        zerobit.setEditable(false);
        zerobit.setForeground(Color.black);
        zerobit.setBackground(Grau);
        setzePos(gbc,1,7,1,1,0,0);
        testbag.setConstraints(zerobit,gbc);
        ra.add(zerobit);

        setzePos(gbc,0,8,1,1,0,0);
        testbag.setConstraints(labelDC,gbc);
        ra.add(labelDC);

        dc = new JTextField("",4);
        dc.setEditable(false);
        dc.setForeground(Color.black);
        dc.setBackground(Grau);
        setzePos(gbc,1,8,1,1,0,0);
        testbag.setConstraints(dc,gbc);
        ra.add(dc);

        setzePos(gbc,0,9,1,1,0,0);
        testbag.setConstraints(labelC,gbc);
        ra.add(labelC);

        carry = new JTextField("",4);
        carry.setEditable(false);
        carry.setForeground(Color.black);
        carry.setBackground(Grau);
        setzePos(gbc,1,9,1,1,0,0);
        testbag.setConstraints(carry,gbc);
        ra.add(carry);

        setzePos(gbc,0,10,1,1,0,0);
        testbag.setConstraints(labelDuration,gbc);
        ra.add(labelDuration);

        duration = new JTextField("",4);
        duration.setEditable(false);
        duration.setForeground(Color.black);
        duration.setBackground(Grau);
        setzePos(gbc, 1, 10, 1, 1, 0, 0);
        testbag.setConstraints(duration,gbc);
        ra.add(duration);

        setzePos(gbc,0,11,1,1,0,0);
        testbag.setConstraints(labelFreq,gbc);
        ra.add(labelFreq);

        quarz = new JComboBox(frequenz);
        quarz.setEditable(false);
        quarz.setSelectedIndex(9);
        setzePos(gbc, 1, 11, 1, 1, 0, 0);
        testbag.setConstraints(quarz,gbc);
        ra.add(quarz);


        //Scrollbar und Tabelle für Register
        model = new RegisterTable();
        tablereg = new JTable(model);
        tablereg.setDefaultRenderer(Object.class, new REGTabelRenderer());
                tablereg.setEnabled(false);
        JScrollPane scrolltable = new JScrollPane(tablereg);
        scrolltable.setPreferredSize(new Dimension(250,535));
        scrolltable.setVisible(true);
        reg.add( scrolltable );

        //Hauptfenster mit Attributen ausstatten
        hauptFenster.setSize(1200, 550);
        hauptFenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hauptFenster.pack();//Passt Buttons an
        //hauptFenster.setResizable(false);
        hauptFenster.setVisible(true);
        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                step();
            }
        });
    }

    private void initMenuBar() {
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

    private void initButtons() {
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
    }

    public void initPorts() {
        //Port A
        zeroA = new JCheckBox("RA0");
        zeroA.addActionListener(this);
        oneA = new JCheckBox("RA1");
        oneA.addActionListener(this);
        twoA = new JCheckBox("RA2");
        twoA.addActionListener(this);
        threeA = new JCheckBox("RA3");
        threeA.addActionListener(this);
        fourA = new JCheckBox("RA4");
        fourA.addActionListener(this);
        fiveA = new JCheckBox("RA4");
        fiveA.setVisible(false);
        fiveA.addActionListener(this);
        sixA = new JCheckBox("RA4");
        sixA.setVisible(false);
        sixA.addActionListener(this);
        sevenA = new JCheckBox("RA4");
        sevenA.setVisible(false);
        sevenA.addActionListener(this);
        //PortB
        zeroB = new JCheckBox("RB0");
        zeroB.addActionListener(this);
        oneB = new JCheckBox("RB1");
        oneB.addActionListener(this);
        twoB = new JCheckBox("RB2");
        twoB.addActionListener(this);
        threeB = new JCheckBox("RB3");
        threeB.addActionListener(this);
        fourB = new JCheckBox("RB4");
        fourB.addActionListener(this);
        fiveB = new JCheckBox("RB5");
        fiveB.addActionListener(this);
        sixB = new JCheckBox("RB6");
        sixB.addActionListener(this);
        sevenB = new JCheckBox("RB7");
        sevenB.addActionListener(this);
    }

    private void initTextfields() {
        labelpc =new JLabel("PC:");
        labelSFR=new JLabel("SFR:");
        labelwreg=new JLabel("W:");
        labelZ= new JLabel("Z:");
        labelC=new JLabel("C:");
        labelDC=new JLabel("DC:");
        labelDuration = new JLabel("Laufzeit");
        labelFreq = new JLabel("Quarzfrequenz");
    }

    public void actionPerformed(ActionEvent object) {
        buttonListener.actionPerformed(object);
    }

    //Führt nächsten Befehl im Programm aus
    public void step() {
        try {
            menuBar.step();
        } catch (NoInstructionException e) {
            e.printStackTrace();
        } catch (NoRegisterAddressException e) {
            e.printStackTrace();
        } catch (NoInstructionFoundException e) {
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
        stepp=true;
        this.running = true;
        this.timer.restart();
        startStopButton.setText("Stop");
        startStopButton.setToolTipText("Programm anhalten");
        stepButton.setEnabled(false);
        resetButton.setEnabled(false);
    }

    //Stoppt das Programm
    public void stop() {
        stepp=false;
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

    static void setzePos(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy) {
        gbc.gridx     = gx;
        gbc.gridy     = gy;
        gbc.gridwidth = gw;
        gbc.gridheight= gh;
        gbc.weightx   = wx;
        gbc.weighty   = wy;
    }

    public void setLST(int zeilennr, String label, String comment, String lineNumber, String address, String opcode, String command){
        lstmodel.setValueAt(address,zeilennr,1);
        lstmodel.setValueAt(opcode,zeilennr,2);
        lstmodel.setValueAt(lineNumber,zeilennr,3);
        lstmodel.setValueAt(label,zeilennr,4);
        lstmodel.setValueAt(command,zeilennr,5);
        lstmodel.setValueAt(comment,zeilennr,6);
    }

    /*
     *Setzt die Ports auf In- oder Output
     */
    @Override
    public void update(UpdateGUIPortsIO event) {
        int address = event.getAddress();
        int value = event.getValue();
        if(address == (TRISA | PORTA)) {
            //TRIS A I/O change
            for(int i=0; i<=4; i++) {
                if(menuBar.register.testBit(value, i)) PortA[i].setEnabled(true);
                else PortA[i].setEnabled(false);
            }
        } else {
            //TRIS B I/O change
            for(int i=0; i<=7; i++) {
                if(menuBar.register.testBit(value, i)) PortB[i].setEnabled(true);
                else PortB[i].setEnabled(false);
            }
        }
    }
    
    /*
     *Aktualisiert die Registertabelle mit entsprechenden Werten
     */
    @Override
    public void update(UpdateGUIRegister event) {
        int address = event.getAddress();
        int value = event.getValue();

        //Write Data to register table
        int row = (address/8);
        int column = (address%8)+1;
        String stringValue = Integer.toHexString(event.getValue());
        model.setValueAt(stringValue, row, column);

        //Update Port Output
        if (address == PORTA) {
            //Change checkBox of PortA
            for(int i=0; i<=4; i++) {
                if(menuBar.register.testBit(value, i)) PortA[i].setSelected(true);
                else PortA[i].setSelected(false);
            }
        }
        if (address == PORTB) {
            //Change checkBox of PortB
            for(int i=0; i<=7; i++) {
                if(menuBar.register.testBit(value, i)) PortB[i].setSelected(true);
                else PortB[i].setSelected(false);
            }
        }
    }

    /*
     *Aktualisiert die Felder PC, W, Carry, DigitalCarry, ZeroBit und Laufzeit
     */
    @Override
    public void update(UpdateGUIInfoField event) {
        int value = event.getValue();
        switch (event.getField()) {
            case PC:
                pc.setText(String.valueOf(Integer.toHexString(value)));
                tablelst.changeSelection((buttonListener.lineConverter[value])-1, 0, false, false);
                if (lstmodel.getValueAt((buttonListener.lineConverter[value]-1),0).equals("b")){
                    stop();
                }
                break;
            case W:
                wreg.setText(String.valueOf(Integer.toHexString(value)));
                break;
            case STATUS:
                carry.setText(String.valueOf(menuBar.register.testBit(value, 0)));
                dc.setText(String.valueOf(menuBar.register.testBit(value, 1)));
                zerobit.setText(String.valueOf(menuBar.register.testBit(value, 2)));
                break;
            case Duration:
                double frequency = this.frequency[quarz.getSelectedIndex()];
                double time = (4/frequency)*1000000; //Zeit für einen Cycle
                double durationTime = time * value;
                durationTime = Math.round(durationTime*1000)/1000.0; //Laufzeit wird auf drei Nachkommastellen gerundet
                System.out.println(durationTime + " \u00B5"+"s");
                duration.setText(String.valueOf(durationTime + " \u00B5"+"s"));
        }
    }

    /*
     *Aktualisiert den Stack
     */
    @Override
    public void update(UpdateGUIStack event) {
        int value = event.getValue();
        int index = event.getIndex();
        if(index == -1){
            stack.setText("");
        } else {
            if (event.getWrite()) {
                stack.append(Integer.toHexString(value) + "\n");
            } else {
                int start = 0;
                int end = 0;
                try {
                    start = stack.getLineStartOffset(index -1);
                    end = stack.getLineEndOffset(index -1);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                if(index > 1){
                    //Sorgt dafür, dass das \n auch gelöscht wird.
                    start = start - 1;
                }
                stack.replaceRange(null, start, end);
            }
        }
    }

}
