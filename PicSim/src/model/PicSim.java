package model;

import view.JMainWindow;
import view.MenuBar;

import javax.swing.event.EventListenerList;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 17.04.14
 * Time: 00:16
 * To change this template use File | Settings | File Templates.
 */
public class PicSim {

    public static void main(String[] args) throws IOException {
        EventListenerList guiListener = new EventListenerList();
        Stack stack = new Stack(guiListener);
        Register register = new Register(stack, guiListener);
        Instructions instructions = new Instructions(register, stack);
        Interrupts interrupts = new Interrupts(register);
        Scan scanner = new Scan(register);
        Pars parser = new Pars(instructions);
        MenuBar menubar = new MenuBar(parser, scanner, register);
        register.addGUIListener(new JMainWindow(menubar));
    }

}