package ust.jzhuaq.drumPC;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;

import ust.jzhuaq.drumPC.Util.Constants;
import ust.jzhuaq.drumPC.listener.CursorMovementHandle;
import ust.jzhuaq.drumPC.listener.StateChangeHandle;
import ust.jzhuaq.drumPC.listener.StateChangeManager;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel drumsPanel;
	private Dimension frameSize;
	private String ipLabel = "The IP Address:      ";

	public static String onConnection = "Connection:    ON";
	public static String offConnection = "Connection:    OFF";

	private String cursorMove = "Cursor Movement";
	public static String cursorX = "X:   ";
	public static String cursorY = "Y:   ";

	SoundGenerator soundGenerator;

	private SoundEvent[] sound = new SoundEvent[7];

	public static StateChangeManager stateChangeManager;
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

	public void buildUI() {
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
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0 };
		gridBagLayout.rowWeights = new double[] { 2.0, 1.0, 2.0,
				Double.MIN_VALUE, Double.MIN_VALUE };

		drumsPanel.setLayout(gridBagLayout);
		drumsPanel.setPreferredSize(new Dimension((int) frameSize.getWidth(),
				(int) frameSize.getHeight()));
		frame.getContentPane().add(drumsPanel);

		JButton btnCrybal_0 = new JButton(new ImageIcon(getClass().getClassLoader().getResource(Constants.PATH_DRUM_0)));
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

		JButton btnToms_0 = new JButton(new ImageIcon(getClass().getClassLoader().getResource(Constants.PATH_DRUM_1)));
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

		JButton btnToms_1 = new JButton(new ImageIcon(getClass().getClassLoader().getResource(Constants.PATH_DRUM_2)));
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

		JButton btnCrybal_1 = new JButton(new ImageIcon(getClass().getClassLoader().getResource(Constants.PATH_DRUM_3)));
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

		JButton btnSnare = new JButton(new ImageIcon(getClass().getClassLoader().getResource(Constants.PATH_DRUM_4)));
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

		JButton btnBass = new JButton(new ImageIcon(getClass().getClassLoader().getResource(Constants.PATH_DRUM_5)));

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

		JButton btnFloor = new JButton(new ImageIcon(getClass().getClassLoader().getResource(Constants.PATH_DRUM_6)));
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
		lblIP.setText(ipLabel + Main.ipString);
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
		if (Main.isConnected) {
			lblState.setText(onConnection);
		} else {
			lblState.setText(offConnection);
		}
		drumsPanel.add(lblState, gbc_lblState);

		JLabel lblCursorMove = new JLabel(cursorMove);
		GridBagConstraints gbc_lblCursorMove = new GridBagConstraints();
		gbc_lblCursorMove.insets = new Insets(0, 0, 5, 0);
		gbc_lblCursorMove.gridx = 0;
		gbc_lblCursorMove.gridy = 4;
		gbc_lblCursorMove.fill = GridBagConstraints.BOTH;
		drumsPanel.add(lblCursorMove, gbc_lblCursorMove);

		JLabel lblCursorX = new JLabel(cursorX);
		GridBagConstraints gbc_lblCursorX = new GridBagConstraints();
		gbc_lblCursorX.insets = new Insets(0, 0, 5, 0);
		gbc_lblCursorX.gridx = 1;
		gbc_lblCursorX.gridy = 4;
		gbc_lblCursorX.fill = GridBagConstraints.BOTH;
		drumsPanel.add(lblCursorX, gbc_lblCursorX);

		JLabel lblCursorY = new JLabel(cursorY);
		GridBagConstraints gbc_lblCursorY = new GridBagConstraints();
		gbc_lblCursorY.insets = new Insets(0, 0, 5, 0);
		gbc_lblCursorY.gridx = 2;
		gbc_lblCursorY.gridy = 4;
		gbc_lblCursorY.fill = GridBagConstraints.BOTH;
		drumsPanel.add(lblCursorY, gbc_lblCursorY);

		stateChangeManager.addListener(new CursorMovementHandle(lblCursorX,
				lblCursorY));
	}

	public void refresh() {
		this.revalidate();
		this.repaint();
	}
}
