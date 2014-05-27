package view.update;

import java.util.EventObject;
/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 25.05.14
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */
public class UpdateGUIRegisterEvent extends EventObject{
    private final int address;
    private final int value;

    public UpdateGUIRegisterEvent(Object source, int address, int value)
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
