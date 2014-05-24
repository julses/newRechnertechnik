package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
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
public class Listener implements ActionListener {

    private final MenuBar menuBar;
    private boolean running;
    private JMainWindow mainWindow;

    public Listener(JMainWindow jMainWindow, MenuBar menuBar, boolean running) {
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
        }
        if (object.getSource() == about) {
            menuBar.ueber();
        }
    }

}
