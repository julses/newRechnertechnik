package view.update;

import java.util.EventObject;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 27.05.14
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class UpdateGUIInfoField extends EventObject {
    private final int field;
    private final int value;

    public UpdateGUIInfoField( Object source, int field, int value )
    {
        super( source );
        this.field = field;
        this.value = value;
    }

    public int getField() {
        return field;
    }

    public int getValue() {
        return value;
    }
}