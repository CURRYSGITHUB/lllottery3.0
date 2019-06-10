import	 java.sql.*;
import	javax.swing.*;
import	java.awt.*;
import	java.awt.event.*;

public class Register {
	static JFrame frame;
	static JButton register,out;
	static JLabel laccount,lpassword,lpassword2,ltitle;
	static JTextField taccount;
	static JPasswordField tpassword,tpassword2;
	static ImageIcon bgImage = new ImageIcon("C:/Users/Damien/Desktop/bg.jpg");
	static JLabel bgLabel = new JLabel(bgImage);
	
	public static void userRegister(){
		frame = new JFrame();
		Font font1 = new Font("serif",Font.BOLD,24);
		register = new JButton("注 册");
		register.setFont(font1);
		out = new JButton("返 回");
		out.setFont(font1);
		Font font2 = new Font("serif",Font.BOLD,26);
		laccount = new JLabel("用 户 名");
        lpassword = new JLabel("密      码");
        lpassword2 = new JLabel("确认密码");
        laccount.setFont(font2);
        lpassword.setFont(font2);
        lpassword2.setFont(font2);
        ltitle = new JLabel("用  户  注  册");
        Font font3 = new Font("微软雅黑",Font.BOLD,48);
        ltitle.setFont(font3);
        taccount = new JTextField();
        tpassword = new JPasswordField();
        tpassword2 = new JPasswordField();
        taccount.setFont(font1);
        tpassword.setFont(font1);
        tpassword2.setFont(font1);
        
		frame.setSize(950, 750);
		frame.setLocationRelativeTo(null);
		bgLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		JPanel imagePanel = (JPanel) frame.getContentPane();
		imagePanel.setOpaque(false);
		// 把背景图片添加到分层窗格的最底层作为背景
		frame.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
		frame.setResizable(false);
        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);//将布局管理器置为空
        frame.setVisible(true);
        frame.setTitle("注册");
        
        
        register.setBounds(500, 500, 232, 57);
        register.setBackground(Color.ORANGE);
        register.addActionListener(new Button());
        out.setBounds(200, 500, 232, 57);
        out.setBackground(Color.ORANGE);
        out.addActionListener(new Out());
        frame.getContentPane().add(register);
        frame.getContentPane().add(out);
        
        laccount.setBounds(295, 200, 100, 57);
        lpassword.setBounds(295, 281, 100, 57);
        lpassword2.setBounds(295, 362, 150, 57);
        ltitle.setBounds(330,70,280,80);
        frame.getContentPane().add(laccount);
        frame.getContentPane().add(lpassword);
        frame.getContentPane().add(lpassword2);
        frame.getContentPane().add(ltitle);
         
        taccount.setBounds(420, 205, 197, 52);
        tpassword.setBounds(420, 286, 197, 52);
        tpassword2.setBounds(420, 367, 197, 52);
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
	        User.isLogin = false;
	        int k = 0;
	        // 存储到数据库
	        final String DB_URL = "jdbc:mysql://localhost:3306/world?serverTimezone=GMT%2B8";
		    final String USER = "root";
		    final String PASS = "1315202benny";
		    
		    try {
				// 获取数据库的连接
				Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
				Statement stmt = conn.createStatement();
				String sql = "SELECT account, password, isLogin FROM lottery";
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
	    			sql = "INSERT INTO lottery(account,password,isLogin,ftimes,stimes,ttimes) VALUES (?,?,?,?,?)";
	    			// 预处理sql语句
	    			PreparedStatement presta = conn.prepareStatement(sql);
	    			// 设置sql语句中的values值
	    			presta.setString(1, User.account);
	    			presta.setString(2, User.password);
	    			presta.setBoolean(3, User.isLogin);
	    			presta.setInt(4, 0);
	    			presta.setInt(5, 0);
	    			presta.setInt(6, 0);
	    			// 执行SQL语句，实现数据添加
	    			presta.execute();
	    			//  打印注册信息
	    		    JOptionPane.showMessageDialog(null, "注册成功！\n用户名："+User.account); 
	    		    frame.setVisible(false);
			    Start.frame.setVisible(true);
	            }
	} 
		    catch (SQLException e1) {
					e1.printStackTrace();
				 }
		}
	}
	static class Out implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			Start.frame.setVisible(true);
		}
	}
}
