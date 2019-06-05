import   java.util.*;
import java.sql.*;
import	javax.swing.*;
import	java.awt.*;
import	java.awt.event.*;

public class Login {
	// �����¼ʧ�ܵĴ���
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
	
    // JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8";//urlָ��Ҫ���ʵ����ݿ���
 
    // MySQL����ʱ���û���������
    static final String USER = "root";
    static final String PASS = "123456";
    

    static int date1 = 0;
    // ��¼����
    // ��̬�������� ��Ҫʹ�þ�̬�ĳ�Ա����
    public static void userLogin() {
        
    	frame = new JFrame();
    	Login = new JButton("ȷ�ϵ�¼");
    	taccount = new JTextField();
    	
    	frame.setBounds(100, 100, 300, 300);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);//�����ֹ�������Ϊ��
        frame.setVisible(true);
        frame.setTitle("�û���¼");
        Login.setBounds(180, 198, 93, 23);
        Login.setBackground(Color.ORANGE);
        Login.addActionListener(new Login1());
        frame.getContentPane().add(Login);
        
        laccount = new JLabel("�û�����");
        lpassword = new JLabel("���룺");
        lpassword2 = new JLabel("��֤�룺");
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
        	//��ȡ��ǰϵͳ����
        	Calendar c = Calendar.getInstance();
        	int date2 = c.get(Calendar.DATE);

            // �ж�ƥ���¼��Ϣ
            Connection conn = null;//����connection����
            Statement stmt = null;//����statement���������ִ��sql���
            if(date1!=date2) {
                try{
                     // ע�� JDBC ����
                     Class.forName("com.mysql.cj.jdbc.Driver");
                 
                     // ������
                     conn = DriverManager.getConnection(DB_URL,USER,PASS);//getConnection����������MySQL���ݿ�
                 
                     // ִ�в�ѯ
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
                        	 sql="update lottery set isLogin=1";//�޸ĵ�¼״̬
                        	 stmt.executeUpdate(sql);//ִ��SQL���
                        	 frame.setVisible(false);
                        	 User.frame.setVisible(true);
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
                 if(m == 0) {	
            		num++;
            		// ��ʾ�û���ʣ���λ���
            	   JOptionPane.showMessageDialog(null, "�˻�����������㻹��"+(3-num)+"�λ���");
            		// �жϵ�¼���˼���
            		if (num != 3) {
            			// ������¼
            		   userLogin();
            		} else {
            			// ��¼ʧ��
            			JOptionPane.showMessageDialog(null, "�˻���������󣬽��ջ���������");
           			// ���ü�¼��¼�����ı���
            			// ���뿨�Ŵ�������
            			date1 = date2;//������Ĵ�����
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