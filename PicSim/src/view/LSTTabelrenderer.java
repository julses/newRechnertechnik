package view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class LSTTabelrenderer extends JLabel implements TableCellRenderer {
    private static final Color Grau = new Color(238, 238, 238);//Grau fuer die Tabellenspalte 1 + Register
    private static final Color Blau = new Color(205,175,149);
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        //TableModel tableModel = table.getModel();

        setOpaque(true);
        setBackground(row < 1000 && column==0? Grau : Color.WHITE);
        setText((value != null)?value.toString():"");
        if (isSelected) {
            this.setBackground(Blau);
        }

        return this;
    }

}
