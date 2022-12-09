/*
 * 
 */
package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import serviceui.ServiceUI;

/**
 * The Class SecurityService.
 */
public class SecurityService extends Service implements ServiceListener {

	private final Vector<ServiceInfo> doors;
	private final Vector<ServiceInfo> windows;

	/**
	 * Instantiates a new security service.
	 * 
	 * @param name
	 *            the name
	 * @param priority
	 *            the priority
	 */
	public SecurityService(String name, String priority) {
		super(name, "security._udp.local.", "Priority=" + priority);
		doors = new Vector<ServiceInfo>();
		windows = new Vector<ServiceInfo>();
		ui = new ServiceUI(this, name);
		jmdns.addServiceListener("door._udp.local.", this);
		jmdns.addServiceListener("window._udp.local.", this);

	}

	@Override
	public void performAction(String a) {
		if (a.equals("get_status")) {
			sendBack(getStatus());
		} else if (a.equals("close all")) {
			sendBack("OK");
			for (ServiceInfo s : windows) {
				new SendMessage(s.getHostAddress(), s.getPort(), "close");
			}
			ui.updateArea("Closing all windows....\n All Windows are closed");
		} else if (a.equals("lock all")) {
			sendBack("OK");
			for (ServiceInfo s : doors) {
				new SendMessage(s.getHostAddress(), s.getPort(), "lock");
			}
			ui.updateArea("Locking all doors....\n All Doors are locked");
		} else if (a.equals("unlock all")) {
			for (ServiceInfo s : doors) {
				new SendMessage(s.getHostAddress(), s.getPort(), "unlock");
			}
			ui.updateArea("Locking all doors....\n All Doors are unlocked");
		} else if (a.equals("close all")) {
			sendBack("OK");
			for (ServiceInfo s : windows) {
				new SendMessage(s.getHostAddress(), s.getPort(), "open");
			}
			ui.updateArea("Closing all windows....\n All Windows are opened");
		} else if (a.equals("check")) {
			String returnString = "OK";
			for (ServiceInfo s : windows) {
				returnString += ","
						+ sendMessage(s.getHostAddress(), s.getPort(), "status");
			}
			for (ServiceInfo s : doors) {
				returnString += ","
						+ sendMessage(s.getHostAddress(), s.getPort(), "status");
			}
			sendBack(returnString);
			ui.updateArea("Sending status to user.");
		} else {
			sendBack(BAD_COMMAND);
		}
	}

	public String sendMessage(String ip, int host, String a_message) {
		String msg = "";
		try {
			Socket toServer = new Socket(ip, host);
			PrintWriter out = new PrintWriter(toServer.getOutputStream(), true);
			out.println(a_message);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					toServer.getInputStream()));
			msg = in.readLine();
			out.close();
			toServer.close();
		} catch (Exception ioe) {
			ui.updateArea("Door/Window offline");
		}
		return msg;
	}

	@Override
	public void serviceAdded(ServiceEvent arg0) {
		arg0.getDNS().requestServiceInfo(arg0.getType(), arg0.getName(), 0);
	}

	@Override
	public void serviceRemoved(ServiceEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void serviceResolved(ServiceEvent arg0) {
		if (arg0.getInfo().getType().equals("door._udp.local.")) {
			doors.add(arg0.getInfo());
		} else {
			windows.add(arg0.getInfo());
		}
	}

	@Override
	public String getStatus() {
		return "Security System Active";
	}

	public static void main(String[] args) {
		// new DoorService("1");
		// new DoorService("2");
		// new DoorService("3");
		// new DoorService("4");
		// new DoorService("5");
		// new DoorService("6");
		// new DoorService("7");
		//new DoorService("8");

		// new WindowService("w1");
		// new WindowService("w2");
		// new WindowService("w3");
		// new WindowService("w4");
		//new WindowService("w5");
		//new WindowService("w6");
		new SecurityService("house-security", "1");
	}
}