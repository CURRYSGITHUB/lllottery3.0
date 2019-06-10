import   java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdmiLogin {
	
	static String checkcode1;
	
	static JFrame frame;
	static JButton AdmiLogin,out;
	static JLabel laccount;
	static JLabel lpassword;
	static JLabel lpassword2;
	static JLabel ltitle;
	static JTextField taccount,checkcode;
	static JPasswordField tpassword;
	static ValidCode vcode; 
	
	// 保存登录失败的次数
    static int num = 0;
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/world?serverTimezone=GMT%2B8";//url指向要访问的数据库名
 
    // MySQL配置时的用户名与密码
    static final String USER = "root";
    static final String PASS = "1315202benny";
    
    static ImageIcon bgImage = new ImageIcon("C:/Users/Damien/Desktop/bg.jpg");
	static JLabel bgLabel = new JLabel(bgImage);
    
    static int date1 = 0;
    // 登录方法
    // 静态方法里面 需要使用静态的成员变量
    public static void admiLogin() {
    	
    	frame = new JFrame();
    	AdmiLogin = new JButton("确认登录");
    	frame.setSize(950, 750);
		frame.setLocationRelativeTo(null);
		bgLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		JPanel imagePanel = (JPanel) frame.getContentPane();
		imagePanel.setOpaque(false);
		// 把背景图片添加到分层窗格的最底层作为背景
		frame.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
        frame.getContentPane().setLayout(null);//将布局管理器置为空
        frame.setVisible(true);
        frame.setTitle("用户登录");
        
        Font font1 = new Font("serif",Font.BOLD,24);
    	AdmiLogin = new JButton("确 认 登 录");
    	out = new JButton("返 回");
    	AdmiLogin.setFont(font1);
    	out.setFont(font1);
        AdmiLogin.setBounds(500, 500, 232, 57);
        AdmiLogin.setBackground(Color.ORANGE);
        out.setBounds(200, 500, 232, 57);
        out.setBackground(Color.ORANGE);
        AdmiLogin.addActionListener(new AdmiLogin1());
        out.addActionListener(new Out());
        frame.getContentPane().add(AdmiLogin);
        frame.getContentPane().add(out);
        
        Font font2 = new Font("serif",Font.BOLD,26);
        Font font3 = new Font("微软雅黑",Font.BOLD,48);
        laccount = new JLabel("用 户 名：");
        lpassword = new JLabel("密     码：");
        lpassword2 = new JLabel("验 证 码：");
        ltitle = new JLabel("管  理  员  登  录");
        laccount.setFont(font2);
        lpassword.setFont(font2);
        lpassword2.setFont(font2);
        ltitle.setFont(font3);
        laccount.setBounds(295, 200, 150, 57);
        lpassword.setBounds(295, 281, 150, 57);
        lpassword2.setBounds(295, 362, 150, 57);
        ltitle.setBounds(285,70,400,80);
        frame.getContentPane().add(laccount);
        frame.getContentPane().add(lpassword);
        frame.getContentPane().add(lpassword2);
        frame.getContentPane().add(ltitle);
        
        taccount = new JTextField();
        tpassword = new JPasswordField();
        checkcode = new JTextField();
        taccount.setBounds(420, 205, 197, 52);
        tpassword.setBounds(420, 286, 197, 52);
        checkcode.setBounds(420, 367, 197, 52);
        taccount.setFont(font2);
        tpassword.setFont(font2);
        checkcode.setFont(font2);
        frame.getContentPane().add(taccount);
        frame.getContentPane().add(tpassword);
        frame.getContentPane().add(checkcode);
        
        vcode = new ValidCode();
		vcode.setBounds(650, 372, 150, 40);
		frame.add(vcode);
		
    	
     	}
    static class AdmiLogin1 implements ActionListener{
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
            		frame.setVisible(false);
          		    admiLogin();
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
            			Start.cjStart();
            			// 输入卡号次数重置
            			date1 = date2;//避免第四次输入
            			num = 0;   
            		}
                 }
            	}

     	}

    }
    
    static class Out implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			Start.frame.setVisible(true);
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
