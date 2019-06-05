import	 java.sql.*;
import	javax.swing.*;
import	java.awt.*;
import	java.awt.event.*;

public class User extends Person{
	
	public static int number = 0;
	public static boolean isWin = false;
	public static int b;
	
	// JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8";//url指向要访问的数据库名
 
    // MySQL配置时的用户名与密码
    static final String USER = "root";
    static final String PASS = "123456";
    
	static JFrame frame;
	JButton b1,b2,b3;
	public void swingCJ() {
		frame = new JFrame();
		b1 = new JButton("开始抽奖");
		b2 = new JButton("查看中奖信息");
		b3 = new JButton("退出登录");
		
		frame.setBounds(100, 100, 300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);//将布局管理器置为空
        frame.setVisible(true);
        
        b1.setBounds(180, 198, 93, 23);
        b1.setBackground(Color.ORANGE);
        b1.addActionListener(new B1());
        b2.setBounds(156, 198, 93, 23);
        b2.setBackground(Color.ORANGE);
        b2.addActionListener(new B2());
        b3.setBounds(180, 198, 93, 23);
        b3.setBackground(Color.ORANGE);
        b3.addActionListener(new B3());
        frame.getContentPane().add(b1);
        frame.getContentPane().add(b2);  
        frame.getContentPane().add(b3); 
        
    	   
	}
	class B1 implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Play.userCJ();
		}
	}     
	class B2 implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			int num = 0;
			frame = new JFrame();
			frame.setBounds(100, 100, 300, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(null);//将布局管理器置为空
	        frame.setVisible(true);
	        frame.setTitle("中奖信息查询");
	        JLabel CJinfo;

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
		            sql = "SELECT account, number, FROM lottery";
		            ResultSet rs = stmt.executeQuery(sql);
		        
		            // 展开结果集数据库
		            while(rs.next()){
		                // 通过字段检索
		                String account = rs.getString("account");
		                num = rs.getInt("number");
                        //找到用户信息
		                if(User.account == account) {
		                	break;
		                }
		            }
					if(num == Play.cardNumber) {
		                 
		                 CJinfo = new JLabel("恭喜您，中奖啦！");
		                 CJinfo.setBounds(140, 31, 34, 23);
		                 frame.getContentPane().add(CJinfo);
		             }
		             else {
		            	 CJinfo = new JLabel("很遗憾，您未中奖！");
		            	 CJinfo.setBounds(140, 31, 34, 23);
		                 frame.getContentPane().add(CJinfo);
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
	class B3 implements ActionListener{
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
		            sql = "SELECT account, isLogin, FROM lottery";
		            ResultSet rs = stmt.executeQuery(sql);
		        
		            // 展开结果集数据库
		            while(rs.next()){
		                // 通过字段检索
		                String account = rs.getString("account");
		                @SuppressWarnings("unused")
						boolean isLogin = rs.getBoolean("isLogin");
		                
		                if(User.account == account) {
		                	isLogin = false;
		                	break;
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
		}
	}     
