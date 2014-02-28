package guiSwing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class UpdGame {
	private FormGame form;
	private Statement st;
	static int editGame;
	
	UpdGame(String spec, String name, String city, String date) throws SQLException{
		form = new FormGame(true);
		form.jtxtName.setText(name);
		form.jtxtCity.setText(city);
		form.jtxtDate.setText(setDate(date));
		form.jcbSpec.setSelectedItem(spec);
		st = BdGUI.st;
		ResultSet rs = st.executeQuery("SELECT skillName,idGame,gameMembers,gameDateAt FROM  ((Соревнования s INNER JOIN Мастерство m ON m.idSkill = s.gameSkill)"+
		" INNER JOIN Специализация spec ON spec.idSpec = s.gameSpecializ)  WHERE gameName='"+name+"' AND city ='"+city+
		"' AND specializeName='"+spec+"'");
		if(rs.next()){
			form.jcbSkill.setSelectedItem(rs.getString("skillName"));	
			editGame = rs.getInt("idGame");
			form.jtxtCount.setText(rs.getString("gameMembers"));
			form.jtxtDOB.setText(rs.getString("gameDateAt"));
		}
		rs = st.executeQuery("SELECT SecName,spDOB FROM Спортсмены,Соревнования WHERE Skill>=gameSkill AND gameName ='"+name+"' AND gameDate>Date() AND traum=false AND YEAR(spDOB)>="+form.jtxtDOB.getText());		
		while(rs.next()) {
			Vector<String> data = new Vector<String>();
			//data.addElement(rs.getString("SecName"));
			//data.addElement(setDate(rs.getString("spDOB")));
			form.mod.addRow(new Object[] {rs.getString("SecName"),(setDate(rs.getString("spDOB"))),false});
		}
		form.jscrPane.setVisible(true);
	}
	String setDate(String ins){
		if((ins!=null)&&(ins!="")) return ins.substring(0,10);
		else return "";
	}
}
