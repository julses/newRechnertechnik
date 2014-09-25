package view;

import javax.swing.table.AbstractTableModel;

public class LstTableModel extends AbstractTableModel {
    public String[][] row = new String[1000][1000];
    public LstTableModel() {
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j <7; j++) {
                if (j==0) {
                    row[i][j] ="";
                                    }
                else {
                    row[i][j] ="";
                }
            }
        }
    }

    public int getColumnCount() {
        return 7;
    }

    public int getRowCount() {
        return row[0].length;
    }

   /* @Override
    public Class<?> getColumnClass(int column) {
        if(column == 0){
            return Boolean.class;
        }
        return super.getColumnClass(column);
    }*/

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "BR";
            case 1:
                return "Adresse";
            case 2:
                return "Befehl(Hex)";

            case 3:
                return "LineNumber";
            case 4:
                return "Label";
            case 5:
                return "Befehl";
            case 6:
                return "Kommentare";

            default:
                return null;
        }
    }

    public void setRow(String[][] row) {
        this.row = row;
    }
    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return columnIndex==0;
    }

    public Object getValueAt(int r, int c) {
        return row[r][c];
    }

    public void setValueAt(Object obj, int r, int c) {
        row[r][c] = (String) obj;
        fireTableDataChanged();
    }

}
