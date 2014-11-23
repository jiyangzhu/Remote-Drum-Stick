package ust.jzhuaq.drumPC.Util;

import java.net.InetAddress;
import java.util.ArrayList;
/**
 * Get the IP address of the host PC
 * @author 	Bryce
 * @since	02-Oct-2014
 */
public class GetIpAddress {
	//The list of IP
	public ArrayList<String> iPList = new ArrayList<String>();
	//Single IP
	public String ipString;
	
	public void run(){
		String hostName;
		try {
			ipString = InetAddress.getLocalHost().getHostAddress();
			hostName = InetAddress.getLocalHost().getHostName();
			InetAddress inetAddress[] = InetAddress.getAllByName(hostName);
			for (InetAddress addr : inetAddress) {
				String ip = addr.getHostAddress().toString();
				iPList.add(ip);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
}
