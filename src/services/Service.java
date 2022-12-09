/*
 * 
 */
package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import serviceui.ServiceUI;

/**
 * The Class Service.
 */
public abstract class Service extends Thread {

	/** The SERVIC e_ type. */
	protected String SERVICE_TYPE;

	/** The SERVIC e_ name. */
	protected String SERVICE_NAME;

	/** The SERVIC e_ port. */
	protected int SERVICE_PORT;

	/** The my_backlog. */
	protected int my_backlog = 5;

	/** The my_server socket. */
	protected ServerSocket my_serverSocket;

	/** The jmdns. */
	protected JmDNS jmdns;

	/** The socket. */
	protected Socket socket;

	/** The status. */
	protected String status;

	/** The ui. */
	protected ServiceUI ui;

	/** The info. */
	protected ServiceInfo info;

	/** The BA d_ command. */
	protected final String BAD_COMMAND = "bad Command";

	/** The STATU s_ request. */
	protected String STATUS_REQUEST = "get_status";

	/** The out. */
	protected PrintWriter out;

	/** The in. */
	protected BufferedReader in;

	/**
	 * Instantiates a new service.
	 * 
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 */
	public Service(String name, String type) {
		this(name, type, "");
	}

	/**
	 * Instantiates a new service.
	 * 
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 * @param location
	 *            the location
	 */
	public Service(String name, String type, String location) {
		SERVICE_NAME = name;
		try {
			SERVICE_PORT = findFreePort();
		} catch (IOException e2) {

			e2.printStackTrace();
		}
		SERVICE_TYPE = type;
		try {
			my_serverSocket = new ServerSocket(SERVICE_PORT, my_backlog);
		} catch (IOException e) {
			try {
				SERVICE_PORT = findFreePort();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		try {
			jmdns = JmDNS.create();
			info = ServiceInfo.create(SERVICE_TYPE, SERVICE_NAME, SERVICE_PORT,
					"params=" + location);
			jmdns.registerService(info);
		} catch (IOException e) {
			e.printStackTrace();
		}
		start();
	}

	/**
	 * De register.
	 */
	public void deRegister() {
		jmdns.unregisterService(info);
		try {
			this.stop();
			my_serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method that listens on the server socket forever and prints each incoming
	 * message to the console.
	 */
	@Override
	public void run() {
		while (true) {
			try {
				socket = my_serverSocket.accept();

				in = new BufferedReader(new InputStreamReader(socket
						.getInputStream()));

				String msg = in.readLine();
				performAction(msg);
				in.close();
				socket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (SecurityException se) {
				se.printStackTrace();
			} finally {
			}
		}
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return SERVICE_TYPE;
	}

	/**
	 * Gets the service name.
	 * 
	 * @return the service name
	 */
	public String getServiceName() {
		return SERVICE_NAME;
	}

	/**
	 * Gets the port.
	 * 
	 * @return the port
	 */
	public int getPort() {
		return SERVICE_PORT;
	}

	/**
	 * Send back.
	 * 
	 * @param a
	 *            the a
	 */
	public void sendBack(String a) {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(a);
			out.close();
		} catch (IOException e) {
			ui.updateArea("Client not accessible");
		}
	}

	/**
	 * Implemented by all subclasses to process client requests.
	 * 
	 * @param a
	 *            the a
	 */
	protected abstract void performAction(String a);

	/**
	 * Find free port.
	 * 
	 * @return the int
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static int findFreePort() throws IOException {
		ServerSocket server = new ServerSocket(0);
		int port = server.getLocalPort();
		server.close();
		return port;
	}

	public abstract String getStatus();

}
