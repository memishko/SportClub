package guiSwing;

import java.sql.*;
import java.util.Vector;

public class UpdCoach {
	private FormCoach form;
	static int editCoach;
	
	UpdCoach(String secName, String name, String otch, String spec) throws SQLException{
		form = new FormCoach(true);
		form.jtxtName.setText(name);
		form.jtxtSecName.setText(secName);
		form.jtxtOtch.setText(otch);
		form.jcbSpec.setSelectedItem(spec);		
		ResultSet rs = BdGUI.st.executeQuery("SELECT idCoach,tRating FROM Тренеры WHERE CSecName='"+secName+"' AND cName ='"+name+
				"' AND cOtch='"+otch+"'");
		if(rs.next()){
			form.jtxtRating.setText(rs.getString("tRating"));
			editCoach = rs.getInt("idCoach");
		}
		rs = BdGUI.st.executeQuery("SELECT SecName,spname,spDOB FROM Спортсмены s INNER JOIN Тренеры t ON s.Coach =t.idCoach"+
				" WHERE CSecName='"+form.jtxtSecName.getText()+"' AND specializ=spSpecializ");
				while(rs.next()){
					System.out.println("adg");
					Vector<String> tmp = new Vector<String>();
					tmp.addElement(rs.getString("SecName"));
					tmp.addElement(rs.getString("spName"));
					tmp.addElement(setDate(rs.getString("spDOB")));
					form.mod.addRow(tmp);
				}
		form.jtxtRating.setVisible(true);
		form.jlabelRating.setVisible(true);
		System.out.println("ne");
	}
	String setDate(String ins){
		if((ins!=null)&&(ins!="")) return ins.substring(0,10);
		else return "";
	}
}
