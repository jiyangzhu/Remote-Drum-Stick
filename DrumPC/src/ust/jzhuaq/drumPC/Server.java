package ust.jzhuaq.drumPC;

import java.awt.AWTException;
import java.net.SocketException;
import java.util.Date;
import java.util.List;

import ust.jzhuaq.drumPC.Util.Mouse;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;

public class Server {
	public static Mouse mouse;
	public static OSCPortIn receiver = null;
	public static String ipString;
	public static boolean isConnected = false;
	private static MainFrame frame;

	
	public static void main(String[] args) {
		try {
			mouse = new Mouse();
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
					receiver = new OSCPortIn(Config.port);

					OSCListener listener = new OSCListener() {

						@Override
						public void acceptMessage(Date arg0, OSCMessage arg1) {
							if (arg1 != null) {
								isConnected = true;
								ipString = "0";
								MainFrame.stateChangeManager.isConnect();
								List<Object> args = arg1.getArguments();
								if ((args!= null) && (args.size() != 0)) {
									mouse.getCommands(args);
								}
							}

						}
					};

					receiver.addListener("/msg", listener);
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
	
	public static void refreshFrame(){
		frame.revalidate();
		frame.repaint();
		frame.revalidate();
	}
}
