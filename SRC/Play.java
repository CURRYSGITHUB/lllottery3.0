import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Play {
	 // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/world?serverTimezone=GMT%2B8";//url指向要访问的数据库名
 
    // MySQL配置时的用户名与密码
    static final String USER = "root";
    static final String PASS = "1315202benny";
    //要读取得奖品地址
    static String imgurl1 =null;
    static String imgurl2 =null;
    static String imgurl3 =null;
    static String imgurl4 =null;
    //要读取得奖品概率
    static double frate = 0.00;
	static double srate = 0.00;
	static double trate = 0.00;
	
	private static  JFrame frame ;
	
	private JLabel label1 = new JLabel();
	private JLabel label2 = new JLabel();
	private static ImageIcon icon;
	private final JButton b1,b2;

	static int date1 = 0;
    //创建管理员对象
    static Administrator admin = new Administrator();
    
    static ImageIcon bgImage = new ImageIcon("C:/Users/Damien/Desktop/bg.jpg");
	static JLabel bgLabel = new JLabel(bgImage);
    
    SheThread thread = null;
    
    public Play() {
    	frame = new JFrame();
		frame.setSize(950, 750);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);//将布局管理器置为空
        frame.setVisible(true);
        
        bgLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		JPanel imagePanel = (JPanel) frame.getContentPane();
		imagePanel.setOpaque(false);
		// 把背景图片添加到分层窗格的最底层作为背景
		frame.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
		frame.setResizable(false);
    	
		b1 = new JButton("开始");
    	b2 = new JButton("结束");
    	b1.addActionListener(new Begin());
		b2.addActionListener(new Stop());
		b1.setBounds(400, 600, 100, 50);
		b2.setBounds(500, 600, 100, 50);
		
    	frame.setTitle("开始抽奖");
    	frame.getContentPane().add(b1);
    	frame.getContentPane().add(b2);
    	
    	JButton b0 = new JButton("返回");		   
        b0.setBounds(750, 600, 100, 50);
        b0.setBackground(Color.ORANGE);
        b0.addActionListener(new B0());
        frame.getContentPane().add(b0);
	    //将面板加载到Frame主窗口里
		//b2.setVisible(false);
        readurl();
        readrate();

    }
    
    
    
    
    // 抽奖方法
    public void userCJ() {
        // 判断登录状态
        if (!User.isLogin) {
        	Start start = new Start();
            // 没登录 直接结束方法
            JOptionPane.showMessageDialog(null,"请先登录");
            frame.setVisible(false);
            start.cjStart();
            return;
        }else {
        	frame.setVisible(true);
        }
        
    }

     class Begin implements ActionListener{ 
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			b1.setVisible(false);
			b2.setVisible(true);
			if (thread != null)
				thread.stop();
			thread = new SheThread();
			thread.start();
			}
		}
    
     static class B0 implements ActionListener{
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			frame.setVisible(false);
    			User.swingCJ();
    		}
    		
    	}

     
     class Stop implements ActionListener{ 
 		public ImageIcon icon;

		@SuppressWarnings("deprecation")
		@Override
 		public void actionPerformed(ActionEvent e) {
 			// TODO Auto-generated method stub
 			b2.setVisible(false);
 			b1.setVisible(true); 
 			if (thread != null)
				thread.stop();
			thread = null;
			String end = null;
			end = cjtest();
			icon = new ImageIcon(end);
		    	label2.setIcon(icon);
		    	label2.setBounds(200,70,icon.getIconWidth(),icon.getIconHeight());
			    frame.add(label2);//将面板加载到Frame主窗口里
			    setprize(end);
 		}
     }
     
     

	public String cjtest() {
      //开始抽奖
      //确定每个概率的范围
		
	        int fprize = (int)(frate*1000);
	        int sprize = (int)(srate*1000);
	        int tprize = (int)(trate*1000);
	        int pnumber = (int)(Math.random() * 1001 + 1000);
		 if(pnumber<=(1000+fprize)) {
	        	//中一等奖 调出id为1的图片 地址为 imgurl1；
		    	return imgurl1;
	        }
	        else if(pnumber>(1000+fprize)&&pnumber<=(1000+fprize+sprize)){
	        	//中二等奖 调出id为1的图片 地址为 imgurl2；
	        	return imgurl2;
	        }
	        else if(pnumber>(1000+fprize+sprize)&&pnumber<=(1000+fprize+sprize+tprize)) {
	        	//中三等奖 调出id为1的图片 地址为 imgurl3；
	        	return imgurl3;
	        }else {
	        	return imgurl4;
	        }
}
	
	class SheThread extends Thread {
		public SheThread() {
		}
	 
		public void run() {
			while (true) {
				try {
					sleep(5);
				} catch (InterruptedException e) {
				}
				icon = new ImageIcon(cjtest());
 		    	label2.setIcon(icon);
 		    	label2.setBounds(200,70,icon.getIconWidth(),icon.getIconHeight());
 			    frame.add(label2);//将面板加载到Frame主窗口里
			}
		}

	}	
	
	
	//传一个图片地址进去，它就能把正在抽奖的账户的相应中奖次数加一
	public void setprize(String a) {

		Connection conn = null;//声明connection对象
		Statement stmt = null;//创建statement类对象，用来执行sql语句
	try {
	    // 注册 JDBC 驱动
	    Class.forName("com.mysql.cj.jdbc.Driver");

	    // 打开链接
	    conn = DriverManager.getConnection(DB_URL,USER,PASS);//getConnection方法，连接MySQL数据库

	    // 执行查询
	    stmt = conn.createStatement();
	    int b = 0;//初始化中奖id
	    String sql;
	    sql = "SELECT imgID, imgURL FROM img";
	    ResultSet rs = stmt.executeQuery(sql);
	   
	    while(rs.next()){
	    	if(rs.getString("imgURL").contentEquals(a))
	    		{
	    	    switch(rs.getInt("imgID")) {
	    	    case 1:
	    	    	User.ftimes++;
	    	    	JOptionPane.showMessageDialog(null, "恭喜你中了一等奖！！");
	    	    	break;
	    	    case 2:
	    	    	User.stimes++;
	    	    	JOptionPane.showMessageDialog(null, "恭喜你中了二等奖！！");
	    	    	break;
	    	    case 3:
	    	    	User.ttimes++;
	    	    	JOptionPane.showMessageDialog(null, "恭喜你中了三等奖！！");
	    	    	break;
	        }	
	    	}
	    }

		    String sql1 = "update lottery set ftimes= '"+User.ftimes+"'where account ='"+User.account+"'";
		    stmt.executeUpdate(sql1);
		    String sql2 = "update lottery set stimes= '"+User.stimes+"'where account ='"+User.account+"'";
		    stmt.executeUpdate(sql2);
		    String sql3 = "update lottery set ttimes= '"+User.ttimes+"'where account ='"+User.account+"'";
		    stmt.executeUpdate(sql3);//执行SQL语句
		    // 完成后关闭
		    rs.close();
		    stmt.close();
		    conn.close();
		}catch(SQLException se){
		    // 处理 JDBC 错误
		    se.printStackTrace();
		}catch(Exception e){
		    // 处理 Class.forName 错误
		    e.printStackTrace();
		}finally{
		    // 关闭资源
		    try{
		        if(stmt!=null) stmt.close();
		    }catch(SQLException se2){
		    }// 什么都不做
		    try{
		        if(conn!=null) conn.close();
		    }catch(SQLException se){
		        se.printStackTrace();
		    }
		}
		}
	







	public void readurl() {
	Connection conn = null;//声明connection对象
	Statement stmt = null;//创建statement类对象，用来执行sql语句

	try{
	// 注册 JDBC 驱动
	    Class.forName("com.mysql.cj.jdbc.Driver");
	
	    // 打开链接
	    conn = DriverManager.getConnection(DB_URL,USER,PASS);//getConnection方法，连接MySQL数据库
	
	    // 执行查询
	    stmt = conn.createStatement();
	    String sql;
	    sql = "SELECT imgID, imgURL FROM img";
	    ResultSet rs = stmt.executeQuery(sql);
	    // 展开结果集数据库
	    while(rs.next()){
	        // 通过字段检索
	    	if(rs.getInt("imgID")==1) {
	    		 imgurl1 = rs.getString("imgURL");
	    	}
	    	else if(rs.getInt("imgID")==2){
	    		 imgurl2 = rs.getString("imgURL");
	    	}
	    	else if(rs.getInt("imgID")==3){
	    		 imgurl3 = rs.getString("imgURL");
	    	}
	    	else if(rs.getInt("imgID")==4){
	    		 imgurl4 = rs.getString("imgURL");
	    	}
	    	
	    }
	    // 完成后关闭
	    rs.close();
	    stmt.close();
	    conn.close();
	}catch(SQLException se){
	    // 处理 JDBC 错误
	    se.printStackTrace();
	}catch(Exception e){
	    // 处理 Class.forName 错误
	    e.printStackTrace();
	}finally{
	    // 关闭资源
	    try{
	        if(stmt!=null) stmt.close();
	    }catch(SQLException se2){
	    }// 什么都不做
	    try{
	        if(conn!=null) conn.close();
	    }catch(SQLException se){
	        se.printStackTrace();
	    }
	}
	}
	public void readrate() {
	Connection conn = null;//声明connection对象
	Statement stmt = null;//创建statement类对象，用来执行sql语句
	

		try {
		    // 注册 JDBC 驱动
		    Class.forName("com.mysql.cj.jdbc.Driver");
		
		    // 打开链接
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);//getConnection方法，连接MySQL数据库
		
		    // 执行查询
		    stmt = conn.createStatement();
		    String sql;
		    sql = "SELECT fprize, sprize, tprize FROM setrate";
		    ResultSet rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		       	 frate = rs.getDouble("fprize");
		       	 srate = rs.getDouble("sprize");
		       	 trate = rs.getDouble("tprize");
		    }
		    // 完成后关闭
		    rs.close();
		    stmt.close();
		    conn.close();
		}catch(SQLException se){
		    // 处理 JDBC 错误
		    se.printStackTrace();
		}catch(Exception e){
		    // 处理 Class.forName 错误
		    e.printStackTrace();
		}finally{
		    // 关闭资源
		    try{
		        if(stmt!=null) stmt.close();
		    }catch(SQLException se2){
		    }// 什么都不做
		    try{
		        if(conn!=null) conn.close();
		    }catch(SQLException se){
		        se.printStackTrace();
		    }
		}	
		}
}
