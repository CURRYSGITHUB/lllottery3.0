import	javax.swing.*;
import	java.awt.*;
import	java.awt.event.*;

public class Start {
	static JFrame frame;
	JPanel panel;
	JLabel label;
	JButton b1,b2,b3;
	public void cjStart () {
		frame = new JFrame();//创建frame
		//创建组件
		panel = new JPanel();
		label = new JLabel("Java抽奖系统");
		Font bigFont = new Font("serif",Font.BOLD,28);
		label.setFont(bigFont);//设置label字体大小
		b1 = new JButton("用户登录");
		b2 = new JButton("用户注册");
		b3 = new JButton("管理员登录");
		//设置监听,向按钮注册
		b1.addActionListener(new Button1());
		b2.addActionListener(new Button2());
		b3.addActionListener(new Button3());
		//将按钮添加到面板
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));//更换布局管理器
		panel.add(b1);
		panel.add(b2);
		panel.add(b3);
		
		frame.getContentPane().add(BorderLayout.CENTER,panel);//把面板添加到frame的pane上
		frame.getContentPane().add(BorderLayout.NORTH,label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//在window关闭时结束程序
		frame.setSize(300,300);//设定frame大小
		frame.setVisible(true);//显示frame
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
