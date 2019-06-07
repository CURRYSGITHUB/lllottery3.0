import	 java.sql.*;
import	javax.swing.*;
import	java.awt.*;
import	java.awt.event.*;

public class User extends Person {
	public static int number = 0;
	public static boolean isWin = false;
	public static int b;
	
	// JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8";//url指向要访问的数据库名
 
    // MySQL配置时的用户名与密码
    static final String USER = "11";
    static final String PASS = "12345678";
    
	static JFrame frame,frame2;
	static JButton b1,b2,b3;
	public static void swingCJ() {
		frame = new JFrame();
		b1 = new JButton("开 始 抽 奖");
		b2 = new JButton("查 看 中 奖 信 息");
		b3 = new JButton("退 出 登 录");
		
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);//将布局管理器置为空
        frame.setVisible(true);
        
        b1.setBounds(350, 240, 220, 52);
        b1.setBackground(Color.ORANGE);
        b1.addActionListener(new B1());
        b2.setBounds(350, 331, 220, 52);
        b2.setBackground(Color.ORANGE);
        b2.addActionListener(new B2());
        b3.setBounds(350, 422, 220, 52);
        b3.setBackground(Color.ORANGE);
        b3.addActionListener(new B3());
        Font font1 = new Font("serif",Font.BOLD,24);
		b1.setFont(font1);
		b2.setFont(font1);
		b3.setFont(font1);
        frame.getContentPane().add(b1);
        frame.getContentPane().add(b2);  
        frame.getContentPane().add(b3); 
        
    	   
	}
	static class B1 implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Play.userCJ();
		}
	}  
	//中奖信息查询
	static class B2 implements ActionListener{
		public void actionPerformed(ActionEvent event) {

			frame2 = new JFrame();
			frame2.setBounds(100, 100, 1000, 800);
			frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame2.getContentPane().setLayout(null);//将布局管理器置为空
	        User.frame.setVisible(false);
	        frame2.setVisible(true);
	        JLabel CJinfo;
	        JButton out = new JButton("返回");
	        Font font2 = new Font("serif",Font.BOLD,24);
	        out.setFont(font2);
	        out.setBounds(350, 600, 232, 57);
	        out.setBackground(Color.ORANGE);
	        out.addActionListener(new Out());
	        frame2.getContentPane().add(out);
	        Font font3 = new Font("微软雅黑",Font.BOLD,48);

		   if(User.isWin) {
			   CJinfo = new JLabel("恭喜您,您已中奖！");
			   CJinfo.setBounds(260,250,500,200);
			   
			   CJinfo.setFont(font3);
			   frame2.getContentPane().add(CJinfo);
		   }else {
			   CJinfo = new JLabel("很遗憾,您未中奖！");
			   CJinfo.setBounds(260,250,500,200);
			   CJinfo.setFont(font3);
			   frame2.getContentPane().add(CJinfo);
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

