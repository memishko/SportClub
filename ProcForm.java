package guiSwing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.Vector;
import java.sql.*;

public class ProcForm extends JFrame implements MouseListener,ActionListener {
	
	DefaultTableModel mod;
	String name;
	String count;
	JTable tab;
	JFrame frame;
//	int col;
	JLabel labelWrong;
	Timer timer;
	
	ProcForm(String name, String count){
		this.name = name;
		this.count = count;		
		frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel panelSouth = new JPanel();
		panel.setLayout(new BorderLayout());
		tab = new JTable();
		tab.addMouseListener(this);
		JScrollPane jscrPane = new JScrollPane(tab);
		panel.add(jscrPane);
		JButton jbtnSave = new JButton("���������");
		jbtnSave.addActionListener(this);		
		labelWrong = new JLabel("<html><u><font color=red> ������ �������",null,SwingConstants.CENTER);
		panel.add(new JLabel("<html><u><font color=red>"+name,null,SwingConstants.CENTER),BorderLayout.NORTH);		
		timer = new Timer( 1500, new ActionListener()
		  {
		      public void actionPerformed(ActionEvent e){	      
		    	 labelWrong.setVisible(false);
		    	 timer.stop();
		      }
		  } );
		mod = new DefaultTableModel(new String[] {"�������", "�����"},0);		
		try {
			ResultSet rs = BdGUI.st.executeQuery("SELECT SecName,pos FROM (��������� p INNER JOIN ���������� s ON p.IDsp = s.idS) INNER JOIN"+
			" ������������ g ON g.idGame = p.IDSr WHERE gameName = '"+name+"'");			
			while(rs.next()){ 
				Vector<String> insData = new Vector<String>();
				insData.addElement(rs.getString("SecName"));
				insData.addElement(rs.getString("pos"));
				mod.addRow(insData);
			}					
		} catch (SQLException e) {
			// TODO ������������� ��������� ���� catch
			e.printStackTrace();
		}		
		labelWrong.setVisible(false);
		panelSouth.add(jbtnSave);		
		panelSouth.add(labelWrong);
		panel.add(panelSouth,BorderLayout.SOUTH);		
		tab.setModel(mod);
		//col = tab.getRowCount()-1;
		frame.getContentPane().add(panel);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

/*	@Override
	public void mouseClicked(MouseEvent e) {
		JTable tab = (JTable)e.getSource();
		  if(tab.getSelectedRow()==mod.getRowCount()-1)
			  mod.addRow(new Vector<String>());		
	}*/

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO ������������� ��������� �������� ������
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO ������������� ��������� �������� ������
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO ������������� ��������� �������� ������
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO ������������� ��������� �������� ������
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("���������")){
			 try {
				/* for(int i =0;i<col;i++){
					 BdGUI.st.executeUpdate("UPDATE ��������� SET pos =");
				 }*/
				 for(int i = 0;i<mod.getRowCount();i++){		
					 ResultSet rs = BdGUI.st.executeQuery("SELECT IDsp,IDSr FROM (��������� p INNER JOIN ���������� s ON s.idS=p.IDsp)"+
							 " INNER JOIN ������������ g ON g.idGame=p.idSr WHERE SecName='"+tab.getValueAt(i, 0)+"' AND gameName='"+
							 name+"'");
					 if(rs.next()){
						 tab.setRowSelectionInterval(i, i); 
						 labelWrong.setVisible(true);
						 timer.start();
						 break;
					 }else{
						 BdGUI.st.executeUpdate("INSERT INTO ��������� (IDsp,IDSr,pos) SELECT idS,idGame,"+tab.getValueAt(i, 1)+
								 " FROM ����������, ������������"+" WHERE SecName ='"+tab.getValueAt(i, 0)+"' AND gameName='"+name+"'");
						 System.out.println("SELECT rating + (("+count+"/2)/"+tab.getValueAt(i, 1)+")"+
								 " FROM ���������� WHERE SecName ='"+ tab.getValueAt(i, 0)+"'");
						 rs = BdGUI.st.executeQuery("SELECT rating + (("+count+"/2)/"+tab.getValueAt(i, 1)+")"+
								 " FROM ���������� WHERE SecName ='"+ tab.getValueAt(i, 0)+"'");
						 Float rating = null;
						 if(rs.next()){
							 rating = rs.getFloat(1);					 					 
							 BdGUI.st.executeUpdate("UPDATE ���������� SET rating ="+rating+" WHERE SecName ='"+tab.getValueAt(i, 0)+"'");
						 }
						 rs = BdGUI.st.executeQuery("SELECT Coach,tRating FROM ����������,������� WHERE SecName ='"+ tab.getValueAt(i, 0)+"'");
						 if(rs.next())
							 BdGUI.st.executeUpdate("UPDATE ������� SET tRating ="+(rs.getFloat("tRating")+rating)+" WHERE idCoach ="+rs.getString("Coach"));					 
					 //rs = BdGUI.st.executeQuery("SELECT ");
					/* BdGUI.st.executeUpdate("INSERT INTO ���������� (rating) SELECT rating +"+count+
							 " /"+tab.getValueAt(i, 1)+" FROM ���������� WHERE SecName ='"+tab.getValueAt(i, 0)+"'");*/
					 
					// BdGUI.st.executeUpdate("INSERT INTO ��������� (pos) VALUES ("+tab.getValueAt(i, 1)+")");
					 }					
							frame.dispose();	
				 }
			} catch (SQLException e1) {
				// TODO ������������� ��������� ���� catch
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO ������������� ��������� �������� ������
		
	}
}
