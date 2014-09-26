package view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by saschlick on 26.09.2014.
 */
public class REGTabelRenderer extends JLabel implements TableCellRenderer {
    private static final Color Grau = new Color(238, 238, 238);
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {


            setOpaque(true);
            setBackground(row < 32 && column == 0 ? Grau : Color.WHITE);
            setText((value != null) ? value.toString() : "");
            if (isSelected) {
                this.setBackground(Grau);
            }

            return this;
        }
    }
