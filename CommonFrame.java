package guiSwing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public abstract class CommonFrame extends JFrame implements ActionListener { 
		protected Statement st;
		JTextField jtxtName;
		JComboBox<String> jcbSpec;
		JFrame frame1;
		GridBagConstraints gbc;
		JPanel panel;
		GridBagLayout gbag;
		JTextField jtxtRating;
		JLabel jlabelRating;
		Timer timer;
		JLabel jlabelWrong;
		DefaultTableModel mod;
		JScrollPane jscrPane;
		JTable tab;
		
	  CommonFrame() throws SQLException { 	    
		  st = BdGUI.st;
		  Vector<String> spec = new Vector<String>();
		  ResultSet rs = st.executeQuery("SELECT specializeName FROM Специализация");
		  while(rs.next())
				spec.addElement(rs.getString(1));
		  jcbSpec = new JComboBox<String>(spec);
	
		  frame1 = new JFrame("Добавление");//добавление спортсмена
		  jtxtName = new JTextField(10);
		  jtxtRating = new JTextField(10);
		  panel = new JPanel();
		  jlabelRating = new JLabel("Рейтинг",null,SwingConstants.RIGHT);
		  JButton jbutSave = new JButton("Сохранить");
		  jbutSave.addActionListener(this);		
		  jtxtRating.setVisible(false);	
		  jlabelRating.setVisible(false);
		  jlabelWrong = new JLabel("<html><u><font color=red> Запись имеется",null,SwingConstants.RIGHT);
		  jlabelWrong.setVisible(false);
		  tab = new JTable();
		  tab.setPreferredScrollableViewportSize(new Dimension(tab.getPreferredScrollableViewportSize().width,7 * tab.getRowHeight()));
		  jscrPane = new JScrollPane(tab);
		  
		  timer = new Timer( 1500, new ActionListener()
		  {
		      public void actionPerformed(ActionEvent e){	      
		    	 jlabelWrong.setVisible(false);
		    	 timer.stop();
		      }
		  } );
			
		  gbag = new GridBagLayout(); 
		  gbc = new GridBagConstraints();	    
		  gbc.insets = new Insets(3, 3, 3, 3); 	   
		  gbc.fill = GridBagConstraints.BOTH;	    
		  packPanel(jbutSave,1,5);	
		 // packPanel(jtxtRating,3,4);
		    
		  panel.setLayout(gbag);
		  panel.add(jlabelWrong);
		  frame1.getContentPane().add(panel);
		  frame1.setSize(500, 500);
		  frame1.setVisible(true);
	  }   
	  
	void packPanel(Component obj, int x, int y){
		gbc.gridx = x;
		gbc.gridy = y;
		gbag.setConstraints(obj, gbc);
		panel.add(obj);	
	}
	
	void ShowLabel(){
		frame1.dispose();
		BdGUI.timer.start();
		BdGUI.jlblOk.setVisible(true);
	}

	String getDate(String ins){
		if(ins.trim().equals("")) return "null" ; 
		else return ("'"+ins+"'");
	}
	
	String setDate(String ins){
		if((ins!=null)&&(ins!="")) return ins.substring(0,10);
		else return "";
	}
	
}//класс
