package view;

import exceptions.NoRegisterAddressException;
import model.Register;

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
                try {
                    Desktop.getDesktop().open(new File("./Dokumente/pic16f84A.pdf"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (object.getSource() == about) {
            menuBar.ueber();
        }

        if (object.getSource() == zeroA) {
            boolean selected;
            if(zeroA.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTA, selected, 0);
        }
        if (object.getSource() == oneA) {
            boolean selected;
            if(oneA.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTA, selected, 1);
        }
        if (object.getSource() == twoA) {
            boolean selected;
            if(twoA.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTA, selected, 2);
        }
        if (object.getSource() == threeA) {
            boolean selected;
            if(threeA.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTA, selected, 3);
        }
        if (object.getSource() == fourA) {
            boolean selected;
            if(fourA.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTA, selected, 4);
        }
        if (object.getSource() == zeroB) {
            boolean selected;
            if(zeroB.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTB, selected, 0);
        }
        if (object.getSource() == oneB) {
            boolean selected;
            if(oneB.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTB, selected, 1);
        }
        if (object.getSource() == twoB) {
            boolean selected;
            if(twoB.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTB, selected, 2);
        }
        if (object.getSource() == threeB) {
            boolean selected;
            if(threeB.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTB, selected, 3);
        }
        if (object.getSource() == fourB) {
            boolean selected;
            if(fourB.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTB, selected, 4);
        }
        if (object.getSource() == fiveB) {
            boolean selected;
            if(fiveB.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTB, selected, 5);
        }
        if (object.getSource() == sixB) {
            boolean selected;
            if(sixB.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTB, selected, 6);
        }
        if (object.getSource() == sevenB) {
            boolean selected;
            if(sevenB.isSelected()) selected=true;
            else selected=false;
            menuBar.register.setPort(Register.PORTB, selected, 7);
        }
    }
}
