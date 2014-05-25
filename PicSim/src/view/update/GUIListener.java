package view.update;

import java.util.EventListener;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 25.05.14
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */
public interface GUIListener extends EventListener{
    void update ( UpdateGUIEvent event);
}
