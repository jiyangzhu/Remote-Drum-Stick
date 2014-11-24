package ust.jzhuaq.drumAndroid.util;

public class Constants {

	public final static String PREFS_NAME = "MyPrefsFile";
	public final static String PREFS_KEY_IP = "ip";
	public final static String PREFS_KEY_SENSITIVITY = "sen";
	
	public final static int SENSITIVY_DEFAULT = 1;
	
	public final static int EVENT_CLICK = 0;
	public final static int EVENT_CURSOR = 1;
	public final static int EVENT_DISCONNECT = 2;
	public final static int EVENT_KEYBOARD = 3;
	
	public final static int PORT = 7890;	//the port of the socket
	
	public static final int KEY_UP = 1;
	public static final int KEY_DOWN = 2;
	public static final int KEY_LEFT = 3;
	public static final int KEY_RIGHT = 4;
	public static final int KEY_PGUP = 5;
	public static final int KEY_PGDN = 6;
	public static final int KEY_ESC = 7;
	public static final int KEY_TASKSWITCH = 8;
	
	
	public static final String ADDRESS_SELECTOR_MSG = "/msg";
	public static final String ADDRESS_SELECTOR_CONNECT = "/connect";
	public static final String ADDRESS_SELECTOR_DISCONNECT = "/disconnect";
	public static final String ADDRESS_SELECTOR_KEYBOARD = "/key";
}
