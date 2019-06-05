import	 java.sql.*;
import	javax.swing.*;
import	java.awt.*;
import	java.awt.event.*;

public class Register {
	static JFrame frame;
	static JButton register,out;
	static JLabel laccount,lpassword,lpassword2;
	static JTextField taccount;
	static JPasswordField tpassword,tpassword2;
	
	public static void userRegister(){
		frame = new JFrame();
		register = new JButton("注册");
		out = new JButton("返回");
		laccount = new JLabel("用户名");
        lpassword = new JLabel("密码");
        lpassword2 = new JLabel("确认密码");
        taccount = new JTextField();
        tpassword = new JPasswordField();
        tpassword2 = new JPasswordField();
        
		frame.setBounds(100, 100, 300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);//将布局管理器置为空
        frame.setVisible(true);
        frame.setTitle("注册");
        
        register.setBounds(180, 198, 93, 23);
        register.setBackground(Color.ORANGE);
        register.addActionListener(new Button());
        out.setBounds(156, 198, 93, 23);
        out.setBackground(Color.ORANGE);
        out.addActionListener(new Out());
        frame.getContentPane().add(register);
        frame.getContentPane().add(out);
        
        laccount.setBounds(140, 31, 34, 23);
        lpassword.setBounds(140, 73, 34, 23);
        lpassword2.setBounds(140, 121, 34, 23);
        frame.getContentPane().add(laccount);
        frame.getContentPane().add(lpassword);
        frame.getContentPane().add(lpassword2);
         
        taccount.setBounds(182, 31, 79, 21);
        tpassword.setBounds(182, 73, 79, 21);
        tpassword2.setBounds(182, 123, 79, 21);
        frame.getContentPane().add(taccount);
        frame.getContentPane().add(tpassword);
        frame.getContentPane().add(tpassword2);
        
		
	}
	private static void clearText() {
		taccount.setText("");
		tpassword.setText("");
		tpassword2.setText("");
	}
	static class Button implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// 接收用户信息
	        User.account = taccount.getText();
	        String m = String.valueOf(tpassword.getPassword());
	        String n = String.valueOf(tpassword2.getPassword());
	        if(m.equals(n)) {
	        	User.password = n;
	        }
			User.number = (int)(Math.random() * 1001 + 1000);
	        User.isLogin = false;
	        User.isWin = false;
	        int k = 0;
	        // 存储到数据库
	        final String DB_URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8";
		    final String USER = "root";
		    final String PASS = "123456";
		    
		    try {
				// 获取数据库的连接
				Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
				Statement stmt = conn.createStatement();
				String sql = "SELECT account, password, number, isLogin, isWin FROM lottery";
	            ResultSet rs = stmt.executeQuery(sql);
	            
	            while(rs.next()){
	                if(rs.getString("account").contentEquals(User.account)) {
	                	k++;           	
	                	}
	            	}
	            if(k!=0) {
	            	JOptionPane.showMessageDialog(null, "用户名已存在，请重新输入",null,JOptionPane.ERROR_MESSAGE);
	            	clearText();
	            }else {
	            	// 设置SQL规则，数据由于还不知道先用？代替
	    			sql = "INSERT INTO lottery(account,password,number,isLogin,isWin) VALUES (?,?,?,?,?)";
	    			// 预处理sql语句
	    			PreparedStatement presta = conn.prepareStatement(sql);
	    			// 设置sql语句中的values值
	    			presta.setString(1, User.account);
	    			presta.setString(2, User.password);
	    			presta.setInt(3, User.number);
	    			presta.setBoolean(4, User.isLogin);
	    			presta.setBoolean(5, User.isWin );
	    			// 执行SQL语句，实现数据添加
	    			presta.execute();
	    			//  打印注册信息
	    		    JOptionPane.showMessageDialog(null, "注册成功！\n用户名："+User.account+"\n抽奖号码："+User.number); 
	    		    frame.setVisible(false);
	            }
	} 
		    catch (SQLException e1) {
					e1.printStackTrace();
				 }
			frame.setVisible(false);
			Register.userRegister();
		}
	}
	static class Out implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			Start.frame.setVisible(true);
		}
	}
}
