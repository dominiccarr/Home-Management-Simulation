/*
 * 
 */
package datastructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * The Class ContactsRepository.
 */
public class ContactsRepository implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6433455379958872372L;
	private final File contactFile;
	private final HashMap<String, Contact> contacts = new HashMap<String, Contact>();

	/**
	 * Instantiates a new contacts repository.
	 * 
	 * @param file
	 *            the file
	 */
	public ContactsRepository(String file) {
		contactFile = new File(file);
		readIn();
	}

	/**
	 * reads in from a file.
	 */
	private void readIn() {
		try {
			BufferedReader fileread = new BufferedReader(new FileReader(
					contactFile));
			String l = null;
			while ((l = fileread.readLine()) != null) {
				String[] s = l.split("-");
				String name = s[0];
				String uniqueid = s[1];
				Contact a = new Contact(name, Integer.valueOf(uniqueid), 10);
				contacts.put(a.getName(), a);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * returns the contacts.
	 * 
	 * @return the contacts
	 */
	public HashMap<String, Contact> getContacts() {
		return contacts;
	}
}
