package com.example.OSCServer;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.example.OSCServer.Util.Constants;
import com.example.OSCServer.Util.Mouse;
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		 * MainFrame mainFrame = new MainFrame(); mainFrame.buildUI();
		 */

		Thread thread = new Thread() {

			@Override
			public void run() {
				GetIpAddress getAddress = new GetIpAddress();
				getAddress.run();
				ipString = getAddress.ipString;
				initFrame();
				// isConnected = true;
				System.out.println("Server is running.\nThe IP address is "
						+ getAddress.ipString);
				try {
					receiver = new OSCPortIn(Config.port);
					System.out.println("Server is running.");

					OSCListener listener = new OSCListener() {

						@Override
						public void acceptMessage(Date arg0, OSCMessage arg1) {
							// TODO Auto-generated method stub
							if (arg1 != null) {
								System.out.println("Connected");
								isConnected = true;
								ipString = "0";
								MainFrame.stateChangeManager.isConnect();
								List<Object> args = arg1.getArguments();
								if (args.size() != 0) {
									mouse.getCommands(args);
								}
							}

						}
					};

					receiver.addListener("/msg", listener);
					System.out.println("Server is running.2");
					receiver.startListening();
					if (receiver.isListening())
						System.out.println("Server is running.3");
					receiver.run();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("error" + e);
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
