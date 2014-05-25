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
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTA);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(zeroA.isSelected()==true) value = menuBar.register.setBit(value, 0);
            else value =menuBar.register.clearBit(value, 0);
            try {menuBar.register.setRegValue(Register.PORTA, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
        if (object.getSource() == oneA) {
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTA);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(oneA.isSelected()==true) value = menuBar.register.setBit(value, 1);
            else value =menuBar.register.clearBit(value, 1);
            try {menuBar.register.setRegValue(Register.PORTA, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
        if (object.getSource() == twoA) {
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTA);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(twoA.isSelected()==true) value = menuBar.register.setBit(value, 2);
            else value =menuBar.register.clearBit(value, 2);
            try {menuBar.register.setRegValue(Register.PORTA, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
        if (object.getSource() == threeA) {
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTA);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(threeA.isSelected()==true) value = menuBar.register.setBit(value, 3);
            else value =menuBar.register.clearBit(value, 3);
            try {menuBar.register.setRegValue(Register.PORTA, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
        if (object.getSource() == fourA) {
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTA);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(fourA.isSelected()==true) value = menuBar.register.setBit(value, 4);
            else value =menuBar.register.clearBit(value, 4);
            try {menuBar.register.setRegValue(Register.PORTA, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
        if (object.getSource() == zeroB) {
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTB);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(zeroB.isSelected()==true) value = menuBar.register.setBit(value, 0);
            else value =menuBar.register.clearBit(value, 0);
            try {menuBar.register.setRegValue(Register.PORTB, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
        if (object.getSource() == oneB) {
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTB);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(oneB.isSelected()==true) value = menuBar.register.setBit(value, 1);
            else value =menuBar.register.clearBit(value, 1);
            try {menuBar.register.setRegValue(Register.PORTB, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
        if (object.getSource() == twoB) {
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTB);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(twoB.isSelected()==true) value = menuBar.register.setBit(value, 2);
            else value =menuBar.register.clearBit(value, 2);
            try {menuBar.register.setRegValue(Register.PORTB, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
        if (object.getSource() == threeB) {
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTB);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(threeB.isSelected()==true) value = menuBar.register.setBit(value, 3);
            else value =menuBar.register.clearBit(value, 3);
            try {menuBar.register.setRegValue(Register.PORTB, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
        if (object.getSource() == fourB) {
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTB);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(fourB.isSelected()==true) value = menuBar.register.setBit(value, 4);
            else value =menuBar.register.clearBit(value, 4);
            try {menuBar.register.setRegValue(Register.PORTB, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
        if (object.getSource() == fiveB) {
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTB);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(fiveB.isSelected()==true) value = menuBar.register.setBit(value, 5);
            else value =menuBar.register.clearBit(value, 5);
            try {menuBar.register.setRegValue(Register.PORTB, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
        if (object.getSource() == sixB) {
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTB);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(sixB.isSelected()==true) value = menuBar.register.setBit(value, 6);
            else value =menuBar.register.clearBit(value, 6);
            try {menuBar.register.setRegValue(Register.PORTB, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
        if (object.getSource() == sevenB) {
            int value = 0;
            try { value = menuBar.register.getRegValue(Register.PORTB);}
            catch (NoRegisterAddressException e) { e.printStackTrace();}
            if(sevenB.isSelected()==true) value = menuBar.register.setBit(value, 7);
            else value =menuBar.register.clearBit(value, 7);
            try {menuBar.register.setRegValue(Register.PORTB, value);}
            catch (NoRegisterAddressException e) {e.printStackTrace();}
        }
    }
}
