import	 java.sql.*;
import	javax.swing.*;
import	java.awt.*;
import	java.awt.event.*;

public class User extends Person{
	
	public static int number = 0;
	public static boolean isWin = false;
	public static int b;
	
	// JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8";//urlָ��Ҫ���ʵ����ݿ���
 
    // MySQL����ʱ���û���������
    static final String USER = "root";
    static final String PASS = "123456";
    
	static JFrame frame;
	JButton b1,b2,b3;
	public void swingCJ() {
		frame = new JFrame();
		b1 = new JButton("��ʼ�齱");
		b2 = new JButton("�鿴�н���Ϣ");
		b3 = new JButton("�˳���¼");
		
		frame.setBounds(100, 100, 300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);//�����ֹ�������Ϊ��
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
	        frame.getContentPane().setLayout(null);//�����ֹ�������Ϊ��
	        frame.setVisible(true);
	        frame.setTitle("�н���Ϣ��ѯ");
	        JLabel CJinfo;

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
		            sql = "SELECT account, number, FROM lottery";
		            ResultSet rs = stmt.executeQuery(sql);
		        
		            // չ����������ݿ�
		            while(rs.next()){
		                // ͨ���ֶμ���
		                String account = rs.getString("account");
		                num = rs.getInt("number");
                        //�ҵ��û���Ϣ
		                if(User.account == account) {
		                	break;
		                }
		            }
					if(num == Play.cardNumber) {
		                 
		                 CJinfo = new JLabel("��ϲ�����н�����");
		                 CJinfo.setBounds(140, 31, 34, 23);
		                 frame.getContentPane().add(CJinfo);
		             }
		             else {
		            	 CJinfo = new JLabel("���ź�����δ�н���");
		            	 CJinfo.setBounds(140, 31, 34, 23);
		                 frame.getContentPane().add(CJinfo);
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
			
	}     
	class B3 implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
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
		            sql = "SELECT account, isLogin, FROM lottery";
		            ResultSet rs = stmt.executeQuery(sql);
		        
		            // չ����������ݿ�
		            while(rs.next()){
		                // ͨ���ֶμ���
		                String account = rs.getString("account");
		                @SuppressWarnings("unused")
						boolean isLogin = rs.getBoolean("isLogin");
		                
		                if(User.account == account) {
		                	isLogin = false;
		                	break;
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
		}
	}     
