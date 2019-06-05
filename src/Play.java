import   java.util.*;
import java.sql.*;


public class Play {
	 // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8";//url指向要访问的数据库名
 
    // MySQL配置时的用户名与密码
    static final String USER = "root";
    static final String PASS = "123456";
    
	static int date1 = 0;
	// 保存输入卡号的次数
    static int cardNumber = 0;
    //创建管理员对象
    static Administrator admin = new Administrator();
    //获取中奖概率
    static double cjRate = admin.rate;
    // 抽奖方法
    public static void userCJ() {
    	Connection conn = null;//声明connection对象
        Statement stmt = null;//创建statement类对象，用来执行sql语句
    	// 判断登录状态
        if (!User.isLogin) {
            // 没登录 直接结束方法
            System.out.println("请先登录");
            return;
        }
        // 判断是否输入正确
        if(!isCarNum()) {
            System.out.println("你输入的不正确");
            return;
        }

        // 随机五个数并且拼接成字符串打印逗号隔开
        String string = "本日幸运数字:";
        for (int i = 0; i < 5; i++) {
            // 随机
        	cjRate = 0.5;
        	double x = Math.pow((1-cjRate), 1/5);
        	System.out.print(x);
        	double y = 1/(1-x);
        	//System.out.print(y);
            int num = (int)(Math.random() * y + User.number);
            // 拼接
            if (i < 4) {
                string = string + num + ",";
            } else {
                string = string + num;
            }
            // 查看是否中奖
            if (num == User.number) {
                User.isWin = true;
                try{
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.cj.jdbc.Driver");
                
                    // 打开链接
                    conn = DriverManager.getConnection(DB_URL,USER,PASS);//getConnection方法，连接MySQL数据库
                
                    // 执行查询
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT account, password, number, isLogin, isWin FROM lottery";
                    ResultSet rs = stmt.executeQuery(sql);
                    
                    while(rs.next()){
                   	
                        if(rs.getString("account").contentEquals(User.account)
                       		 &&rs.getString("password").contentEquals(User.password)) {
                       	 sql="update lottery set isWin=1";//修改登录状态
                       	 stmt.executeUpdate(sql);//执行SQL语句
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
            }
        }
        // 打印中奖号
        System.out.println(string);
        // 判断是否中奖
        if(User.isWin) {
            System.out.println("恭喜中奖");
        } else {
            System.out.println("很遗憾，您未中奖");
        }
    }

    // 输入卡号方法
    public static boolean isCarNum() {
    	
    	//获取当前系统日期
        Calendar c = Calendar.getInstance();
        int date2 = c.get(Calendar.DATE);
        if(date1!=date2) {
        	System.out.println("请输入卡号:");
        	@SuppressWarnings("resource")
        	Scanner scanner = new Scanner(System.in);
        	// 接收
        	String carNum = scanner.nextLine();
        	// 转int类型
        	int num = Integer.parseInt(carNum);
        	// 进行比对
        	if (User.number == num) {
        		// 匹配正确
        		System.out.println("卡号输入正确");
        		return true;
        	} else {
        		//匹配不正确
        		cardNumber++;
        		System.out.println("输入还剩" + (3 - cardNumber) + "次");
        		if (cardNumber != 3) {
        			isCarNum();
        		} else {
        			System.out.println("3次机会用完");
        			// 输入卡号次数重置
        			cardNumber = 0;
        			date1 = date2;//避免第四次输入
        		}
        		return false;
        	}
        }
        return false;
    }
}
	