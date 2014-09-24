package view;

import exceptions.NoInstructionException;
import exceptions.NoInstructionFoundException;
import exceptions.NoRegisterAddressException;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 09.04.14
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public class MenuBar{

    private Converter convert = new Converter();
    private Scan scanner;
    private Pars parser;
    public Register register;
    private Interrupts interrupts;
    public Path pathToSource;

    public MenuBar(Pars parser, Scan scanner, Register register, Interrupts interrupts) {
        this.parser = parser;
        this.scanner = scanner;
        this.register = register;
        this.interrupts= interrupts;
    }

    public void oeffnen() throws IOException {
        // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser();
        // Dialog zum Oeffnen von Dateien anzeigen
        int rueckgabeWert = chooser.showOpenDialog(null);

        /* Abfrage, ob auf "Öffnen" geklickt wurde */
        if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
            // Ausgabe der ausgewaehlten Datei
            pathToSource = chooser.getSelectedFile().toPath();
            if(pathToSource != null) {
                System.out.println("Die zu öffnende Datei befindet sich hier:\n" + pathToSource);
                scanner.setPathToLSTFile(pathToSource);
                scanner.reader();
            }
        }
    }

    public void ueber() {
        // neues objekt "fenster" von der klasse JFrame erstellen (der text wird
        // als fenster-titel gleich mit übergeben)
        JFrame fenster = new JFrame("Über");

        // gibt an was passieren soll, wenn wir das Fenster schließen (anwendung schließen)
        fenster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // neues objekt "label" von der klasse JLabel erstellen (der anzeigetext wird
        // gleich mit übergeben)
        JLabel label = new JLabel("<HTML><BODY><center>PIC Sim<BR>Version 0.1<BR>Jan Ulses & Sascha Moser</BODY></HTML>", JLabel.CENTER);

        // dem fenster das label hinzufügen
        fenster.getContentPane().add(label);
        // fesntergröße festlegen
        fenster.setSize(200, 150);
        // fenster anzeigen
        fenster.setVisible(true);
    }

    public void step() throws NoInstructionException, NoRegisterAddressException, NoInstructionFoundException {
        interrupts.next();
        //Befehlsstring laden
        String stringopcode = scanner.getOper();
        //String in integer Umwandeln
        int instruction = convert.hexStringToInt(stringopcode);
        //Operation Code (Befehl) decodieren
        int opcode = parser.decode(instruction);
        //Instruction ausführen
        parser.exec(instruction, opcode);
    }

    public void reset() {
        scanner.reset();
    }

    public void openDoku() {
        if (Desktop.isDesktopSupported()) {
            try { Desktop.getDesktop().open(new File("./Dokumente/Doku.pdf")); }
            catch (IOException e1) { e1.printStackTrace(); }
        }
    }

}
