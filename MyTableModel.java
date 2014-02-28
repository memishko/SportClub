package guiSwing;

import java.util.Vector;

public class MyTableModel extends javax.swing.table.DefaultTableModel {
	public MyTableModel(String[] args) {
	      super(args, 0);
	    }

	    @Override
	    public Class<?> getColumnClass(int columnIndex) {
	      Class clazz = String.class;
	      switch (columnIndex) {
	        case 2:
	          clazz = Boolean.class;
	          break;
	      }
	      return clazz;
	    }

	    @Override
	    public boolean isCellEditable(int row, int column) {
	      return column == 2;
	    }

	    @Override
	    public void setValueAt(Object aValue, int row, int column) {
	      if (aValue instanceof Boolean && column == 2) {
	        System.out.println(aValue);
	        Vector rowData = (Vector)getDataVector().get(row);
	        rowData.set(2, (boolean)aValue);
	        fireTableCellUpdated(row, column);
	      }
	    }  
}
