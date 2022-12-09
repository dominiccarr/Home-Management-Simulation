/*
 * 
 */
package datastructure;

import java.io.Serializable;

/**
 * The Class Contact.
 */
public class Contact implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5914010530825106851L;
	private final String name;
	private final int LastUpdated;
	private final int uniqueID;

	/**
	 * Instantiates a new contact.
	 * 
	 * @param n
	 *            the n
	 * @param l
	 *            the l
	 * @param u
	 *            the u
	 */
	public Contact(String n, int l, int u) {
		name = n;
		LastUpdated = l;
		uniqueID = u;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + " " + uniqueID;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * gets the lastupdated.
	 * 
	 * @return the last updated
	 */
	public int getLastUpdated() {
		return LastUpdated;
	}

	/**
	 * gets the uniqueid.
	 * 
	 * @return the unique id
	 */
	public int getUniqueID() {
		return uniqueID;
	}

}
