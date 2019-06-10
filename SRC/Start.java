import	javax.swing.*;
import	java.awt.*;
import	java.awt.event.*;

public class Start {
	static JFrame frame;
	static ImageIcon bgImage = new ImageIcon("C:/Users/Damien/Desktop/bg.jpg");
	static JLabel label;
	static JLabel bgLabel = new JLabel(bgImage);
	static JButton b1,b2,b3;
	public static void cjStart () {
		frame = new JFrame();//创建frame
		//创建组件
		label = new JLabel("Java 抽 奖 系 统");
		Font font1 = new Font("微软雅黑",Font.BOLD,44);
		label.setFont(font1);//设置label字体大小
		label.setBounds(300,90,350,100);
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
		b1.setBounds(360, 270, 197, 52);
		b2.setBounds(360, 360, 197, 52);
        b3.setBounds(360, 450, 197, 52);

		frame.setSize(950, 750);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);//将布局管理器置为空
        frame.setVisible(true);
        
        bgLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		JPanel imagePanel = (JPanel) frame.getContentPane();
		imagePanel.setOpaque(false);
		// 把背景图片添加到分层窗格的最底层作为背景
		frame.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
		frame.setResizable(false);
		
        frame.getContentPane().add(label);
        frame.getContentPane().add(b1);
        frame.getContentPane().add(b2);
        frame.getContentPane().add(b3);
	}
	//内部类
	static class Button1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			Login.userLogin();
		}
	}
	static class Button2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			Register.userRegister();
		}
	}
	static class Button3 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			AdmiLogin.admiLogin();
		}
	}
}
