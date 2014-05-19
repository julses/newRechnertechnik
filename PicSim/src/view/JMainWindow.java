package view;

import exceptions.NoInstructionException;
import exceptions.NoRegisterAddressException;
import model.PicSim;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 09.04.14
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */

public class JMainWindow implements ActionListener {

    private MenuBar menuBar;

    Timer timer;

    String[][] rowData;

    String[] columnNames;

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

    JFrame hauptFenster;
    Container container;
    // Buttonliste
    JButton stepButton;
    JButton runButton;
    JButton stopButton;

    // Menüleiste
    JMenuBar menueLeiste;
    // Menüleiste Elemente
    JMenu datei;
    JMenu optionen;
    JMenu hilfe;
    // Datei
    JMenuItem oeffnen;
    JMenuItem beenden;
    // Optionen
    JMenuItem step;
    JMenuItem einstellungen;
    // Hilfe
    JMenuItem doku;
    JMenuItem about;
    // Textfeld
    JTextArea lstFile;

    JTextField pcl;
    JTextField acc;
    JTextField wreg;

    JLabel labelpcl;
    JLabel labelacc;
    JLabel labelwreg;

    class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            timer.start();
        }
    }

    class StopListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            timer.stop();
        }
    }

    public JMainWindow(final MenuBar menuBar) {
        this.menuBar = menuBar;
        hauptFenster = new JFrame("PicSim 0.0.1");
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
        two.add(runButton);
        two.add(stopButton);
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

        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    menuBar.step();
                } catch (NoInstructionException e) {
                    e.printStackTrace();
                } catch (NoRegisterAddressException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void JMenuBar() {
        stepButton = new JButton("Step");
        //stepButton.setPreferredSize(new Dimension(10, 10));
        stepButton.setVisible(true);
        stepButton.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               super.mousePressed(e);
           }
        });
        runButton = new JButton("Run");
        //runButton.setVisible(true);
        runButton.addActionListener(new StartListener());
        /*runButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
            }
        });*/
        stopButton = new JButton("Stop");
        //stopButton.setVisible(true);
        stopButton.addActionListener(new StopListener());
        /*
        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
            }
        }); */

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
        einstellungen = new JMenuItem("Einstellungen");
        einstellungen.addActionListener(this);
        doku = new JMenuItem("Dokumentation");
        doku.addActionListener(this);
        about = new JMenuItem("Über");
        about.addActionListener(this);
        stepButton.addActionListener(this);
        runButton.addActionListener(this);
        stopButton.addActionListener(this);

        // Menüelemente hinzufügen
        menueLeiste.add(datei);
        menueLeiste.add(optionen);
        menueLeiste.add(hilfe);

        // Untermenüelemente hinzufügen
        datei.add(oeffnen);
        datei.add(beenden);
        optionen.add(step);
        optionen.add(einstellungen);
        hilfe.add(doku);
        hilfe.add(about);
    }

    public void actionPerformed(ActionEvent object) {
            if (object.getSource() == stepButton) try {
                menuBar.step();
            } catch (NoInstructionException e) {
                e.printStackTrace();
            } catch (NoRegisterAddressException e) {
                e.printStackTrace();
            }
        if (object.getSource() == runButton) {

        }
        if (object.getSource() == stopButton) {
            System.out.println("stopButton pressed");
        }

        if (object.getSource() == oeffnen) {
            try {
                menuBar.oeffnen();
                FileReader fr = new FileReader(String.valueOf(menuBar.pathToSource));
                BufferedReader br = new BufferedReader(fr);

                String zeile = "";
                while ((zeile = br.readLine()) != null)
                {
                    lstFile.read(br,null);
                    zeile = br.readLine();
                }

                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (object.getSource() == beenden) {
                System.exit(0);
            }
            if (object.getSource() == step) {
                try {
                    menuBar.step();
                } catch (NoInstructionException e) {
                    e.printStackTrace();
                } catch (NoRegisterAddressException e) {
                    e.printStackTrace();
                }
            }
            if (object.getSource() == einstellungen) {
                System.out.println("einstellungen wurde angeklickt");
            }
            if (object.getSource() == doku) {
                System.out.println("doku wurde angeklickt");
            }
            if (object.getSource() == about) {
                menuBar.ueber();
            }
        }
    }
}
