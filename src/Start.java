import	javax.swing.*;
import	java.awt.*;
import	java.awt.event.*;

public class Start {
	static JFrame frame;
	JPanel panel;
	JLabel label;
	JButton b1,b2,b3;
	public void cjStart () {
		frame = new JFrame();//����frame
		//�������
		panel = new JPanel();
		label = new JLabel("Java�齱ϵͳ");
		Font bigFont = new Font("serif",Font.BOLD,28);
		label.setFont(bigFont);//����label�����С
		b1 = new JButton("�û���¼");
		b2 = new JButton("�û�ע��");
		b3 = new JButton("����Ա��¼");
		//���ü���,��ťע��
		b1.addActionListener(new Button1());
		b2.addActionListener(new Button2());
		b3.addActionListener(new Button3());
		//����ť��ӵ����
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));//�������ֹ�����
		panel.add(b1);
		panel.add(b2);
		panel.add(b3);
		
		frame.getContentPane().add(BorderLayout.CENTER,panel);//�������ӵ�frame��pane��
		frame.getContentPane().add(BorderLayout.NORTH,label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//��window�ر�ʱ��������
		frame.setSize(300,300);//�趨frame��С
		frame.setVisible(true);//��ʾframe
	}
	//�ڲ���
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
