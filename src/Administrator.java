import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Administrator extends Person{
		// JDBC 驱动名及数据库 URL
	    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	    static final String DB_URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8";//url指向要访问的数据库名
	 
	    // MySQL配置时的用户名与密码
	    static final String USER = "root";
	    static final String PASS = "123456";
	    //显示用户信息
	    static double rate = 0;
	    
	    static JFrame frame;
	    static JButton b1,b2,b3,b4,b0;
	    
	    public static void swingCJ() {
			frame = new JFrame();
			b1 = new JButton("查看用户信息");
			b2 = new JButton("查看中奖用户");
			b3 = new JButton("设置中奖概率");
			b4 = new JButton("显示中奖概率");
			b0 = new JButton("退出登陆");
			
			frame.setBounds(100, 100, 300, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(null);//将布局管理器置为空
	        frame.setVisible(true);
	        
	        b1.setBounds(1000, 198, 200, 23);
	        b1.setBackground(Color.ORANGE);
	        b1.addActionListener(new B1());
	        b2.setBounds(750, 198, 200, 23);
	        b2.setBackground(Color.ORANGE);
	        b2.addActionListener(new B2());
	        b3.setBounds(500, 198, 200, 23);
	        b3.setBackground(Color.ORANGE);
	        b3.addActionListener(new B3());
	        b4.setBounds(250, 198, 200, 23);
	        b4.setBackground(Color.ORANGE);
	        b4.addActionListener(new B4());
	        b0.setBounds(0, 200, 93, 23);
	        b0.setBackground(Color.ORANGE);
	        b0.addActionListener(new B0());
	        frame.getContentPane().add(b1);
	        frame.getContentPane().add(b2);  
	        frame.getContentPane().add(b3); 
	        frame.getContentPane().add(b4);
	        frame.getContentPane().add(b0);
	        
	    	   
		}	    
	    //查看所有用户
static class  B1 implements ActionListener{
	    	public  void userInfo() {	    		
	    		frame = new JFrame();
				frame.setBounds(100, 100, 300, 300);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.getContentPane().setLayout(null);//将布局管理器置为空
		        frame.setVisible(true);
		        frame.setTitle("全体用户信息");		        
		        JLabel label = new JLabel();
		        
		        JButton b0 = new JButton("返回");		   
		        b0.setBounds(180, 198, 93, 23);
		        b0.setBackground(Color.ORANGE);
		        b0.addActionListener(new B0());
		        
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
		            sql = "SELECT account, number, isLogin FROM administrator";
		            ResultSet rs = stmt.executeQuery(sql);
		        
		            // 展开结果集数据库;添加注册管理员类？
		            while(rs.next()){
		                // 通过字段检索
		                int number  = rs.getInt("number");
		                String account = rs.getString("account");
		                boolean isLogin = rs.getBoolean("isLogin");
		    
		                // 输出数据
		                label.setText("Account " + account);
		                label.setText("Number " + number);
		                label.setText(", 是否登录: " + isLogin);
		                label.setText("\n");
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

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}	    
        }

	    //查看中奖用户
static class B2 implements ActionListener{	    	
	    	public void announce()  {
	    		frame = new JFrame();
				frame.setBounds(100, 100, 300, 300);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.getContentPane().setLayout(null);//将布局管理器置为空
		        frame.setVisible(true);
		        frame.setTitle("中奖用户信息");		        
		        JLabel label = new JLabel();
		        
		        JButton b0 = new JButton("返回");		   
		        b0.setBounds(180, 198, 93, 23);
		        b0.setBackground(Color.ORANGE);
		        b0.addActionListener(new B0());
		        
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
		            sql = "SELECT account, number, isLogin，isWin FROM lottery";
		            ResultSet rs = stmt.executeQuery(sql);
		            // 展开结果集数据库
		            while(rs.next()){
		                // 通过字段检索
		            	if(rs.getBoolean("isWin")) {
		            		label.setText("获奖用户为:"+ rs.getString(account));
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

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
	    }
	    
	    //设置中奖概率
static class B3 implements ActionListener{
	public void setRate() {
    	String rate0 = (String)JOptionPane.showInputDialog("请设置抽奖概率");
    	Scanner scanner = new Scanner(System.in);
        // 接收信息
        Administrator.rate = Double.parseDouble(rate0);    
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

	    //获取中奖概率
static class B4 implements ActionListener{
	public void getRate() {
    	JOptionPane.showMessageDialog(null, Administrator.rate);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

static class B0 implements ActionListener{
	public void backUp() {
		swingCJ();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
}
