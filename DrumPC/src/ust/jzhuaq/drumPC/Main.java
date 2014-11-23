package ust.jzhuaq.drumPC;

import java.awt.AWTException;
import java.net.SocketException;
import java.util.Date;
import java.util.List;

import ust.jzhuaq.drumPC.Util.Constants;
import ust.jzhuaq.drumPC.Util.GetIpAddress;
import ust.jzhuaq.drumPC.Util.MouseMovement;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;

public class Main {
	public static MouseMovement mouse;
	public static OSCPortIn receiver = null;
	public static String ipString;
	public static boolean isConnected = false;
	private static MainFrame frame;

	public static void main(String[] args) {
		try {
			mouse = new MouseMovement();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}

		Thread thread = new Thread() {

			@Override
			public void run() {
				GetIpAddress getAddress = new GetIpAddress();
				getAddress.run();
				ipString = getAddress.ipString;
				initFrame();
				System.out.println("Server is running.\nThe IP address is "
						+ getAddress.ipString);
				try {
					receiver = new OSCPortIn(Constants.port);

					OSCListener connListener = new OSCListener() {

						@Override
						public void acceptMessage(Date arg0, OSCMessage arg1) {
							isConnected = true;
							MainFrame.stateChangeManager.isConnect();
						}
					};

					receiver.addListener(Constants.ADDRESS_SELECTOR_CONNECT,
							connListener);

					OSCListener disConnListener = new OSCListener() {

						@Override
						public void acceptMessage(Date arg0, OSCMessage arg1) {
							isConnected = false;
							MainFrame.stateChangeManager.unConnect();
						}
					};

					receiver.addListener(Constants.ADDRESS_SELECTOR_DISCONNECT,
							disConnListener);

					OSCListener listener = new OSCListener() {

						@Override
						public void acceptMessage(Date arg0, OSCMessage arg1) {
							if ((arg1 != null)&&isConnected) {

								List<Object> args = arg1.getArguments();
								if ((args != null) && (args.size() != 0)) {
									mouse.getCommands(args);
								}
							}

						}
					};

					receiver.addListener(Constants.ADDRESS_SELECTOR_MSG,
							listener);

					receiver.startListening();
					if (receiver.isListening())
						receiver.run();
				} catch (SocketException e) {
					e.printStackTrace();
					System.out.println("error" + e);
				} catch (IllegalArgumentException e) {
					System.out.println("address error" + e);
				}
			}
		};

		thread.start();

	}

	public static void initFrame() {
		frame = new MainFrame();
		frame.buildUI();
	}

	public static void refreshFrame() {
		frame.revalidate();
		frame.repaint();
		frame.revalidate();
	}
}
