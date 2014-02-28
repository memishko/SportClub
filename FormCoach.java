package guiSwing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class FormCoach extends CommonFrame implements ActionListener  { 
	JTextField jtxtSecName;
	JTextField jtxtOtch;
	Boolean edit;
	
  FormCoach(boolean b) throws SQLException { 
	  edit = b;
		jtxtSecName = new JTextField(10);
		jtxtOtch = new JTextField(10);
		
		JLabel jlabelName = new JLabel("Имя",null,SwingConstants.RIGHT);
		JLabel jlabelSecName = new JLabel("Фамилия",null,SwingConstants.RIGHT);
		JLabel jlabelOtch = new JLabel("Отчество",null,SwingConstants.RIGHT);
		JLabel jlabelSpec = new JLabel("<html>Вид спорта </html>>",null,SwingConstants.RIGHT);
		
		mod = new DefaultTableModel(new String[] {"Фамилия","имя","дата рождения"},0);
		tab.setModel(mod);		
		
		packPanel(jlabelName,0,0);
		packPanel(jtxtName,1,0);
		packPanel(jlabelSecName,2,0);
		packPanel(jtxtSecName,3,0);
		packPanel(jlabelOtch,0,1);
		packPanel(jtxtOtch,1,1);
		packPanel(jlabelSpec,2,1);
		packPanel(jcbSpec,3,1);
		packPanel(jlabelRating,2,5);
		packPanel(jtxtRating,3,5);
		gbc.gridx = 2;
	    gbc.gridy = 2;
	    gbc.gridwidth=2;
	    gbag.setConstraints(jlabelWrong, gbc);
	    panel.add(jlabelWrong);
	    gbc.gridx = 0;
	    gbc.gridy = 6;
	    gbc.gridwidth=4;
	    gbag.setConstraints(jscrPane, gbc);
	    panel.add(jscrPane);
  }   
  
@Override
public void actionPerformed(ActionEvent e) {
	if(e.getActionCommand().equals("Сохранить")){
		if(!jtxtSecName.getText().equals(""))
			try {
				Boolean f = true;
				ResultSet rs = st.executeQuery("SELECT CName,COtch,specializeName FROM Тренеры t INNER JOIN Специализация spec ON t.Specializ = spec.idSpec WHERE CSecName = '"+jtxtSecName.getText()+"'");
				while(rs.next()){
					if((rs.getString("cName").equals(jtxtName.getText()))&&(rs.getString("cOtch").equals(jtxtOtch.getText()))&&
							(rs.getString("specializeName").equals(jcbSpec.getSelectedItem().toString()))){
						jlabelWrong.setVisible(true);
						timer.start();
						f=false;
					}else f = true;}
				if(f){
					if(!edit)
						st.executeUpdate("INSERT INTO Тренеры (CSecName, CName,COtch,specializ) "+
								"SELECT'"+jtxtSecName.getText()+"','"+jtxtName.getText()+"','"+jtxtOtch.getText()+
								"',idSpec FROM Специализация WHERE specializeName ='"+jcbSpec.getSelectedItem().toString()+"'");
					else{
						String spec = null;
						rs = st.executeQuery("SELECT idSpec FROM Специализация WHERE specializeName ='"+jcbSpec.getSelectedItem().toString()+"'");
						if(rs.next()) spec = rs.getString(1);
						st.executeUpdate("UPDATE Тренеры SET CSecName ='"+jtxtSecName.getText()+"', CName='"+jtxtName.getText()+
								"',COtch='"+jtxtOtch.getText()+"',specializ="+spec+" WHERE idCoach="+UpdCoach.editCoach);
					}
				frame1.dispose();
				BdGUI.timer.start();
				BdGUI.jlblOk.setVisible(true);
				}
			} catch (SQLException e1) {
				// TODO Автоматически созданный блок catch
				e1.printStackTrace();			
			}
	}
}
}//класс
