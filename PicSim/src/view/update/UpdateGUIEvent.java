package view.update;

import java.util.EventObject;
/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 25.05.14
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */
public class UpdateGUIEvent extends EventObject{
    private final int type;
    private final int address;
    private final int value;

    public UpdateGUIEvent( Object source, int type, int address, int value )
    {
        super( source );
        this.type = type;
        this.address = address;
        this.value = value;
    }

    public int getAddress() {
        return address;
    }

    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
