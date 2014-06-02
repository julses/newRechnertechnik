package view.update;

import java.util.EventObject;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 30.05.14
 * Time: 00:36
 * To change this template use File | Settings | File Templates.
 */
public class UpdateGUIStack extends EventObject {
    //private final int address;
    //private final int value;

    public UpdateGUIStack(Object source, int address, int value)
    {
        super( source );
        //this.address = address;
        //this.value = value;
    }
/*
    public int getAddress() {
        return address;
    }

    public int getValue() {
        return value;
    }
*/
}
