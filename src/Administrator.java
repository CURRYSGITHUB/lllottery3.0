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
		// JDBC �����������ݿ� URL
	    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	    static final String DB_URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8";//urlָ��Ҫ���ʵ����ݿ���
	 
	    // MySQL����ʱ���û���������
	    static final String USER = "root";
	    static final String PASS = "123456";
	    //��ʾ�û���Ϣ
	    static double rate = 0;
	    
	    static JFrame frame;
	    static JButton b1,b2,b3,b4,b0;
	    
	    public static void swingCJ() {
			frame = new JFrame();
			b1 = new JButton("�鿴�û���Ϣ");
			b2 = new JButton("�鿴�н��û�");
			b3 = new JButton("�����н�����");
			b4 = new JButton("��ʾ�н�����");
			b0 = new JButton("�˳���½");
			
			frame.setBounds(100, 100, 300, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(null);//�����ֹ�������Ϊ��
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
	    //�鿴�����û�
static class  B1 implements ActionListener{
	    	public  void userInfo() {	    		
	    		frame = new JFrame();
				frame.setBounds(100, 100, 300, 300);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.getContentPane().setLayout(null);//�����ֹ�������Ϊ��
		        frame.setVisible(true);
		        frame.setTitle("ȫ���û���Ϣ");		        
		        JLabel label = new JLabel();
		        
		        JButton b0 = new JButton("����");		   
		        b0.setBounds(180, 198, 93, 23);
		        b0.setBackground(Color.ORANGE);
		        b0.addActionListener(new B0());
		        
		        Connection conn = null;//����connection����
		        Statement stmt = null;//����statement���������ִ��sql���
		        try{
		            // ע�� JDBC ����
		            Class.forName("com.mysql.cj.jdbc.Driver");
		        
		            // ������
		            conn = DriverManager.getConnection(DB_URL,USER,PASS);//getConnection����������MySQL���ݿ�
		        
		            // ִ�в�ѯ
		            stmt = conn.createStatement();
		            String sql;
		            sql = "SELECT account, number, isLogin FROM administrator";
		            ResultSet rs = stmt.executeQuery(sql);
		        
		            // չ����������ݿ�;���ע�����Ա�ࣿ
		            while(rs.next()){
		                // ͨ���ֶμ���
		                int number  = rs.getInt("number");
		                String account = rs.getString("account");
		                boolean isLogin = rs.getBoolean("isLogin");
		    
		                // �������
		                label.setText("Account " + account);
		                label.setText("Number " + number);
		                label.setText(", �Ƿ��¼: " + isLogin);
		                label.setText("\n");
		            }
		            // ��ɺ�ر�
		            rs.close();
		            stmt.close();
		            conn.close();
		        }catch(SQLException se){
		            // ���� JDBC ����
		            se.printStackTrace();
		        }catch(Exception e){
		            // ���� Class.forName ����
		            e.printStackTrace();
		        }finally{
		            // �ر���Դ
		            try{
		                if(stmt!=null) stmt.close();
		            }catch(SQLException se2){
		            	
		            }// ʲô������
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

	    //�鿴�н��û�
static class B2 implements ActionListener{	    	
	    	public void announce()  {
	    		frame = new JFrame();
				frame.setBounds(100, 100, 300, 300);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.getContentPane().setLayout(null);//�����ֹ�������Ϊ��
		        frame.setVisible(true);
		        frame.setTitle("�н��û���Ϣ");		        
		        JLabel label = new JLabel();
		        
		        JButton b0 = new JButton("����");		   
		        b0.setBounds(180, 198, 93, 23);
		        b0.setBackground(Color.ORANGE);
		        b0.addActionListener(new B0());
		        
		    	 Connection conn = null;//����connection����
			     Statement stmt = null;//����statement���������ִ��sql���
		    	try{
		            // ע�� JDBC ����
		            Class.forName("com.mysql.cj.jdbc.Driver");
		        
		            // ������
		            conn = DriverManager.getConnection(DB_URL,USER,PASS);//getConnection����������MySQL���ݿ�
		        
		            // ִ�в�ѯ
		            stmt = conn.createStatement();
		            String sql;
		            sql = "SELECT account, number, isLogin��isWin FROM lottery";
		            ResultSet rs = stmt.executeQuery(sql);
		            // չ����������ݿ�
		            while(rs.next()){
		                // ͨ���ֶμ���
		            	if(rs.getBoolean("isWin")) {
		            		label.setText("���û�Ϊ:"+ rs.getString(account));
		            	}
		            }
		            // ��ɺ�ر�
		            rs.close();
		            stmt.close();
		            conn.close();
		        }catch(SQLException se){
		            // ���� JDBC ����
		            se.printStackTrace();
		        }catch(Exception e){
		            // ���� Class.forName ����
		            e.printStackTrace();
		        }finally{
		            // �ر���Դ
		            try{
		                if(stmt!=null) stmt.close();
		            }catch(SQLException se2){
		            }// ʲô������
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
	    
	    //�����н�����
static class B3 implements ActionListener{
	public void setRate() {
    	String rate0 = (String)JOptionPane.showInputDialog("�����ó齱����");
    	Scanner scanner = new Scanner(System.in);
        // ������Ϣ
        Administrator.rate = Double.parseDouble(rate0);    
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

	    //��ȡ�н�����
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
