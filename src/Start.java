import	javax.swing.*;
import	java.awt.*;
import	java.awt.event.*;

public class Start {
	static JFrame frame;
	JLabel label;
	JButton b1,b2,b3;
	public void cjStart () {
		frame = new JFrame();//创建frame
		//创建组件
		label = new JLabel("Java 抽 奖 系 统");
		Font font1 = new Font("微软雅黑",Font.BOLD,44);
		label.setFont(font1);//设置label字体大小
		label.setBounds(300,80,350,100);
		b1 = new JButton("用 户 登 录");
		b2 = new JButton("用 户 注 册");
		b3 = new JButton("管 理 员 登 录");
		//设置监听,向按钮注册
		b1.addActionListener(new Button1());
		b2.addActionListener(new Button2());
		b3.addActionListener(new Button3());
		
		Font font2 = new Font("serif",Font.BOLD,24);
		b1.setFont(font2);
		b2.setFont(font2);
		b3.setFont(font2);
		b1.setBounds(350, 240, 197, 52);
		b2.setBounds(350, 331, 197, 52);
        b3.setBounds(350, 422, 197, 52);

		
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);//将布局管理器置为空
        frame.setVisible(true);
        
        frame.getContentPane().add(label);
        frame.getContentPane().add(b1);
        frame.getContentPane().add(b2);
        frame.getContentPane().add(b3);
	}
	//内部类
	class Button1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			Login.userLogin();
		}
	}
	class Button2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			Register.userRegister();
		}
	}
	class Button3 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			AdmiLogin.admiLogin();
		}
	}
}
