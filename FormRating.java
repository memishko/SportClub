package guiSwing;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class FormRating extends JFrame implements ActionListener{	
	JComboBox jcbSpec;
	String[] columnNames = {"место","вид спорта","Фамилия","рейтинг"};
	JTable tab;
	JComboBox jcbType;
	
	FormRating() throws SQLException{
		Statement st =BdGUI.st; 
		JFrame frame = new JFrame("Рейтинг");
	    frame.setSize(600, 400); 	    
	    Vector<String> spec = new Vector<String>();
	    spec.addElement("все");
	    Vector<String> type = new Vector<String>();
	    type.addElement("спортсмены");
	    type.addElement("тренеры");
	    jcbType = new JComboBox(type);
	    ResultSet rs = st.executeQuery("SELECT specializeName from Специализация");
	    while(rs.next())
	    	spec.addElement(rs.getString(1));
	    jcbSpec  = new JComboBox<String>(spec);	 
	    jcbSpec.addActionListener(this);
	    jcbType.addActionListener(this);
		JPanel panel = new JPanel();
		tab = new JTable();
		JScrollPane scrPane = new JScrollPane(tab);
		panel.add(jcbType);	
		panel.add(jcbSpec);		
		panel.add(scrPane);
		String[] columnNames = {"место","вид спорта","Фамилия","рейтинг"};
		int resulSetSize;		
		String specName = jcbSpec.getSelectedItem().toString(); 
		if(specName.equals("все")) specName = "";
			rs = st.executeQuery("SELECT specializeName,SecName,rating FROM Спортсмены s INNER JOIN Специализация spec ON"+
			" spec.idSpec = s.spSpecializ WHERE specializeName LIKE '%"+specName+"%' ORDER BY rating DESC");
			rs.last();//вычисление количества записей
			resulSetSize = rs.getRow();
			rs.beforeFirst(); 
			System.out.println(resulSetSize);
			String[][] data = new String[resulSetSize][4];
			tab.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
			int i =0;
			while(rs.next()){
				System.out.println("fg");
				tab.setValueAt(Integer.toString(i+1), i, 0);
				tab.setValueAt(rs.getString("specializeName"), i, 1);
				tab.setValueAt(rs.getString("SecName"), i, 2);
				tab.setValueAt(rs.getString("rating"), i, 3);
				i++;
			}		
		//tab.setModel(new DefaultTableModel(data, columnNames));
		
	    frame.getContentPane().add(panel);	    
	    frame.setVisible(true); 
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox box = (JComboBox)e.getSource();
		String specName = jcbSpec.getSelectedItem().toString(); 
		if(specName.equals("все")) specName = "";
			ResultSet rs = null;
			try {
				if(jcbType.getSelectedItem().equals("спортсмены")){
				rs = BdGUI.st.executeQuery("SELECT specializeName,SecName,rating FROM Спортсмены s INNER JOIN Специализация spec ON"+
				" spec.idSpec = s.spSpecializ WHERE specializeName LIKE '%"+specName+"%' ORDER BY rating DESC");
				}else{
					rs = BdGUI.st.executeQuery("SELECT specializeName,CSecName,trating FROM Тренеры s INNER JOIN Специализация spec ON"+
							" spec.idSpec = s.specializ WHERE specializeName LIKE '%"+specName+"%' ORDER BY trating DESC");
				}
				rs.last();//вычисление количества записей
				int resulSetSize = rs.getRow();
				rs.beforeFirst(); 
				System.out.println(resulSetSize);
				String[][] data = new String[resulSetSize][4];
				tab.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
				int i =0;
				while(rs.next()){
					System.out.println("fg");
					tab.setValueAt(Integer.toString(i+1), i, 0);
					tab.setValueAt(rs.getString("specializeName"), i, 1);
					tab.setValueAt(rs.getString(2), i, 2);
					tab.setValueAt(rs.getString(3), i, 3);
					i++;
				}			
			} catch (SQLException e1) {
				// TODO Автоматически созданный блок catch
				e1.printStackTrace();
			}				
	}

}
