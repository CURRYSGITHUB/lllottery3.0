import   java.util.*;
import java.sql.*;
import	javax.swing.*;
import	java.awt.*;
import	java.awt.event.*;

public class Login {
	// 存储登录失败的次数
    static int num = 0;
    
    static String checkcode1;
    
    
	static JFrame frame;
	static JButton Login;
	static JLabel laccount;
	static JLabel lpassword;
	static JLabel lpassword2;
	static JTextField taccount,checkcode;
	static JPasswordField tpassword;
	static ValidCode vcode;
	
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8";//url指向要访问的数据库名
 
    // MySQL配置时的用户名与密码
    static final String USER = "root";
    static final String PASS = "123456";
    

    static int date1 = 0;
    // 登录方法
    // 静态方法里面 需要使用静态的成员变量
    public static void userLogin() {
        
    	frame = new JFrame();
    	Login = new JButton("确认登录");
    	taccount = new JTextField();
    	
    	frame.setBounds(100, 100, 300, 300);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);//将布局管理器置为空
        frame.setVisible(true);
        frame.setTitle("用户登录");
        Login.setBounds(180, 198, 93, 23);
        Login.setBackground(Color.ORANGE);
        Login.addActionListener(new Login1());
        frame.getContentPane().add(Login);
        
        laccount = new JLabel("用户名：");
        lpassword = new JLabel("密码：");
        lpassword2 = new JLabel("验证码：");
        laccount.setBounds(100, 31, 60, 23);
        lpassword.setBounds(100, 73, 50, 23);
        lpassword2.setBounds(100, 121, 60, 23);
        frame.getContentPane().add(laccount);
        frame.getContentPane().add(lpassword);
        frame.getContentPane().add(lpassword2);
        
        tpassword = new JPasswordField();
        checkcode = new JTextField();
        taccount.setBounds(182, 31, 79, 21);
        tpassword.setBounds(182, 73, 79, 21);
        checkcode.setBounds(182, 123, 79, 21);
        frame.getContentPane().add(taccount);
        frame.getContentPane().add(tpassword);
        frame.getContentPane().add(checkcode);
        
        vcode = new ValidCode();
		vcode.setBounds(400, 190, 80, 40);
		frame.add(vcode);
		
		
    }
    static class Login1 implements ActionListener{
    	public void actionPerformed(ActionEvent event) {
    		User.account = taccount.getText();
            User.password = String.valueOf(tpassword.getPassword());
            checkcode1 = checkcode.getText();
        	int m = 0;	
        	//获取当前系统日期
        	Calendar c = Calendar.getInstance();
        	int date2 = c.get(Calendar.DATE);

            // 判断匹配登录信息
            Connection conn = null;//声明connection对象
            Statement stmt = null;//创建statement类对象，用来执行sql语句
            if(date1!=date2) {
                try{
                     // 注册 JDBC 驱动
                     Class.forName("com.mysql.cj.jdbc.Driver");
                 
                     // 打开链接
                     conn = DriverManager.getConnection(DB_URL,USER,PASS);//getConnection方法，连接MySQL数据库
                 
                     // 执行查询
                     stmt = conn.createStatement();
                     String sql;
                     sql = "SELECT account, password, number, isLogin, isWin FROM lottery";
                     ResultSet rs = stmt.executeQuery(sql);
                     
                     while(rs.next()){
                    	
                         if(rs.getString("account").contentEquals(User.account)
                        		 &&rs.getString("password").contentEquals(User.password)
                        		 &&rs.getBoolean("isLogin")==false&&isValidCodeRight()) {
                        	 m++;
                        	 User.number = rs.getInt("number");
                        	 User.isLogin = true;
                        	 User.isWin = rs.getBoolean("isWin");
                        	 sql="update lottery set isLogin=1 where account ='"+User.account+"'";//修改登录状态
                        	 stmt.executeUpdate(sql);//执行SQL语句
                        	 frame.setVisible(false);
                        	 User.frame.setVisible(true);
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
                 if(m == 0) {	
            		num++;
             		if (num != 3) {
             			// 提示用户还剩几次机会
                  	   JOptionPane.showMessageDialog(null, "账户、密码或验证码错误，你还有"+(3-num)+"次机会");
             			// 继续登录
                  	   frame.setVisible(false);
             		   userLogin();
             		} else {
             			// 登录失败
             			JOptionPane.showMessageDialog(null, "账户或密码错误，今日机会已用完");
            			// 重置记录登录次数的变量
             			// 输入卡号次数重置
             			date1 = date2;//避免第四次输入
             			num = 0;   
             		}
                 }
            	}

     	}

    }
    public static boolean isValidCodeRight() {
    	 
		if (checkcode == null) {
			return false;
		}
		if (vcode == null) {
			return true;
		}
		if (vcode.getCode().equals(checkcode.getText())) {
			return true;
		}
		return false;
	}
 
}
