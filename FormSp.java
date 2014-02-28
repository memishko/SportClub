package guiSwing;

import java.awt.Dimension;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;   
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

   
public class FormSp extends CommonFrame implements ActionListener  { 
	JTextField jtxtSecName;
	JTextField jtxtOtch;
	JTextField jtxtDOB;
	JTextField jtxtChange;
	JComboBox<String>  jcbCoach;
	JComboBox<String>  jcbSkill;
	JFrame frame1;		
	Boolean upd;
	String lateCoach;
	JLabel jlabelChange;
	
  FormSp(boolean b) throws SQLException{ 	 
	  upd = b;	  	  
	  Vector<String> Coachs = new Vector<String>();
	  ResultSet rs = st.executeQuery("SELECT CSecName FROM Тренеры");
	  while(rs.next())
			Coachs.addElement(rs.getString(1));	  
	  Vector<String> skill = new Vector<String>();
	  rs = st.executeQuery("SELECT SkillName FROM Мастерство");
	  while(rs.next())
			skill.addElement(rs.getString(1));
	  
		jtxtSecName = new JTextField(10);
		jtxtOtch = new JTextField(10);
		jtxtDOB = new JTextField(10);
		jtxtChange = new JTextField(10);		
		mod = new DefaultTableModel(new String[] {"соревнование","место"},0);
		tab.setModel(mod);
		
		JLabel jlabelName = new JLabel("Имя",null,SwingConstants.RIGHT);
		JLabel jlabelSecName = new JLabel("Фамилия",null,SwingConstants.RIGHT);
		JLabel jlabelOtch = new JLabel("Отчество",null,SwingConstants.RIGHT);
		JLabel jlabelDOB = new JLabel("DOB",null,SwingConstants.RIGHT);
		JLabel jlabelCoach = new JLabel("Тренер",null,SwingConstants.RIGHT);
		JLabel jlabelSpec = new JLabel("<html>Вид спорта </html>>",null,SwingConstants.RIGHT);
		JLabel jlabelSKill = new JLabel("Квалификация",null,SwingConstants.RIGHT);
		jlabelChange = new JLabel("дата смены",null,SwingConstants.RIGHT);
		
		
		
		
		jcbCoach = new JComboBox<String>(Coachs);
		jcbCoach.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Автоматически созданная заглушка метода
				if((upd)&&(jcbCoach.getModel().getSize()!=0)){					
						   if(jcbCoach.getSelectedItem().equals(lateCoach)){
							   jtxtChange.setVisible(false);
						       jlabelChange.setVisible(false);
						   }else{
							   jtxtChange.setVisible(true);
						       jlabelChange.setVisible(true);
						   }
					}
				}
			});
		jcbSpec.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					ResultSet rs = st.executeQuery("SELECT CSecName FROM тренеры t INNER JOIN Специализация spec ON t.specializ=spec.idSpec WHERE"+
				" specializeName ='"+jcbSpec.getSelectedItem().toString()+"'");
				jcbCoach.removeAllItems();
				//Vector<String> spec = new Vector<String>();
				while(rs.next()){
					jcbCoach.addItem(rs.getString(1));
				}
				} catch (SQLException e1) {
					// TODO Автоматически созданный блок catch
					e1.printStackTrace();
				}				
			}			
		});
		jcbSkill = new JComboBox<String>(skill);

	    packPanel(jlabelName, 0, 0);
	    packPanel(jtxtName, 1, 0);
	    packPanel(jlabelSecName, 2, 0);
	    packPanel(jtxtSecName, 3, 0);
	    packPanel(jlabelOtch, 0, 1);
	    packPanel(jtxtOtch, 1, 1);
	    packPanel(jlabelDOB, 2, 1);
	    packPanel(jtxtDOB, 3, 1);
	    packPanel(jlabelCoach, 2, 2);
	    packPanel(jcbCoach, 3, 2);
	    packPanel(jlabelSpec, 0, 2);
	    packPanel(jcbSpec, 1, 2);
	    packPanel(jlabelSKill, 0, 4);
	    packPanel(jcbSkill, 1, 4);
	    packPanel(jlabelRating, 2, 4);
	    packPanel(jtxtRating, 3, 4);
	    packPanel(jtxtChange, 3, 3);
	    packPanel(jlabelChange, 2, 3);
	    //packPanel(jscrPane, 0, 5);
	    gbc.gridx = 0;
	    gbc.gridy = 6;
	    gbc.gridwidth=4;
	    gbag.setConstraints(jscrPane, gbc);
		panel.add(jscrPane);	
		gbc.gridx = 3;
	    gbc.gridy = 5;
	    gbc.gridwidth=2;
	    gbag.setConstraints(jlabelWrong, gbc);
	    jscrPane.setVisible(false);
	    jtxtChange.setVisible(false);
	    jlabelChange.setVisible(false);
	    //if(upd) lateCoach = jcbCoach.getSelectedItem().toString();
  }   
  
  
@Override
public void actionPerformed(ActionEvent e) {
	/*Object objSourse = e.getSource();
	if(objSourse instanceof JComboBox ){
		JComboBox<?> comboBox = (JComboBox<?>) objSourse;
		//if(comboBox.equals("футбол")){
		try {
			ResultSet rs = st.executeQuery("SELECT CSecName FROM тренеры t INNER JOIN Специализация spec ON t.specializ=spec.idSpec WHERE"+
		" specializeName ='"+comboBox.getSelectedItem().toString()+"'");
		jcbCoach.removeAllItems();
		//Vector<String> spec = new Vector<String>();
		while(rs.next()){
			jcbCoach.addItem(rs.getString(1));
		}
		} catch (SQLException e1) {
			// TODO Автоматически созданный блок catch
			e1.printStackTrace();
		}
	}*/
	
	if(e.getActionCommand().equals("Сохранить")){	
		if(!jtxtSecName.getText().equals(""))
			try {				
					Boolean f = true;
					ResultSet rs = st.executeQuery("SELECT spName,Otch,specializeName,CSecName,skillName FROM ((Спортсмены s INNER JOIN Специализация spec ON s.spSpecializ = spec.idSpec)INNER JOIN Мастерство m ON m.idSkill = s.Skill)INNER JOIN Тренеры t ON t.idCoach=s.Coach  WHERE SecName = '"+jtxtSecName.getText()+"'");
					while(rs.next())
						if((rs.getString("spName").equals(jtxtName.getText()))&&(rs.getString("Otch").equals(jtxtOtch.getText()))&&
								(rs.getString("specializeName").equals(jcbSpec.getSelectedItem().toString()))&&(rs.getString("CSecName").equals(jcbCoach.getSelectedItem().toString()))
								&&(rs.getString("SkillName").equals(jcbSkill.getSelectedItem().toString()))){
							jlabelWrong.setVisible(true);
							timer.start();
							f=false;	
						}
					//String DOB=jtxtDOB.getText();					//if(DOB.trim().equals("")) DOB = "null";	
					if(f){
						if(!upd){
							st.executeUpdate("INSERT INTO Спортсмены (SecName, spName,Otch,spSpecializ,Coach,spDOB,Skill) "+
									"SELECT'"+jtxtSecName.getText()+"','"+jtxtName.getText()+"','"+jtxtOtch.getText()+
									"',idSpec,idCoach,"+getDate(jtxtDOB.getText())+" ,idSkill FROM Специализация,Тренеры,Мастерство WHERE"+
									" specializeName ='"+jcbSpec.getSelectedItem().toString()+"' AND SkillName ='"+jcbSkill.getSelectedItem().toString()+
									"' AND CSecName = '"+jcbCoach.getSelectedItem().toString()+"'");
							rs = st.executeQuery("select top 1 * from Спортсмены order by idS desc");
							if(rs.next())
								st.executeUpdate("INSERT INTO переходы (idSp,idCoachNew,dateSup) SELECT "+rs.getString("idS")+",idCoach,Date()"+
										" FROM тренеры WHERE CSecName='"+jcbCoach.getSelectedItem().toString()+"'");
						}
						else{
							//добавление перехода к др тренеру если есть
							if(!lateCoach.equals(jcbCoach.getSelectedItem().toString()))
								st.executeUpdate("INSERT INTO переходы (idSp,idCoachNew,dateSup) SELECT idS,idCoach,"+getDate(jtxtChange.getText())
								+" FROM "+" Спортсмены,Тренеры WHERE idS="+UpdSp.editSp+" AND CSecName='"+jcbCoach.getSelectedItem().toString()+"'");
							/*rs = st.executeQuery("SELECT specializeName,skillName,CSecName FROM ((Спортсмены s INNER JOIN Тренеры t ON"+
						" t.idCoach=s.Coach)INNER JOIN Мастерство m ON m.idSkill = Skill)INNER JOIN Специализация spec ON "+
									" spec.idSpec = spSpecializ WHERE idS="+UpdSp.editSp);*/
							String spec = null,skill = null,coach = null;
							rs= st.executeQuery("SELECT idSpec FROM Специализация WHERE specializeName='"+
									jcbSpec.getSelectedItem().toString()+"'");
							if(rs.next()) spec = rs.getString(1);
							rs = st.executeQuery("SELECT idSkill FROM Мастерство WHERE skillName='"+
									jcbSkill.getSelectedItem().toString()+"'");
							if(rs.next()) skill = rs.getString(1);
							rs = st.executeQuery("SELECT idCoach FROM Тренеры WHERE CSecName='"+
									jcbCoach.getSelectedItem().toString()+"'");
							if(rs.next()) coach = rs.getString(1);
							st.executeUpdate("UPDATE Спортсмены SET SecName='"+jtxtSecName.getText()+"', spName='"+jtxtName.getText()+
									"',Otch='"+jtxtOtch.getText()+"',spSpecializ="+spec+",Coach="+coach+",spDOB="+getDate(jtxtDOB.getText())+
									",Skill="+skill+" WHERE idS = "+UpdSp.editSp);
						}
						ShowLabel();
						System.out.println("g"+"jtxtName.getText())"+"g");	
					}
			} catch (SQLException e1) {
				// TODO Автоматически созданный блок catch
				e1.printStackTrace();			
			}		
	}
}
}//класс