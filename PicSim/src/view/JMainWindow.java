package view;

import exceptions.IllegalCarryOperationException;
import exceptions.NoInstructionException;
import model.Scan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 09.04.14
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */

public class JMainWindow implements ActionListener {
    MenuBar MenuBar = new MenuBar();
    private Scan scanner;

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
    JTextArea textarea;

    public JMainWindow() {
        hauptFenster = new JFrame("PicSim 0.0.1");
        container = hauptFenster.getContentPane();
        container.setLayout(new BorderLayout());
        JPanel p = new JPanel();
        container.add(p);

        JMenuBar();

        // Textfeld erzeugen
        textarea = new JTextArea();

        hauptFenster.add(menueLeiste, BorderLayout.NORTH);
        hauptFenster.add(new JScrollPane(textarea), BorderLayout.WEST);
        p.add(stepButton);
        p.add(runButton);
        p.add(stopButton);

        hauptFenster.setSize(400, 300);
        hauptFenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hauptFenster.setVisible(true);
    }

    private void JMenuBar() {
        stepButton = new JButton("Step");
        stepButton.setVisible(true);
        stepButton.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               super.mousePressed(e);
           }
        });
        runButton = new JButton("Run");
        runButton.setVisible(true);
        runButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
            }
        });
        stopButton = new JButton("Stop");
        stopButton.setVisible(true);
        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
            }
        });

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
                MenuBar.step();
            } catch (NoInstructionException e) {
                e.printStackTrace();
            } catch (IllegalCarryOperationException e) {
                e.printStackTrace();
            }
        if (object.getSource() == runButton) System.out.println("runButton pressed");
        if (object.getSource() == stopButton) System.out.println("stopButton pressed");

        if (object.getSource() == oeffnen) {
                try {
                    MenuBar.oeffnen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (object.getSource() == beenden) {
                System.exit(0);
            }
            if (object.getSource() == step) {
                try {
                    MenuBar.step();
                } catch (NoInstructionException e) {
                    e.printStackTrace();
                } catch (IllegalCarryOperationException e) {
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
                MenuBar.ueber();
            }
        }

}