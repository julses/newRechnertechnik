package controller;

import view.update.GUIListener;
import view.update.UpdateGUIStack;

import javax.swing.event.EventListenerList;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 17.05.14
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public class Stack {

    int pointer;
    int[] stack;
    private EventListenerList listeners;

    public Stack(EventListenerList listeners) {
        pointer = 0;
        stack = new int[8];
        this.listeners = listeners;
    }

    public void push(int value) {
        if (pointer > 7) {
            //Stack voll es wird wieder bei 0 angefangen
            pointer = 0;
        }
        notifyUpdateGUI(new UpdateGUIStack(this, value, pointer, true));
        stack[pointer++] = value & 0x1FFF; //0b0001 1111 1111 11111
    }

    public int pop() {
        if (pointer <= 0)
            //Stack leer, es wird wieder ganz oben angefangen
            pointer = 8;
        notifyUpdateGUI(new UpdateGUIStack(this, pointer, false));
        return stack[--pointer];
    }

    public void clear() {
        stack = new int[8];
        notifyUpdateGUI(new UpdateGUIStack(this, -1, false));
    }

    //Ab hier werden die Eventhandler definiert
    public void addGUIListener( GUIListener listener )
    {
        listeners.add( GUIListener.class, listener );
    }

    protected synchronized void notifyUpdateGUI( UpdateGUIStack event )
    {
        for ( GUIListener l : listeners.getListeners( GUIListener.class ) )
            l.update(event);
    }
}
