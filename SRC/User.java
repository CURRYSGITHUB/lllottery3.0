import	 java.sql.*;
import	javax.swing.*;
import	java.awt.*;
import	java.awt.event.*;

public class User extends Person {
	public static int ftimes = 0;
	public static int stimes = 0;
	public static int ttimes = 0;
	public static int b;
	
	// JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/world?serverTimezone=GMT%2B8";//url指向要访问的数据库名
 
    // MySQL配置时的用户名与密码
    static final String USER = "root";
    static final String PASS = "1315202benny";
    
	static JFrame frame,frame2;
	static JButton b1,b2,b3;
	
	static ImageIcon bgImage = new ImageIcon("C:/Users/Damien/Desktop/bg.jpg");
	static JLabel bgLabel = new JLabel(bgImage);
	
	public static void swingCJ() {
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
		
		b1 = new JButton("开 始 抽 奖");
		b2 = new JButton("查 看 中 奖 信 息");
		b3 = new JButton("退 出 登 录");
        
        b1.setBounds(360, 270, 197, 50);
        b1.setBackground(Color.ORANGE);
        b1.addActionListener(new B1());
        b2.setBounds(360, 360, 197, 50);
        b2.setBackground(Color.ORANGE);
        b2.addActionListener(new B2());
        b3.setBounds(360, 450, 197, 50);
        b3.setBackground(Color.ORANGE);
        b3.addActionListener(new B3());
        Font font1 = new Font("serif",Font.BOLD,20);
		b1.setFont(font1);
		b2.setFont(font1);
		b3.setFont(font1);
        frame.getContentPane().add(b1);
        frame.getContentPane().add(b2);  
        frame.getContentPane().add(b3); 
        
    	   
	}
	static class B1 implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Play play = new Play();
			frame.setVisible(false);
			play.userCJ();
		}
	}  
	//中奖信息查询
	static class B2 implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(ftimes==0&&stimes==0&&ttimes==0) {
				JOptionPane.showMessageDialog(null, "很遗憾，您没有中任何奖项");
				
			}
			else {
				JOptionPane.showMessageDialog(null, "您的中奖情况如下"+"\n一等奖："+ftimes+"次"+"\n二等奖："+stimes+"次"+"\n三等奖："+ttimes+"次");
			}     
		}
	}
			
	 
	//退出登录
	static class B3 implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
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
		            sql="update lottery set isLogin=0 where account ='"+User.account+"'";//修改登录状态
               	    stmt.executeUpdate(sql);//执行SQL语句

		            // 完成后关闭
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
		    JOptionPane.showMessageDialog(null, User.account+"\n退出登录成功！"); 
		    User.frame.setVisible(false);
		    Start.cjStart();
		}
		}
	static class Out implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frame2.setVisible(false);
			frame.setVisible(true);
		}
	}
}

