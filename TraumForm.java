package guiSwing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Vector;

public class TraumForm implements MouseListener,ActionListener{
	DefaultTableModel mod;
	int numRows;
	JTable tab;
	
	TraumForm(){
		JFrame frame = new JFrame("Tравмы");
		tab = new JTable();
		ResultSet rs;
		JComboBox jcb=new JComboBox();
		try {
			rs = BdGUI.st.executeQuery("SELECT SecName FROM Спортсмены");
			while(rs.next())
				jcb.addItem(rs.getString(1));
		} catch (SQLException e1) {
			// TODO Автоматически созданный блок catch
			e1.printStackTrace();
		}	
		//add(tab);
		  
		tab.addMouseListener(this);
		JPanel panel = new JPanel();
		JButton jbtnSave = new JButton("Сохранить");
		jbtnSave.addActionListener(this);
		panel.setLayout(new BorderLayout());
		JScrollPane jscrPane = new JScrollPane(tab);
		mod = new DefaultTableModel(new String[] {"Фамилия","получил","вышел","место"},0);
		tab.setModel(mod);
		try {
			rs = BdGUI.st.executeQuery("SELECT SecName,dateBegin,dateEnd,place FROM Травмы t INNER JOIN Спортсмены s ON "+
		"t.idS = s.idS");
			while(rs.next()){
				Vector<String> data = new Vector<String>();
				data.addElement(rs.getString("SecName"));
				data.addElement(setDate(rs.getString("dateBegin")));
				data.addElement(setDate(rs.getString("dateEnd")));
				data.addElement(rs.getString("place"));
				mod.addRow(data);
			}
		} catch (SQLException e) {
			// TODO Автоматически созданный блок catch
			e.printStackTrace();
		}
		numRows = mod.getRowCount();
		TableColumn column=tab.getColumnModel().getColumn(0);
		column.setCellEditor(new DefaultCellEditor(jcb));
		DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
		column.setCellRenderer(renderer);
		mod.addRow(new Vector<String>());
		panel.add(jscrPane);
		panel.add(jbtnSave,BorderLayout.SOUTH);
		frame.getContentPane().add(panel);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JTable tab = (JTable)e.getSource();
		  if(tab.getSelectedRow()==mod.getRowCount()-1)
			  mod.addRow(new Vector<String>());		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Автоматически созданная заглушка метода
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Автоматически созданная заглушка метода
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Автоматически созданная заглушка метода
		
	}
	
	String setDate(String ins){
		if((ins!=null)&&(ins!="")) return ins.substring(0,10);
		else return "";
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Автоматически созданная заглушка метода
		
	}

	String getDate(Object ins){
		if((ins==null)||(ins.toString().trim().equals(""))) return "null" ; 
		else return ("'"+ins+"'");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Сохранить")){			
			try {
				if(numRows!=mod.getRowCount()-1){
					System.out.println(numRows);
					for(int i=numRows;i<mod.getRowCount()-1;i++){
						String dateBegin = (String) tab.getValueAt(i, 1);
						if((dateBegin==null)||(dateBegin.trim().equals(""))) dateBegin = "null";
						String dateEnd = (String) tab.getValueAt(i, 2);
						if((dateEnd==null)||(dateEnd.trim().equals(""))) dateEnd = "null";
						BdGUI.st.executeUpdate("INSERT INTO Травмы (idS,dateBegin,dateEnd,place) SELECT s.idS,"+getDate(tab.getValueAt(i, 1))+
						","+getDate(tab.getValueAt(i, 2))+",'"+tab.getValueAt(i, 3)+"' FROM Спортсмены s WHERE SecName='"+tab.getValueAt(i, 0)+"'");
					    if(tab.getValueAt(i, 2)==null)BdGUI.st.executeUpdate("UPDATE Спортсмены SET traum = true");
					    else BdGUI.st.executeUpdate("UPDATE Спортсмены SET traum = false WHERE SecName='"+tab.getValueAt(i, 0)+"'");
						}
					}				
			} catch (SQLException e1) {
				// TODO Автоматически созданный блок catch
				e1.printStackTrace();
			}					
		}		
	}	
}

