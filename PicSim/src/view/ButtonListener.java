package view;

import javax.swing.*;

import static model.Register.Adresses.*;

import java.awt.*;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static view.Objects.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 20.05.14
 * Time: 11:06
 * To change this template use File | Settings | File Templates.
 */
public class ButtonListener implements ActionListener {

    private final MenuBar menuBar;
    private boolean running;
    private String pc;
    private String lineNum;
    private String comment;
    private LineNumberReader lnr =null;
    private LineNumberReader lnr1 =null;
    public  int linenumber;
    public int linie=0;

    private JMainWindow mainWindow;

    public ButtonListener(JMainWindow jMainWindow, MenuBar menuBar, boolean running) {
        this.menuBar = menuBar;
        this.running = running;
        this.mainWindow = jMainWindow;
    }

    public void actionPerformed(ActionEvent object) {
        if (object.getSource() == stepButton) {
            mainWindow.step();
        }

        if (object.getSource() == startStopButton){
            mainWindow.toggleRunning();
        }

        if (object.getSource() == resetButton) {
            if (this.running)
            {
                mainWindow.stop();
            }
            menuBar.reset();
            mainWindow.loadwindow();
        }

        if (object.getSource() == oeffnen) {
            try {
                menuBar.oeffnen();
                FileReader fr = new FileReader(String.valueOf(menuBar.pathToSource));
                BufferedReader br = new BufferedReader(fr);
                lnr = new LineNumberReader(br);
                String line;
                int linenr = 0;
                while ((line = lnr.readLine()) != null) {
                    linenr = lnr.getLineNumber();

                }
                linie =linenr;
                System.out.println(linie);

                br.close();
                FileReader fr1 = new FileReader(String.valueOf(menuBar.pathToSource));
                BufferedReader br1 = new BufferedReader(fr1);
                lnr1 = new LineNumberReader(br1);
                String zeile ;
                int linenr1;
                while ((zeile = lnr1.readLine())!=null) {

                    String address = "";
                    String label = "";
                    String opcode = "";
                    String comment = "";
                    String command = "";
                    String lineNumber = "";

                    // fuehrende Whitespaces entfernen und nach Whitespaces splitten
                    String[] splited = zeile.trim().split("(\\s)+");
                    while (true) {

                        // wenn es eine Zeile ohne Binaercode ist, ist Zeilennummer
                        // der erste Block des Strings
                        if (splited[0].length() == 5
                                && splited[0].matches("(\\d){5}")) {

                            lineNumber = splited[0];

                            // wenn die Laenge = 2 ist, muss zweite Stelle ein
                            // Kommentar oder ein Label sein ->
                            // deshalb ueberpruefung auf Kommentar -> wenn nicht als
                            // Label speichern
                            if (splited.length == 2
                                    && !(splited[1].startsWith(";"))) {
                                label = splited[1];
                            }

                            break;

                            // wenn es eine Zeile mit Binarcode ist, sind Teile 1, 2
                            // und 3 klar.
                        } else if (splited[0].length() == 4) {
                            address = splited[0];
                            opcode = splited[1];
                            lineNumber = splited[2];
                            break;
                        }
                    }

                    // StringBuilder fuer die Kombo aus Befehl & Kommentar
                    StringBuilder cAndCBuilder = new StringBuilder();

                    for (int l = 1; l < splited.length; l++) {
                        if (splited.length > 2) {
                            // wenn Assemblercodezeile -> haenge alles ab Stelle 4
                            // an
                            if (l > 2 && splited[0].length() == 4) {
                                cAndCBuilder.append(splited[l] + " ");

                                // wenn anderes Codestueck -> haenge alles ab Stelle
                                // 2 an
                            } else if (splited[0].length() == 5) {
                                cAndCBuilder.append(splited[l] + " ");
                            }
                        }
                    }

                    // String aus Befehl & Kommentar erzeugen
                    String comAndCom = cAndCBuilder.toString();

                    // enthaelt der Kombo-String einen Kommentar: aufteilen und
                    // entsprechend speichern
                    if (comAndCom.contains(";")) {
                        comment = comAndCom.substring(comAndCom.indexOf(";"));
                        command = comAndCom.replace(comment, "");

                        // ansonsten ist alles Befehl -> speichern
                    } else {
                        command = comAndCom;
                    }

                    // Codezeile erzeugen
                    String ergebnis = address+" "+ opcode+" "+lineNumber+" "+label+" "+ command+" "+comment;

                    String pc= String.valueOf(menuBar.register.getPC());
                    if(label.equals("start")){linenumber=Integer.parseInt(lineNumber);}
                    linenr1 =lnr1.getLineNumber();
                    linenr1--;
                    mainWindow.setLST(ergebnis,(linenr1));
                    zeile = br1.readLine();
                }
                br1.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if (object.getSource() == beenden) {
            System.exit(0);
        }

        if (object.getSource() == step) {
            if (this.running) mainWindow.stop();
            mainWindow.step();
        }
        if (object.getSource() == reset) {
            if (this.running) mainWindow.stop();
            menuBar.reset();
        }
        if (object.getSource() == einstellungen) {
            System.out.println("einstellungen wurde angeklickt");
        }
        if (object.getSource() == doku) {
            System.out.println("doku wurde angeklickt");
            if (Desktop.isDesktopSupported()) {
                try { Desktop.getDesktop().open(new File("./Dokumente/pic16f84A.pdf")); }
                catch (IOException e1) { e1.printStackTrace(); }
            }
        }
        if (object.getSource() == about) {
            menuBar.ueber();
        }

        if (object.getSource() == zeroA) {
            portClick(zeroA, PORTA, 0);
        }
        if (object.getSource() == oneA) {
            portClick(oneA, PORTA, 1);
        }
        if (object.getSource() == twoA) {
            portClick(twoA, PORTA, 2);
        }
        if (object.getSource() == threeA) {
            portClick(threeA, PORTA, 3);
        }
        if (object.getSource() == fourA) {
            portClick(fourA, PORTA, 4);
        }
        if (object.getSource() == zeroB) {
            portClick(zeroB, PORTB, 0);
        }
        if (object.getSource() == oneB) {
            portClick(oneB, PORTB, 1);
        }
        if (object.getSource() == twoB) {
            portClick(twoB, PORTB, 2);
        }
        if (object.getSource() == threeB) {
            portClick(threeB, PORTB, 3);
        }
        if (object.getSource() == fourB) {
            portClick(fourB, PORTB, 4);
        }
        if (object.getSource() == fiveB) {
            portClick(fiveB, PORTB, 5);
        }
        if (object.getSource() == sixB) {
            portClick(sixB, PORTB, 6);
        }
        if (object.getSource() == sevenB) {
            portClick(sevenB, PORTB, 7);
        }
    }

    private void open() {
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
    }

    private void portClick(JCheckBox PortButton, int addresse, int number) {
        boolean selected;
        if(PortButton.isSelected()) selected=true;
        else selected=false;
        menuBar.register.setPort(addresse, selected, number);
    }

}
