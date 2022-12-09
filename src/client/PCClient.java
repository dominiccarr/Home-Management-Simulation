/*
 * 
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import clientui.PCUI;
import datastructure.Contact;
import datastructure.ContactsRepository;

/**
 * Home PC Client.
 * 
 * @author dominic
 */
public class PCClient extends Client {

	private static final String SYNC = "contacts";
	private final ContactsRepository contacts;
	boolean loggedin = false;

	/**
	 * Constructor for the PCClient.
	 */
	public PCClient() {
		super();
		contacts = new ContactsRepository("src/resources/clientContacts.txt");
		serviceType = "homePC._udp.local.";
		ui = new PCUI(this);
		name = "PC Sync";
	}

	public void login(String user, String pass) {
		String a = sendMessage("login," + user + "," + pass);
		loggedin = Boolean.parseBoolean(a);
		if (loggedin) {
			((PCUI) ui).loggedin();
		} else {
			ui.updateArea("Login failed");
		}
	}

	/**
	 * Sends a message to a computer to sync contacts.
	 */
	public void syncContacts() {
		ObjectInputStream objectIn;
		try {
			toServer = new Socket(serverHost, serverPort);
			PrintWriter out = new PrintWriter(toServer.getOutputStream(), true);
			out.println(SYNC);
			objectIn = new ObjectInputStream(toServer.getInputStream());
			HashMap<String, Contact> pcContacts = (HashMap<String, Contact>) objectIn
					.readObject();
			sync(pcContacts);
			out.close();
			toServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * syncs the 2 vectors of contacts.
	 * 
	 * @param musicList
	 *            the music list
	 */
	private void sync(HashMap<String, Contact> musicList) {
		HashMap<String, Contact> mycontacts = contacts.getContacts();
		HashMap<String, Contact> pccontacts = musicList;

		for (String a : pccontacts.keySet()) {
			if (!mycontacts.containsKey(a)) {
				ui.updateArea("Added contact from PC " + a);
				mycontacts.put(a, pccontacts.get(a));
			}
		}
	}

	@Override
	public void disable() {
		super.disable();
		ui = new PCUI(this);
	}
}