package guiSwing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class UpdSp {
	private FormSp form;
	private Statement st;
	static int editSp;
	
	UpdSp(String secName, String name, String DOB, String spec) throws SQLException{
		form = new FormSp(true);
		form.jtxtName.setText(name);
		form.jtxtSecName.setText(secName);
		String date = setDate(DOB);	
		form.jtxtDOB.setText(date);
		form.jcbSpec.setSelectedItem(spec);		
		st = BdGUI.st;
		ResultSet rs = st.executeQuery("SELECT idS,Otch,CSecName,skillName,rating FROM  (((Спортсмены s INNER JOIN Тренеры "
				+ "t ON s.Coach = t.idCoach) INNER"+" JOIN Мастерство m ON m.idSkill = s.Skill) INNER JOIN Специализация k ON k.idSpec"+
				" = s.spSpecializ) WHERE SecName='"+secName+"' AND spName ='"+name+"' AND specializeName='"+spec+"'");
		if(rs.next()){
			editSp = rs.getInt("idS");
			form.jtxtOtch.setText(rs.getString("Otch"));
			form.jtxtRating.setText(rs.getString("rating"));
			form.jcbCoach.setSelectedItem(rs.getString("CSecName"));
			form.jcbSkill.setSelectedItem(rs.getString("skillName"));
			form.jlabelRating.setVisible(true);
			form.jtxtRating.setVisible(true);
		}
		rs = st.executeQuery("SELECT gameName,pos FROM (Соревнования s INNER JOIN протоколы p ON s.idGame = p.IDSr) INNER JOIN Спортсмены sp"+
		" ON sp.idS = p.IDsp WHERE idS ="+editSp);
		while(rs.next()){
			Vector<String> data = new Vector<String>();
			data.addElement(rs.getString("gameName"));
			data.addElement(rs.getString("pos"));
			form.mod.addRow(data);
		}
		form.lateCoach = form.jcbCoach.getSelectedItem().toString();
		form.jlabelChange.setVisible(false);
		form.jtxtChange.setVisible(false);
		form.jscrPane.setVisible(true);
		form.revalidate();
		form.repaint();
	}
	String setDate(String ins){
		if((ins!=null)&&(ins!="")) return ins.substring(0,10);
		else return "";
	}
}
