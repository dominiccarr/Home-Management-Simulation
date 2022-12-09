/*
 * 
 */
package services;

import java.io.IOException;
import java.io.ObjectOutputStream;

import serviceui.ServiceUI;
import datastructure.ContactsRepository;

/**
 * The Class PCService.
 */
public class PCService extends Service {

	private final ContactsRepository contacts;
	private final String user = "Dominic";
	private final String pass = "DS";

	/**
	 * Instantiates a new pC service.
	 * 
	 * @param name
	 *            the name
	 */
	public PCService(String name) {
		super(name, "homePC._udp.local.");
		contacts = new ContactsRepository("src/resources/PCcontacts.txt");
		ui = new ServiceUI(this, name);
	}

	@Override
	public void performAction(String a) {
		if (a.equals("get_status")) {
			sendBack(getStatus());
		} else if (a.equals("contacts")) {
			sync();
		} else if (a.startsWith("login,")) {
			String[] split = a.split(",");
			String u = split[1];
			String p = split[2];
			if (user.equals(u) && pass.equals(p)) {
				sendBack("true");
			} else {
				sendBack("false");
			}
		} else {
			sendBack(BAD_COMMAND);
		}
	}

	/**
	 * Sync.
	 */
	private void sync() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(contacts.getContacts());
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getStatus() {
		return "PC Online";
	}

	public static void main(String[] args) {
		new PCService("Eoin's PC");
	}
}
