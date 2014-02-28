package guiSwing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class FormGame extends CommonFrame implements ActionListener  { 
	JTextField jtxtCity;
	JTextField jtxtDate;
	JTextField jtxtDOB;
	JComboBox<String> jcbSkill;
	JButton jbtnProc;
	JTextField jtxtCount;
	Boolean edit;
	
  FormGame(boolean b) throws SQLException {
	  edit = b;
	  Vector<String> skill = new Vector<String>();
	  ResultSet rs = st.executeQuery("SELECT skillName FROM Мастерство");
	  while(rs.next())
			skill.addElement(rs.getString(1));
		jtxtCity = new JTextField(10);
		jtxtDate = new JTextField(10);
		jtxtDOB = new JTextField(10);
		jtxtCount = new JTextField(10);
		jbtnProc = new JButton("Протокол");
		jbtnProc.addActionListener(this); 
		mod = new MyTableModel(new String[] {"фамилия","год рождения","участие"});
		tab.setModel(mod);

		
		JLabel jlabelName = new JLabel("Название",null,SwingConstants.RIGHT);
		JLabel jlabelSpec = new JLabel("<html>Вид спорта </html>>",null,SwingConstants.RIGHT);
		JLabel jlabelCity = new JLabel("Город",null,SwingConstants.RIGHT);
		JLabel jlabelSkill = new JLabel("Рейтинг",null,SwingConstants.RIGHT);
		JLabel jlabelDate = new JLabel("Дата",null,SwingConstants.RIGHT);
		JLabel jlabelCount = new JLabel("Участников",null,SwingConstants.RIGHT);
		JLabel jlabelDOB = new JLabel("год от",null,SwingConstants.RIGHT);
		
		jcbSkill = new JComboBox<String>(skill);
	    
		packPanel(jlabelSpec,0,0);
		packPanel(jcbSpec,1,0);
		packPanel(jlabelName,2,0);
		packPanel(jtxtName,3,0);
		packPanel(jlabelCity,0,1);
		packPanel(jtxtCity,1,1);
		packPanel(jlabelDate,2,1);
		packPanel(jtxtDate,3,1);
		packPanel(jlabelSkill,0,2);
		packPanel(jcbSkill,1,2);
		//packPanel(jbutSave,1,3);
		packPanel(jbtnProc,3,5);
		packPanel(jtxtDOB,1,3);
		packPanel(jlabelDOB,0,3);
		packPanel(jlabelCount,2,2);
		packPanel(jtxtCount,3,2);
		gbc.gridx = 0;
	    gbc.gridy = 6;
	    gbc.gridwidth=4;
	    gbag.setConstraints(jscrPane, gbc);
	    gbc.gridx = 3;
	    gbc.gridy = 3;
	    gbc.gridwidth=2;
	    gbag.setConstraints(jlabelWrong, gbc);
	    panel.add(jscrPane);
	    jscrPane.setVisible(false);
  }   
  
@Override
public void actionPerformed(ActionEvent e) {
	if(e.getActionCommand().equals("Сохранить")){
		if(!jtxtName.getText().equals(""))
			try {		
				Boolean f = true;
				ResultSet rs = st.executeQuery("SELECT city,specializeName, skillName FROM (Соревнования s INNER JOIN Специализация spec ON s.gameSpecializ = spec.idSpec)INNER JOIN Мастерство m ON m.idSkill=s.gameSkill WHERE gameName = '"+jtxtName.getText()+"'");
				while(rs.next()){
					if((rs.getString("city").equals(jtxtCity.getText()))&&(rs.getString("skillName").equals(jcbSkill.getSelectedItem().toString()))&&
							(rs.getString("specializeName").equals(jcbSpec.getSelectedItem().toString()))){
						jlabelWrong.setVisible(true);
						timer.start();
						f=false;
					}else f = true;}
				String count= jtxtCount.getText();
				if(count.trim().equals("")) count="null";
				String dateAt= jtxtDOB.getText();
				if(dateAt.trim().equals("")) dateAt="null";
				if(f){
					if(!edit){
						System.out.println(jtxtCount.getText());
						st.executeUpdate("INSERT INTO Соревнования (gameName, gameDate,city,gameSkill,gameSpecializ,gameMembers,gameDateAt) "+
							"SELECT'"+jtxtName.getText()+"',"+getDate(jtxtDate.getText())+",'"+jtxtCity.getText()+
							"',idSkill,idSpec,"+count+",+"+dateAt+" FROM Мастерство,Специализация"+" WHERE specializeName ='"+jcbSpec.getSelectedItem().toString()+
							"' AND skillName='"+jcbSkill.getSelectedItem().toString()+"'");
					}else{
						String skill = null,spec = null;
						rs = st.executeQuery("SELECT idSkill FROM Мастерство WHERE skillName='"+jcbSkill.getSelectedItem().toString()+"'");
						if(rs.next()) skill = rs.getString(1);
						rs = st.executeQuery("SELECT idSpec FROM Специализация WHERE specializeName='"+jcbSpec.getSelectedItem().toString()+"'");
						if(rs.next()) spec = rs.getString(1);
						st.executeUpdate("UPDATE Соревнования SET gameName='"+jtxtName.getText()+"', gameDate="+getDate(jtxtDate.getText())+
								",city='"+jtxtCity.getText()+"',gameSkill="+skill+",gameSpecializ="+spec+",gameMembers="+count+",gameDateAt="+dateAt+" WHERE idGame ="+UpdGame.editGame);
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
	if(e.getActionCommand().equals("Протокол")){
		ProcForm form =new ProcForm(jtxtName.getText(),jtxtCount.getText());
		for(int i=0;i<mod.getRowCount();i++)
			if((boolean) tab.getValueAt(i, 2)){
				form.mod.addRow(new Object[] {tab.getValueAt(i, 0)});
			}		
	}
}
}//класс
