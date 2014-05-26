package view;

import javax.swing.table.AbstractTableModel;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 25.05.14
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class TableModel extends AbstractTableModel {
    public String[][] row = new String[32][32] ;
    public int leiste=0;
    public TableModel() {
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 9; j++) {
                if (j==0) {
                    row[i][j] = Integer.toHexString(leiste);
                    leiste=leiste+8;
                }
                else {
                 row[i][j] ="0";
                }
            }
        }
    }

    public int getColumnCount() {
        return 9;
    }

    public int getRowCount() {
        return row[0].length;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "  ";
            case 1:
                return "00";
            case 2:
                return "01";
            case 3:
                return "02";
            case 4:
                return "03";
            case 5:
                return "04";
            case 6:
                return "05";
            case 7:
                return "06";
            case 8:
                return "07";
            default:
                return null;
        }
    }

    public Object getValueAt(int r, int c) {
        return row[r][c];
    }

    public void setValueAt(Object obj, int r, int c) {
        row[r][c] = (String) obj;
        fireTableDataChanged();
    }

}
