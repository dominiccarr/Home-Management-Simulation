package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

class SendMessage extends Thread {
	final private int port;
	final private String ip;
	final private String details;

	public SendMessage(String ip, int port, String details) {
		this.port = port;
		this.ip = ip;
		this.details = details;
		this.start();
	}

	@Override
	public void run() {
		Socket s;
		try {
			s = new Socket(ip, port);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			out.print(details);
			out.close();
			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}