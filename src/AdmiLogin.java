import   java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdmiLogin {
	
	static String checkcode1;
	
	static JFrame frame;
	static JButton AdmiLogin;
	static JLabel laccount;
	static JLabel lpassword;
	static JLabel lpassword2;
	static JTextField taccount,checkcode;
	static JPasswordField tpassword;
	static ValidCode vcode; 
	
	// 保存登录失败的次数
    static int num = 0;
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8";//url指向要访问的数据库名
 
    // MySQL配置时的用户名与密码
    static final String USER = "root";
    static final String PASS = "123456";
    
    static int date1 = 0;
    // 登录方法
    // 静态方法里面 需要使用静态的成员变量
    public static void admiLogin() {
    	
    	frame = new JFrame();
    	AdmiLogin = new JButton("确认登录");
    	
    	frame.setBounds(100, 100, 300, 300);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);//将布局管理器置为空
        frame.setVisible(true);
        frame.setTitle("用户登录");
        AdmiLogin.setBounds(180, 198, 93, 23);
        AdmiLogin.setBackground(Color.ORANGE);
        AdmiLogin.addActionListener(new admiLogin1());
        frame.getContentPane().add(AdmiLogin);
        
        laccount = new JLabel("用户名");
        lpassword = new JLabel("密码");
        lpassword2 = new JLabel("验证码");
        laccount.setBounds(140, 31, 34, 23);
        lpassword.setBounds(140, 73, 34, 23);
        lpassword2.setBounds(140, 121, 34, 23);
        frame.getContentPane().add(laccount);
        frame.getContentPane().add(lpassword);
        frame.getContentPane().add(lpassword2);
        
        taccount = new JTextField();
        tpassword = new JPasswordField();
        checkcode = new JTextField();
        taccount.setBounds(182, 31, 79, 21);
        tpassword.setBounds(182, 73, 79, 21);
        checkcode.setBounds(182, 123, 79, 21);
        frame.getContentPane().add(taccount);
        frame.getContentPane().add(tpassword);
        frame.getContentPane().add(checkcode);
        
        vcode = new ValidCode();
		vcode.setBounds(300, 190, 80, 40);
		frame.add(vcode);
    	
		
    	
     	}
    static class admiLogin1 implements ActionListener{
    	public void actionPerformed(ActionEvent event) {
    		int m = 0;	
        	//获取当前系统日期
        	Calendar c = Calendar.getInstance();
        	int date2 = c.get(Calendar.DATE);
            // 输入信息

            Connection conn = null;//声明connection对象
            Statement stmt = null;//创建statement类对象，用来执行sql语句
            if(date1!=date2) {
                try{
                     // 注册 JDBC 驱动
                     Class.forName("com.mysql.cj.jdbc.Driver");
                 
                     // 打开连接
                     JOptionPane.showMessageDialog(null,"连接数据库...");
                     
                     conn = DriverManager.getConnection(DB_URL,USER,PASS);//getConnection方法，连接MySQL数据库
                 
                     // 执行查询
                     stmt = conn.createStatement();
                     String sql;
                     sql = "SELECT account, password, isLogin FROM administrator";
                     ResultSet rs = stmt.executeQuery(sql);
                     Administrator.account = taccount.getText();
              		Administrator.password = String.valueOf(tpassword.getPassword());
              		checkcode1 = checkcode.getText();
                     
                     while(rs.next()){
                    	
                         if(rs.getString("account").contentEquals(Administrator.account)
                        		 &&rs.getString("password").contentEquals(Administrator.password)
                        		 &&rs.getBoolean("isLogin") == false) {
                        	 m++;
                        	 Administrator.isLogin = true;
                        	 sql="update administrator set isLogin=1";//修改登录状态
                        	 stmt.executeUpdate(sql);//执行SQL语句
                        	 frame.setVisible(false);
                        	 Administrator.swingCJ();
                 
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
            		// 提示还剩几次机会
            		JOptionPane.showMessageDialog(null,"用户名或密码有误,请重新输入!" + "还剩" + (3 - num) + "次");
            		// 判断登录错了几次
            		if (num != 3) {
            			// 继续登录
            			taccount.setText("");
            	        tpassword.setText("");
            	        checkcode.setText("");
            		} else {
            			// 登录失败
            			JOptionPane.showMessageDialog(null,"对不起三次机会用完,请明日再来!");
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
