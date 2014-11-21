package ust.jzhuaq.drumPC;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.omg.CORBA.PUBLIC_MEMBER;

import ust.jzhuaq.drumPC.listener.CursorMovementHandle;
import ust.jzhuaq.drumPC.listener.StateChangeHandle;
import ust.jzhuaq.drumPC.listener.StateChangeListener;
import ust.jzhuaq.drumPC.listener.StateChangeManager;

import javax.swing.JTextField;


public class MainFrame extends JFrame{

	private JFrame frame;
	private JPanel drumsPanel;
	private JPanel controlPanel;
	private Dimension frameSize;
	private int controlHeight = 100; 
	private int actualHeight = 50;

	private String ipLabel = "The IP Address:      ";

	public static String onConnection = "Connection:    ON";
	public static String offConnection = "Connection:    OFF";

	private String cursorMove = "Cursor Movement";
	public static String cursorX = "X:   ";
	public static String cursorY = "Y:   ";
	
	SoundGenerator soundGenerator;
	
	private SoundEvent[] sound = new SoundEvent[7];
	

	public static StateChangeManager stateChangeManager;
	private JTextField textField;
	/**
	 * Create the application.
	 */
	public MainFrame() {
		
		this.soundGenerator = new SoundGenerator();
		this.stateChangeManager = new StateChangeManager();
		
		this.sound[0] = new SoundEvent(SoundEvent.NOTE_DRUM_0);
		this.sound[1] = new SoundEvent(SoundEvent.NOTE_DRUM_1);
		this.sound[2] = new SoundEvent(SoundEvent.NOTE_DRUM_2);
		this.sound[3] = new SoundEvent(SoundEvent.NOTE_DRUM_3);
		this.sound[4] = new SoundEvent(SoundEvent.NOTE_DRUM_4);
		this.sound[5] = new SoundEvent(SoundEvent.NOTE_DRUM_5);
		this.sound[6] = new SoundEvent(SoundEvent.NOTE_DRUM_6);
		
		this.initialize();
	}
	
	
	public void buildUI(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameSize = frame.getSize();
		initializeDrumsPanel();


	}

	private void initializeDrumsPanel() {
		drumsPanel = new JPanel();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0 };
		gridBagLayout.rowWeights = new double[] { 2.0, 1.0, 2.0, Double.MIN_VALUE, Double.MIN_VALUE};
		
		drumsPanel.setLayout(gridBagLayout);
		drumsPanel.setPreferredSize(new Dimension((int) frameSize.getWidth(),
				(int) frameSize.getHeight()));
		frame.getContentPane().add(drumsPanel);

		JButton btnCrybal_0 = new JButton("Cymbal");
		btnCrybal_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[0].play();	
			}
		});
		GridBagConstraints gbc_btnCrybal_0 = new GridBagConstraints();
		gbc_btnCrybal_0.insets = new Insets(0, 0, 5, 5);
		gbc_btnCrybal_0.fill = GridBagConstraints.BOTH;
		gbc_btnCrybal_0.gridx = 0;
		gbc_btnCrybal_0.gridy = 0;
		drumsPanel.add(btnCrybal_0, gbc_btnCrybal_0);

		JButton btnToms_0 = new JButton("Mounted Tom");
		btnToms_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[1].play();
			}
		});
		GridBagConstraints gbc_btnToms_0 = new GridBagConstraints();
		gbc_btnToms_0.insets = new Insets(0, 0, 5, 5);
		gbc_btnToms_0.fill = GridBagConstraints.BOTH;
		gbc_btnToms_0.gridx = 1;
		gbc_btnToms_0.gridy = 0;
		gbc_btnToms_0.gridheight = 2;
		drumsPanel.add(btnToms_0, gbc_btnToms_0);

		JButton btnToms_1 = new JButton("Mounted Tom");
		btnToms_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[2].play();
			}
		});
		GridBagConstraints gbc_btnToms_1 = new GridBagConstraints();
		gbc_btnToms_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnToms_1.fill = GridBagConstraints.BOTH;
		gbc_btnToms_1.gridx = 2;
		gbc_btnToms_1.gridy = 0;
		gbc_btnToms_1.gridheight = 2;
		drumsPanel.add(btnToms_1, gbc_btnToms_1);
		
		
		JButton btnCrybal_1 = new JButton("Cymbal");
		btnCrybal_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[3].play();
			}
		});
		GridBagConstraints gbc_btnCrybal_1 = new GridBagConstraints();
		gbc_btnCrybal_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnCrybal_1.fill = GridBagConstraints.BOTH;
		gbc_btnCrybal_1.gridx = 3;
		gbc_btnCrybal_1.gridy = 0;
		drumsPanel.add(btnCrybal_1, gbc_btnCrybal_1);
		
		
		
		JButton btnSnare = new JButton("Snare drum");
		btnSnare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[4].play();
			}
		});
		GridBagConstraints gbc_btnSnare = new GridBagConstraints();
		gbc_btnSnare.insets = new Insets(0, 0, 5, 5);
		gbc_btnSnare.fill = GridBagConstraints.BOTH;
		gbc_btnSnare.gridx = 0;
		gbc_btnSnare.gridy = 1;
		gbc_btnSnare.gridheight = 2;
		drumsPanel.add(btnSnare, gbc_btnSnare);
		
		JButton btnBass = new JButton("Bass drum");
		btnBass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[5].play();
			}
		});
		GridBagConstraints gbc_btnBass = new GridBagConstraints();
		gbc_btnBass.insets = new Insets(0, 0, 5, 5);
		gbc_btnBass.fill = GridBagConstraints.BOTH;
		gbc_btnBass.gridx = 1;
		gbc_btnBass.gridy = 2;
		gbc_btnBass.gridwidth = 2;
		drumsPanel.add(btnBass, gbc_btnBass);
		
		JButton btnFloor = new JButton("Floor drum");
		btnFloor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[6].play();
			}
		});
		GridBagConstraints gbc_btnFloor = new GridBagConstraints();
		gbc_btnFloor.insets = new Insets(0, 0, 5, 0);
		gbc_btnFloor.fill = GridBagConstraints.BOTH;
		gbc_btnFloor.gridx = 3;
		gbc_btnFloor.gridy = 1;
		gbc_btnFloor.gridheight = 2;
		drumsPanel.add(btnFloor, gbc_btnFloor);
		
		JLabel lblIP = new JLabel();
		lblIP.setText(ipLabel + Server.ipString);
		lblIP.setHorizontalAlignment(JLabel.LEFT);
		GridBagConstraints gbc_lblIP = new GridBagConstraints();
		gbc_lblIP.insets = new Insets(0, 0, 5, 5);
		gbc_lblIP.fill = GridBagConstraints.BOTH;
		gbc_lblIP.gridx = 0;
		gbc_lblIP.gridy = 3;
		gbc_lblIP.gridwidth = 2;
		drumsPanel.add(lblIP, gbc_lblIP);
		
		JLabel lblState = new JLabel();
		stateChangeManager.addListener(new StateChangeHandle(lblState));
		GridBagConstraints gbc_lblState = new GridBagConstraints();
		gbc_lblState.insets = new Insets(0, 0, 5, 5);
		gbc_lblState.gridx = 2;
		gbc_lblState.gridy = 3;
		gbc_lblState.fill = GridBagConstraints.BOTH;
		if (Server.isConnected) {
			lblState.setText(onConnection);
		} else {
			lblState.setText(offConnection);
		}
		drumsPanel.add(lblState, gbc_lblState);
		
		
		JLabel lblCursorMove = new JLabel(cursorMove);
		GridBagConstraints gbc_lblCursorMove= new GridBagConstraints();
		gbc_lblCursorMove.insets = new Insets(0, 0, 5, 0);
		gbc_lblCursorMove.gridx = 0;
		gbc_lblCursorMove.gridy = 4;
		gbc_lblCursorMove.fill = GridBagConstraints.BOTH;
		drumsPanel.add(lblCursorMove, gbc_lblCursorMove);
		
		JLabel lblCursorX = new JLabel(cursorX);
		GridBagConstraints gbc_lblCursorX= new GridBagConstraints();
		gbc_lblCursorX.insets = new Insets(0, 0, 5, 0);
		gbc_lblCursorX.gridx = 1;
		gbc_lblCursorX.gridy = 4;
		gbc_lblCursorX.fill = GridBagConstraints.BOTH;
		drumsPanel.add(lblCursorX, gbc_lblCursorX);
		
		JLabel lblCursorY = new JLabel(cursorY);
		GridBagConstraints gbc_lblCursorY= new GridBagConstraints();
		gbc_lblCursorY.insets = new Insets(0, 0, 5, 0);
		gbc_lblCursorY.gridx = 2;
		gbc_lblCursorY.gridy = 4;
		gbc_lblCursorY.fill = GridBagConstraints.BOTH;
		drumsPanel.add(lblCursorY, gbc_lblCursorY);
		
		stateChangeManager.addListener(new CursorMovementHandle(lblCursorX, lblCursorY));
		
		
		/*JButton btn0 = new JButton("0");
		btn0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[0].play();
				//soundGenerator.play(60);
				
			}
		});
		drumsPanel.add(btn0);

		JButton btn1 = new JButton("1");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[1].play();
				//soundGenerator.play(50);
			}
		});
		drumsPanel.add(btn1);

		JButton btn2 = new JButton("2");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[2].play();
				//soundGenerator.play(45);
			}
		});
		drumsPanel.add(btn2);

		JButton btn3 = new JButton("3");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[3].play();
				//soundGenerator.play(65);
			}
		});

		drumsPanel.add(btn3);

		JButton btn4 = new JButton("4");
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[4].play();
				//soundGenerator.play(55);
			}
		});
		drumsPanel.add(btn4);

		JButton btn5 = new JButton("5");
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sound[5].play();
				//soundGenerator.play(70);
			}
		});
		drumsPanel.add(btn5);*/

		
	}

	private void initializeControlPanel() {
		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(0, 3, 0, 0));

		controlPanel.setPreferredSize(new Dimension((int) frameSize.getWidth(),
				actualHeight));

		frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);
		
		

		JLabel lblIP = new JLabel();
		lblIP.setText(ipLabel + Server.ipString);
		controlPanel.add(lblIP);
		
		controlPanel.add(new JLabel());		//empty
		
		
		
		JLabel lblState = new JLabel();
		stateChangeManager.addListener(new StateChangeHandle(lblState));
		if (Server.isConnected) {
			lblState.setText(onConnection);
		} else {
			lblState.setText(offConnection);
		}
		controlPanel.add(lblState);
		
		
		
		JLabel lblNewLabel = new JLabel(cursorMove);
		controlPanel.add(lblNewLabel);
		
		JLabel lblCursorX = new JLabel(cursorX);
		controlPanel.add(lblCursorX);
		
		JLabel lblCursorY = new JLabel(cursorY);
		controlPanel.add(lblCursorY);
		
		stateChangeManager.addListener(new CursorMovementHandle(lblCursorX, lblCursorY));
		
		
	}

	public void refresh(){
		this.revalidate();
		this.repaint();
	}
}
