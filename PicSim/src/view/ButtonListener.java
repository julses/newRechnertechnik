package view;

import model.Register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
            open();
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
            portClick(zeroA, Register.PORTA, 0);
        }
        if (object.getSource() == oneA) {
            portClick(oneA, Register.PORTA, 1);
        }
        if (object.getSource() == twoA) {
            portClick(twoA, Register.PORTA, 2);
        }
        if (object.getSource() == threeA) {
            portClick(threeA, Register.PORTA, 3);
        }
        if (object.getSource() == fourA) {
            portClick(fourA, Register.PORTA, 4);
        }
        if (object.getSource() == zeroB) {
            portClick(zeroB, Register.PORTB, 0);
        }
        if (object.getSource() == oneB) {
            portClick(oneB, Register.PORTB, 1);
        }
        if (object.getSource() == twoB) {
            portClick(twoB, Register.PORTB, 2);
        }
        if (object.getSource() == threeB) {
            portClick(threeB, Register.PORTB, 3);
        }
        if (object.getSource() == fourB) {
            portClick(fourB, Register.PORTB, 4);
        }
        if (object.getSource() == fiveB) {
            portClick(fiveB, Register.PORTB, 5);
        }
        if (object.getSource() == sixB) {
            portClick(sixB, Register.PORTB, 6);
        }
        if (object.getSource() == sevenB) {
            portClick(sevenB, Register.PORTB, 7);
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
