package view.update;

import java.util.EventObject;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 27.05.14
 * Time: 10:36
 * To change this template use File | Settings | File Templates.
 */
public class UpdateGUIPortsIO extends EventObject {
    private final int address;
    private final int value;

    public UpdateGUIPortsIO(Object source, int address, int value)
    {
        super( source );
        this.address = address;
        this.value = value;
    }

    public int getAddress() {
        return address;
    }

    public int getValue() {
        return value;
    }
}