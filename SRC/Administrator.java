import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;


public class Administrator extends Person{
		// JDBC 驱动名及数据库 URL
	    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	    static final String DB_URL = "jdbc:mysql://localhost:3306/world?serverTimezone=GMT%2B8";//url指向要访问的数据库名
	 
	    // MySQL配置时的用户名与密码
	    static final String USER = "root";
	    static final String PASS = "1315202benny";
	    //显示用户信息
	    
	    static JFrame frame;
	    static JButton b1,b2,b3,b4,b5;
	    
	    static ImageIcon bgImage = new ImageIcon("C:/Users/Damien/Desktop/bg.jpg");
		static JLabel bgLabel = new JLabel(bgImage);
		
		static String u1,u2,u3;
		static Double r1,r2,r3;
		static JLabel url1,url2,url3;
	    static JTextField gr1,gr2,gr3;
	    
	    public static void swingCJ() {
			frame = new JFrame();
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
			frame.setVisible(true);
			
			b1 = new JButton("查 看 用 户 信 息");
			b2 = new JButton("查 看 中 奖 用 户");
			b3 = new JButton("设 置 中 奖 概 率");
			b4 = new JButton("显 示 中 奖 概 率");
			b5 = new JButton("退 出 登 陆");
			Font font1 = new Font("serif",Font.BOLD,24);
			b1.setFont(font1);
			b2.setFont(font1);
			b3.setFont(font1);
			b4.setFont(font1);
			b5.setFont(font1);
			
			b1.setBounds(270, 100, 400, 52);
			b2.setBounds(270, 200, 400, 52);
	        b3.setBounds(270, 300, 400, 52);
	        b4.setBounds(270, 400, 400, 52);
	        b5.setBounds(270, 500, 400, 52);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(null);//将布局管理器置为空
	        frame.setVisible(true);
	        
	       
	        b1.setBackground(Color.ORANGE);
	        b1.addActionListener(new B1());
	        
	        b2.setBackground(Color.ORANGE);
	        b2.addActionListener(new B2());
	       
	        b3.setBackground(Color.ORANGE);
	        b3.addActionListener(new B3());
	       
	        b4.setBackground(Color.ORANGE);
	        b4.addActionListener(new B4());
	        
	        b5.setBackground(Color.ORANGE);
	        b5.addActionListener(new B5());
	        
	        frame.getContentPane().add(b1);
	        frame.getContentPane().add(b2);  
	        frame.getContentPane().add(b3); 
	        frame.getContentPane().add(b4);
	        frame.getContentPane().add(b5);
	        
	    	   
		}	    
	    //查看所有用户
static class  B1 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Administrator.frame.setVisible(false);
				frame = new JFrame();
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
		        frame.setTitle("全体用户信息");		        
		        JLabel label;
		        
		        JButton b0 = new JButton("返回");		   
		        b0.setBounds(750, 600, 100, 50);
		        b0.setBackground(Color.ORANGE);
		        b0.addActionListener(new B0());
		        frame.getContentPane().add(b0);
		        
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
		            sql = "SELECT account, isLogin FROM lottery";
		            ResultSet rs = stmt.executeQuery(sql);
		            
		            int i=50;
		            Font font1 = new Font("微软雅黑",Font.BOLD,20);
		        
		            // 展开结果集数据库;添加注册管理员类？
		            while(rs.next()){
		                // 通过字段检索
		                String account = rs.getString("account");
		                boolean isLogin = rs.getBoolean("isLogin");
		    
		                // 输出数据
		                label=new JLabel();
		                //设置label字体大小
		                label.setFont(font1);
		                label.setText("Account: " + account);
		                label.setBounds(150,i,1000,50);
		                frame.add(label);
		                label=new JLabel();
		                label.setFont(font1);
		                label.setText("是否登录:  " + isLogin);
		                label.setBounds(150,i+30,1000,50);
		                frame.add(label);
		                
		                i=i+90;
		            }
		            // 完成后关闭
		            rs.close();
		            stmt.close();
		            conn.close();
		        }catch(SQLException se){
		            // 处理 JDBC 错误
		            se.printStackTrace();
		        }catch(Exception e1){
		            // 处理 Class.forName 错误
		            e1.printStackTrace();
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

	    //查看中奖用户
static class B2 implements ActionListener{	    	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Administrator.frame.setVisible(false);
				frame = new JFrame();
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
		        frame.setTitle("中奖用户信息");		        
		        JLabel label;
		        
		        JButton b0 = new JButton("返回");		   
		        b0.setBounds(750, 600, 100, 50);
		        b0.setBackground(Color.ORANGE);
		        b0.addActionListener(new B0());
		        frame.getContentPane().add(b0);
		        
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
		            sql = "SELECT account, password, isLogin, ftimes, stimes, ttimes FROM lottery";
		            ResultSet rs = stmt.executeQuery(sql);
		            
		            int i=50;
		            Font font1 = new Font("微软雅黑",Font.BOLD,20);
		            
		            // 展开结果集数据库
		            while(rs.next()){
					     int ftimes=0;
					     int stimes=0;
					     int ttimes=0;
		            	
		            	label = new JLabel();
		                // 通过字段检索
		            	String taccount = rs.getString("account");
		            	if(rs.getInt("ftimes")==0&&rs.getInt("stimes")==0&&rs.getInt("ttimes")==0) {
		            		//显示无中奖用户
		            		label.setText("无中奖用户");
		            	}
		            	else {
		            		if(rs.getInt("ftimes")!=0) {
		            			 ftimes = rs.getInt("ftimes");
		            		}
		            		if(rs.getInt("stimes")!=0) {
		            			 stimes = rs.getInt("stimes");
		            		}
		            		if(rs.getInt("ttimes")!=0) {
		            			 ttimes = rs.getInt("ttimes");
		            		}		
		            	}
		            	//在这里开始输出 此用户 一等奖ftimes次 二等奖stimes次 三等奖 ttimes次
		            	label.setFont(font1);
		            	label.setText("账户: " + taccount+"            一等奖×" + ftimes+"     二等奖×" + stimes+"     三等奖×" + ttimes);
		                label.setBounds(50, i, 750, 50);
		                frame.add(label);
		                
		                i=i+50;
		           
		            }
		            // 完成后关闭
		            rs.close();
		            stmt.close();
		            conn.close();
		        }catch(SQLException se){
		            // 处理 JDBC 错误
		            se.printStackTrace();
		        }catch(Exception e1){
		            // 处理 Class.forName 错误
		            e1.printStackTrace();
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
	    //设置奖品概率
static class B3 implements ActionListener{
	static String u1,u2,u3;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Administrator.frame.setVisible(false);
		
		Font font1 = new Font("微软雅黑",Font.BOLD,35);
		Font font2 = new Font("serif",Font.BOLD,20);
		Font font3 = new Font("serif",Font.BOLD,10);
		
		frame = new JFrame();
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
        frame.setTitle("设置奖品");		        
        
        JButton b0 = new JButton("返回");		   
        b0.setBounds(600, 600, 100, 50);
        b0.setBackground(Color.ORANGE);
        b0.addActionListener(new B0());
        frame.getContentPane().add(b0);
        JButton b00 = new JButton("确定");		   
        b00.setBounds(200, 600, 100, 50);
        b00.setBackground(Color.ORANGE);
        b00.addActionListener(new B00());
        frame.getContentPane().add(b00);
        
        JLabel flabel,slabel,tlabel,rate,url;
        
        flabel=new JLabel("一等奖");
        slabel=new JLabel("二等奖");
        tlabel=new JLabel("三等奖");
        flabel.setFont(font1);
        slabel.setFont(font1);
        tlabel.setFont(font1);
        flabel.setBounds(400, 30, 280, 80);
        slabel.setBounds(400, 230, 280, 80);
        tlabel.setBounds(400, 430, 280, 80);
        frame.getContentPane().add(flabel);
        frame.getContentPane().add(slabel);
        frame.getContentPane().add(tlabel);
        
        int i=0,j=0;
        
        for(i=100,j=0;j<3;i=i+200,j=j+1) {
        	url = new JLabel("图片路径: ___________________________________");
        	url.setFont(font2);
        	url.setBounds(100, i,455, 50);
        	frame.add(url);
        }
        
        for(i=100,j=0;j<3;i=i+200,j=j+1) {
        	rate = new JLabel("概率: ");
        	rate.setFont(font2);
        	rate.setBounds(750, i,100, 50);
        	frame.add(rate);
        }
        
        url1 = new JLabel();
        url2 = new JLabel();
        url3 = new JLabel();
        url1.setFont(font2);
        url2.setFont(font2);
        url3.setFont(font2);
        url1.setBounds(200, 99, 300, 50);
        url2.setBounds(200, 299, 300, 50);
        url3.setBounds(200, 499, 300, 50);
        frame.add(url1);
        frame.add(url2);
        frame.add(url3);
        
        JButton bu1 = new JButton("浏览路径");
        JButton bu2 = new JButton("浏览路径");
        JButton bu3 = new JButton("浏览路径");
        bu1.setFont(font3);
        bu2.setFont(font3);
        bu3.setFont(font3);
        bu1.setBounds(550, 110, 100, 30);
        bu2.setBounds(550, 310, 100, 30);
        bu3.setBounds(550, 510, 100, 30);
        bu1.addActionListener(new Bu1());
        bu2.addActionListener(new Bu2());
        bu3.addActionListener(new Bu3());
        frame.getContentPane().add(bu1);
        frame.getContentPane().add(bu2);
        frame.getContentPane().add(bu3);
        


        
        gr1 = new JTextField();
        gr2 = new JTextField();
        gr3 = new JTextField();
        gr1.setFont(font2);
        gr2.setFont(font2);
        gr3.setFont(font2);
        gr1.setBounds(800, 110, 50, 30);
        gr2.setBounds(800, 310, 50, 30);
        gr3.setBounds(800, 510, 50, 30);
        frame.add(gr1);
        frame.add(gr2);
        frame.add(gr3);
        
	}
}

	    //获取中奖概率
static class B4 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
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
           sql = "SELECT fprize, sprize, tprize FROM setrate";
           ResultSet rs = stmt.executeQuery(sql);
           
           // 展开结果集数据库
           while(rs.next()){
        	   JOptionPane.showMessageDialog(null, "一等奖中奖率："+rs.getDouble("fprize")+"\n二等奖中奖率："+rs.getDouble("sprize")+"\n三等奖中奖率："+rs.getDouble("tprize"));
           }
           // 完成后关闭
           rs.close();
           stmt.close();
           conn.close();
       }catch(SQLException se){
           // 处理 JDBC 错误
           se.printStackTrace();
       }catch(Exception e1){
           // 处理 Class.forName 错误
           e1.printStackTrace();
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


static class B5 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
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
	            sql="update administrator set isLogin=0 ";//修改登录状态
           	    stmt.executeUpdate(sql);//执行SQL语句

	            // 完成后关闭
	            stmt.close();
	            conn.close();
	        }catch(SQLException se){
	            // 处理 JDBC 错误
	            se.printStackTrace();
	        }catch(Exception e1){
	            // 处理 Class.forName 错误
	            e1.printStackTrace();
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
	    JOptionPane.showMessageDialog(null, Administrator.account+"\n退出登录成功！"); 
	    Administrator.frame.setVisible(false);
	    Start.cjStart();
	}
}

static class B0 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		frame.setVisible(false);
		swingCJ();
	}
	
}

@SuppressWarnings("unused")
//设置概率
private static void setrate(double a,double b,double c) {
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
            sql = "INSERT INTO setrate(first prize, second prize, third prize) VALUES (?,?,?)";
			// 预处理sql语句
			PreparedStatement presta = conn.prepareStatement(sql);
			// 设置sql语句中的values值
			presta.setDouble(1, a);
			presta.setDouble(2, b);
			presta.setDouble(3, c);
			presta.execute();
			//  打印注册信息
		    JOptionPane.showMessageDialog(null, "一等奖概率："+a+"二等奖概率："+b+"三等奖概率："+c); 
		    frame.setVisible(false);
		
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
public static void setimage(int a,String b) {
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
            sql = "INSERT INTO img(imgID, imgURL) VALUES (?,?)";
			// 预处理sql语句
			PreparedStatement presta = conn.prepareStatement(sql);
			// 设置sql语句中的values值
			
			//用户输入
			int id = a;
			String imageurl = b;
			
			
			presta.setInt(1, id);
			presta.setString(2, imageurl);
			presta.execute();

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

static class Bu1 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int result = 0;
		String path = null;
		JFileChooser fileChooser = new JFileChooser();
		FileSystemView fsv = FileSystemView.getFileSystemView(); //注意了，这里重要的一句
		fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
		fileChooser.setDialogTitle("请选择要上传的文件...");
		fileChooser.setApproveButtonText("确定");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		Component chatFrame = null;
		result = fileChooser.showOpenDialog(chatFrame);
		if (JFileChooser.APPROVE_OPTION == result) {
		path=fileChooser.getSelectedFile().getPath();
		}
		u1=path;
		url1.setText(u1);
	}
}

static class Bu2 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int result = 0;
		String path = null;
		JFileChooser fileChooser = new JFileChooser();
		FileSystemView fsv = FileSystemView.getFileSystemView(); //注意了，这里重要的一句
		fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
		fileChooser.setDialogTitle("请选择要上传的文件...");
		fileChooser.setApproveButtonText("确定");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		Component chatFrame = null;
		result = fileChooser.showOpenDialog(chatFrame);
		if (JFileChooser.APPROVE_OPTION == result) {
		path=fileChooser.getSelectedFile().getPath();
		}
		u2=path;
		url2.setText(u2);
	}
}

static class Bu3 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int result = 0;
		String path = null;
		JFileChooser fileChooser = new JFileChooser();
		FileSystemView fsv = FileSystemView.getFileSystemView(); //注意了，这里重要的一句
		fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
		fileChooser.setDialogTitle("请选择要上传的文件...");
		fileChooser.setApproveButtonText("确定");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		Component chatFrame = null;
		result = fileChooser.showOpenDialog(chatFrame);
		if (JFileChooser.APPROVE_OPTION == result) {
		path=fileChooser.getSelectedFile().getPath();
		}
		u3=path;
		url3.setText(u3);
	}
}


static class B00 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		r1=Double.parseDouble(gr1.getText());
		r2=Double.parseDouble(gr2.getText());
		r3=Double.parseDouble(gr3.getText());
		setrate(r1,r2,r3);
		setimage(1,u1);
		setimage(2,u2);
		setimage(3,u3);
		
	}
	
    }
}
