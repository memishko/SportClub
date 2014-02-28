package guiSwing;

import java.awt.*;   
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;   

   
public class BdGUI extends JFrame implements ActionListener,MouseListener  { 
	static Statement st;
	JTextField jtxt;
	JTable tab1;
	JTable tab2;
	JTable tab3;
	JFrame jfrm;
	JTabbedPane tabPane;
	static JLabel jlblOk;
	static Timer timer;
	
 static void getConnection()throws ClassNotFoundException, SQLException{
	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	String url = "jdbc:odbc:Driver=Microsoft Access Driver (*.mdb);DBQ=D:/exprojs/testSQL/DB1.mdb";
	Connection con = DriverManager.getConnection(url);
	st = con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
	System.out.println("con created");
 }
  BdGUI() { 	    
    jfrm = new JFrame("GridBagLayout Demo"); 
    new ImagePanel(
            new ImageIcon("image.gif").getImage());
    tabPane = new JTabbedPane();
   
    GridBagLayout gbag = new GridBagLayout(); 
    GridBagConstraints gbc = new GridBagConstraints(); 
    jlblOk = new JLabel("<html><u><font color="+"red"+">Запись сохранена",null,SwingConstants.CENTER);
 
    jfrm.getContentPane().setLayout(gbag);     
  
    jfrm.setSize(600, 600); 
   
    jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
  
    JButton jbtnIns = new JButton("Добавить спортсмена"); 
    JButton jbtnSch = new JButton("Найти");
    JButton jbtnInsCoach = new JButton("Добавить тренера");
    JButton jbtnInsGame = new JButton("Добавить соревнование");
    JButton jbtnRating = new JButton("Посмотреть рейтинг");
    JButton jbtnTraum = new JButton("Травмы");
    
    jbtnIns.addActionListener(this);
    jbtnSch.addActionListener(this);
    jbtnInsCoach.addActionListener(this);
    jbtnInsGame.addActionListener(this);   
    jbtnRating.addActionListener(this);  
    jbtnTraum.addActionListener(this); 
    
    timer = new Timer( 1500, new ActionListener()
	  {
	      public void actionPerformed(ActionEvent e){	      
	    	 jlblOk.setVisible(false);
	    	 timer.stop();
	      }
	  } );
    
    jtxt = new JTextField();
    tab1 = new JTable();
    tab2 = new JTable();
    tab3 = new JTable();
	tab1.addMouseListener(this);
	tab2.addMouseListener(this);
	tab3.addMouseListener(this);
	tab1.setAutoCreateRowSorter(true);
	JScrollPane jscrPane = new JScrollPane(tab1);
	JScrollPane jscrPane2 = new JScrollPane(tab2);
	JScrollPane jscrPane3 = new JScrollPane(tab3);
	tab1.setPreferredScrollableViewportSize(new Dimension(tab1.getPreferredScrollableViewportSize().width,5 * tab1.getRowHeight()));
	tab2.setPreferredScrollableViewportSize(new Dimension(tab2.getPreferredScrollableViewportSize().width,5 * tab2.getRowHeight()));
	tab3.setPreferredScrollableViewportSize(new Dimension(tab3.getPreferredScrollableViewportSize().width,5 * tab3.getRowHeight()));
	tabPane.addTab("спортсмены", jscrPane);
	tabPane.addTab("тренеры", jscrPane2);
	tabPane.addTab("соревнования", jscrPane3);

    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 0.0; 
    gbc.insets = new Insets(6, 6, 6, 0); 
    
    gbc.gridx = 0; 
    gbc.gridy = 1; //заносим кнопку добавить
    gbag.setConstraints(jbtnTraum, gbc);
    gbc.gridx = 0; 
    gbc.gridy = 0; //заносим кнопку добавить
    gbag.setConstraints(jbtnRating, gbc); 
    gbc.gridx = 0; 
    gbc.gridy = 2; //заносим кнопку добавить
    gbag.setConstraints(jbtnIns, gbc); 
    gbc.gridx = 0;
    gbc.gridy = 3;//заносим поле для ввода
    gbag.setConstraints(jbtnInsCoach, gbc);
    gbc.gridx = 0;
    gbc.gridy = 4;//заносим поле для ввода
    gbag.setConstraints(jbtnInsGame, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 6;//заносим поле для ввода
    gbag.setConstraints(jtxt, gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 6;
    gbag.setConstraints(jbtnSch, gbc);
    gbc.insets = new Insets(5,0,0,0);
 
    gbc.gridx = 0;
    gbc.gridy = 7;
    gbag.setConstraints(tabPane, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbag.setConstraints(jlblOk, gbc);
 
    jfrm.getContentPane().add(jbtnIns); 
    jfrm.getContentPane().add(jtxt); 
    jfrm.getContentPane().add(jbtnSch); 
    jfrm.getContentPane().add(jbtnInsCoach);
    jfrm.getContentPane().add(jbtnInsGame);
    jfrm.getContentPane().add(tabPane);
    jfrm.getContentPane().add(jlblOk);
    jfrm.getContentPane().add(jbtnRating);
    jfrm.getContentPane().add(jbtnTraum);
   
    jfrm.setVisible(true); 
    tabPane.setVisible(false);
    jlblOk.setVisible(false);
  }   
    
  public static void main(String args[]) throws ClassNotFoundException, SQLException {    
	getConnection();
    SwingUtilities.invokeLater(new Runnable() {   
      public void run() {   
        new BdGUI();   
      }   
    }); 
  }
  
  public void mouseClicked(MouseEvent e){
		  JTable tab = (JTable)e.getSource();
		  int row = tab.getSelectedRow();
		  if(e.getComponent().equals(tab1))
		  try {
			new UpdSp(tab.getValueAt(row, 1).toString(),tab.getValueAt(row, 2).toString(),tab.getValueAt(row, 3).toString()
					  ,tab.getValueAt(row, 0).toString());
		  } catch (SQLException e1) {
			// TODO Автоматически созданный блок catch
			e1.printStackTrace();
		  }
		  if(e.getComponent().equals(tab3))
			  try {
				new UpdGame(tab.getValueAt(row, 0).toString(),tab.getValueAt(row, 1).toString(),
						tab.getValueAt(row, 2).toString(),tab.getValueAt(row, 3).toString());
			  } catch (SQLException e1) {
				// TODO Автоматически созданный блок catch
				e1.printStackTrace();
			  } 
		  if(e.getComponent().equals(tab2))
			  try {
				new UpdCoach(tab.getValueAt(row, 1).toString(),tab.getValueAt(row, 2).toString(),
						tab.getValueAt(row, 3).toString(),tab.getValueAt(row, 0).toString());
			  } catch (SQLException e1) {
				// TODO Автоматически созданный блок catch
				e1.printStackTrace();
			  }
  }
 

@Override
public void actionPerformed(ActionEvent e) {
	if(e.getActionCommand().equals("Добавить тренера")){ 
		try {
			new FormCoach(false);
		} catch (SQLException e1) {
			// TODO Автоматически созданный блок catch
			e1.printStackTrace();
		}
	}
	if(e.getActionCommand().equals("Посмотреть рейтинг")){ 
			try {
				new FormRating();
			} catch (SQLException e1) {
				// TODO Автоматически созданный блок catch
				e1.printStackTrace();
			}
	}
	if(e.getActionCommand().equals("Травмы")){ 		
			new TraumForm();		
	}
	if(e.getActionCommand().equals("Добавить соревнование")){ 
		try {
			new FormGame(false);
		} catch (SQLException e1) {
			// TODO Автоматически созданный блок catch
			e1.printStackTrace();
		}
	}
	if(e.getActionCommand().equals("Добавить спортсмена")){		
		try {
			new FormSp(false);
		} catch (SQLException e1) {
			// TODO Автоматически созданный блок catch
			e1.printStackTrace();
		}
	}	
		if(e.getActionCommand().equals("Найти")){
		try {//поиск 
			String data = jtxt.getText();
			System.out.println(data);
			getSpDate(data);
			getCoachDate(data);
			getGameDate(data);
			tabPane.setVisible(true);
			jfrm.revalidate();
			jfrm.repaint();
		} catch (SQLException e1) {
			// TODO Автоматически созданный блок catch
			e1.printStackTrace();
		}
	}
}


void getSpDate(String date) throws SQLException{
	//date = "Спортсмены";
	ResultSet rs = st.executeQuery("SELECT SecName, spName, specializeName, spDOB FROM Спортсмены s INNER JOIN Специализация spec ON"+
	" s.spSpecializ = spec.idSpec WHERE secName LIKE '%"+date+"%' OR spName LIKE '%"+date+"%' OR spDOB LIKE '%"+
			date+"%' OR specializeName LIKE '%"+date+"%'");	
	rs.last();//вычисление количества записей
	int resulSetSize = rs.getRow();
	rs.beforeFirst(); 
	System.out.println(resulSetSize);
	String[] columnNames = {
			"Вид спорта",
			"Фамилия",
			"Имя",
			"дата рождения"			
			};
	String[][] data = new String[resulSetSize][4];
	tab1.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
	int i = 0;
	while(rs.next()){
		tab1.setValueAt(rs.getString("SecName"), i, 1);
		tab1.setValueAt(rs.getString("spName"), i, 2);
		tab1.setValueAt(setDate(rs.getString("spDOB")), i, 3);
		tab1.setValueAt(rs.getString("specializeName"), i, 0);
		i++;
	}
}
	
	void getCoachDate(String date) throws SQLException{
		ResultSet rs = st.executeQuery("SELECT cSecName, cName, specializeName, cOtch FROM Тренеры t INNER JOIN Специализация s ON"+
		" t.specializ = s.idSpec WHERE cSecName LIKE '%"+date+"%' OR cName LIKE '%"+date+"%' OR cOtch LIKE '%"+
				date+"%' OR specializeName LIKE '%"+date+"%' ");		
		rs.last();//вычисление количества записей
		int resulSetSize = rs.getRow();
		rs.beforeFirst(); 
		System.out.println(resulSetSize);
		String[] columnNames = {
				"Вид спорта",
				"Фамилия",
				"Имя",
				"Отчество"				
				};
		String[][] data = new String[resulSetSize][4];
		tab2.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
		int i = 0;
		while(rs.next()){
			tab2.setValueAt(rs.getString("cSecName"), i, 1);
			tab2.setValueAt(rs.getString("cName"), i, 2);
			tab2.setValueAt(rs.getString("cOtch"), i, 3);
			tab2.setValueAt(rs.getString("specializeName"), i, 0);
			i++;
		}

}
	void getGameDate(String date) throws SQLException{
		//date = "Спортсмены";
		ResultSet rs = st.executeQuery("SELECT gameName, city, gameDate, specializeName FROM Соревнования s INNER JOIN Специализация spec ON"+
		" s.gameSpecializ = spec.idSpec WHERE gameName LIKE '%"+date+"%' OR city LIKE '%"+date+"%' OR gameDate LIKE '%"
				+date+"%' OR specializeName LIKE '%"+date+"%'");		
		rs.last();//вычисление количества записей
		int resulSetSize = rs.getRow();
		rs.beforeFirst(); 
		System.out.println(resulSetSize);
		String[] columnNames = {
				"Вид спорта",
				"Название",
				"место проведения",
				"дата проведения",
				};
		String[][] data = new String[resulSetSize][4];
		tab3.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
		int i = 0;
		while(rs.next()){
			tab3.setValueAt(rs.getString("specializeName"), i, 0);
			tab3.setValueAt(rs.getString("gameName"), i, 1);
			tab3.setValueAt(rs.getString("city"), i, 2);
			tab3.setValueAt(setDate(rs.getString("gameDate")), i, 3);
			i++;
		}
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
}