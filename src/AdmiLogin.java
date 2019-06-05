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
	
	// �����¼ʧ�ܵĴ���
    static int num = 0;
    // JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8";//urlָ��Ҫ���ʵ����ݿ���
 
    // MySQL����ʱ���û���������
    static final String USER = "root";
    static final String PASS = "123456";
    
    static int date1 = 0;
    // ��¼����
    // ��̬�������� ��Ҫʹ�þ�̬�ĳ�Ա����
    public static void admiLogin() {
    	
    	frame = new JFrame();
    	AdmiLogin = new JButton("ȷ�ϵ�¼");
    	
    	frame.setBounds(100, 100, 300, 300);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);//�����ֹ�������Ϊ��
        frame.setVisible(true);
        frame.setTitle("�û���¼");
        AdmiLogin.setBounds(180, 198, 93, 23);
        AdmiLogin.setBackground(Color.ORANGE);
        AdmiLogin.addActionListener(new admiLogin1());
        frame.getContentPane().add(AdmiLogin);
        
        laccount = new JLabel("�û���");
        lpassword = new JLabel("����");
        lpassword2 = new JLabel("��֤��");
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
        	//��ȡ��ǰϵͳ����
        	Calendar c = Calendar.getInstance();
        	int date2 = c.get(Calendar.DATE);
            // ������Ϣ

            Connection conn = null;//����connection����
            Statement stmt = null;//����statement���������ִ��sql���
            if(date1!=date2) {
                try{
                     // ע�� JDBC ����
                     Class.forName("com.mysql.cj.jdbc.Driver");
                 
                     // ������
                     JOptionPane.showMessageDialog(null,"�������ݿ�...");
                     
                     conn = DriverManager.getConnection(DB_URL,USER,PASS);//getConnection����������MySQL���ݿ�
                 
                     // ִ�в�ѯ
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
                        	 sql="update administrator set isLogin=1";//�޸ĵ�¼״̬
                        	 stmt.executeUpdate(sql);//ִ��SQL���
                        	 frame.setVisible(false);
                        	 Administrator.swingCJ();
                 
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
            		// ��ʾ��ʣ���λ���
            		JOptionPane.showMessageDialog(null,"�û�������������,����������!" + "��ʣ" + (3 - num) + "��");
            		// �жϵ�¼���˼���
            		if (num != 3) {
            			// ������¼
            			taccount.setText("");
            	        tpassword.setText("");
            	        checkcode.setText("");
            		} else {
            			// ��¼ʧ��
            			JOptionPane.showMessageDialog(null,"�Բ������λ�������,����������!");
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
