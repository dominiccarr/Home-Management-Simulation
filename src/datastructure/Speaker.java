/*
 * 
 */
package datastructure;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Speaker.
 */
public class Speaker implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 98234454788359715L;

	/** The name. */
	final private String name;
	
	/** The ip. */
	final private String ip;
	
	/** The port. */
	final private int port;

	/** The location. */
	final private String location;
	/**
	 * Instantiates a new speaker.
	 * 
	 * @param name the name
	 * @param ip the ip
	 * @param port the port
	 * @param location the location
	 */
	public Speaker(String name, String ip, int port, String location) {
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.location = location;
	}

	/**
	 * Ret name.
	 * 
	 * @return the string
	 */
	public String retName() {
		return name;
	}

	/**
	 * Ret ip.
	 * 
	 * @return the string
	 */
	public String retIp() {
		return ip;
	}

	public String retLoc() {
		return location;
	}
	/**
	 * Ret port.
	 * 
	 * @return the int
	 */
	public int retPort() {
		return port;
	}
}
