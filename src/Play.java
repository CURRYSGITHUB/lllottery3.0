import   java.util.*;
import java.sql.*;


public class Play {
	 // JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8";//urlָ��Ҫ���ʵ����ݿ���
 
    // MySQL����ʱ���û���������
    static final String USER = "root";
    static final String PASS = "123456";
    
	static int date1 = 0;
	// �������뿨�ŵĴ���
    static int cardNumber = 0;
    //��������Ա����
    static Administrator admin = new Administrator();
    //��ȡ�н�����
    static double cjRate = admin.rate;
    // �齱����
    public static void userCJ() {
    	Connection conn = null;//����connection����
        Statement stmt = null;//����statement���������ִ��sql���
    	// �жϵ�¼״̬
        if (!User.isLogin) {
            // û��¼ ֱ�ӽ�������
            System.out.println("���ȵ�¼");
            return;
        }
        // �ж��Ƿ�������ȷ
        if(!isCarNum()) {
            System.out.println("������Ĳ���ȷ");
            return;
        }

        // ������������ƴ�ӳ��ַ�����ӡ���Ÿ���
        String string = "������������:";
        for (int i = 0; i < 5; i++) {
            // ���
        	cjRate = 0.5;
        	double x = Math.pow((1-cjRate), 1/5);
        	System.out.print(x);
        	double y = 1/(1-x);
        	//System.out.print(y);
            int num = (int)(Math.random() * y + User.number);
            // ƴ��
            if (i < 4) {
                string = string + num + ",";
            } else {
                string = string + num;
            }
            // �鿴�Ƿ��н�
            if (num == User.number) {
                User.isWin = true;
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
                       		 &&rs.getString("password").contentEquals(User.password)) {
                       	 sql="update lottery set isWin=1";//�޸ĵ�¼״̬
                       	 stmt.executeUpdate(sql);//ִ��SQL���
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
        // ��ӡ�н���
        System.out.println(string);
        // �ж��Ƿ��н�
        if(User.isWin) {
            System.out.println("��ϲ�н�");
        } else {
            System.out.println("���ź�����δ�н�");
        }
    }

    // ���뿨�ŷ���
    public static boolean isCarNum() {
    	
    	//��ȡ��ǰϵͳ����
        Calendar c = Calendar.getInstance();
        int date2 = c.get(Calendar.DATE);
        if(date1!=date2) {
        	System.out.println("�����뿨��:");
        	@SuppressWarnings("resource")
        	Scanner scanner = new Scanner(System.in);
        	// ����
        	String carNum = scanner.nextLine();
        	// תint����
        	int num = Integer.parseInt(carNum);
        	// ���бȶ�
        	if (User.number == num) {
        		// ƥ����ȷ
        		System.out.println("����������ȷ");
        		return true;
        	} else {
        		//ƥ�䲻��ȷ
        		cardNumber++;
        		System.out.println("���뻹ʣ" + (3 - cardNumber) + "��");
        		if (cardNumber != 3) {
        			isCarNum();
        		} else {
        			System.out.println("3�λ�������");
        			// ���뿨�Ŵ�������
        			cardNumber = 0;
        			date1 = date2;//������Ĵ�����
        		}
        		return false;
        	}
        }
        return false;
    }
}
	