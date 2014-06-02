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
    private int value;
    private final int index;
    private final boolean write;

    public UpdateGUIStack(Object source, int value, int index, boolean write)
    {
        super( source );
        this.value = value;
        this.index = index;
        this.write = write;
    }

    public UpdateGUIStack(Object source, int index, boolean write)
    {
        super( source );
        this.index = index;
        this.write = write;
    }

    public int getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public boolean getWrite() {
        return write;
    }
}
