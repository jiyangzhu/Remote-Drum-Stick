package ust.jzhuaq.drumPC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NoFrame {
	JFrame jFrame;
	JPanel jPanel;
	JButton button_0;
	JButton button_1;
	JButton button_2;
	JButton button_3;
	SoundGenerator soundGenerator;
	public NoFrame() {
		this.jFrame = new JFrame("Main Frame");
		this.jPanel = new JPanel();
		this.button_0 = new JButton("0");
		this.button_1 = new JButton("1");
		this.button_2 = new JButton("2");
		this.button_3 = new JButton("3");
		this.soundGenerator = new SoundGenerator();
		initButtons();
	}
	private void initButtons(){
		button_0.addActionListener(new buttonListener_0());
		button_1.addActionListener(new buttonListener_1());
		button_2.addActionListener(new buttonListener_2());
		button_3.addActionListener(new buttonListener_3());
	}
	public void buildUI(){

		jPanel.add(button_0);
		jPanel.add(button_1);
		jPanel.add(button_2);
		jPanel.add(button_3);
		
		jFrame.getContentPane().add(jPanel);
		jFrame.setSize(1000, 1000);
		jFrame.setVisible(true);
	}
	
	private class buttonListener_0 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			soundGenerator.play(50);
			System.out.println("click on button_0");
		}
		
	}
	
	private class buttonListener_1 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			soundGenerator.play(60);
			System.out.println("click on button_1");
		}
		
	}
	private class buttonListener_2 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			soundGenerator.play(45);
			System.out.println("click on button_2");
		}
		
	}
	private class buttonListener_3 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			soundGenerator.play(65);
			System.out.println("click on button_3");
		}
		
	}
}
