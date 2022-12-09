package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import serviceui.ServiceUI;

/**
 * The Class ToasterService.
 */
public class ToasterService extends Service {

	/** The temperature. */
	private final int temperature;
	private final Timer timer;
	private int percenttoasted;

	/**
	 * Instantiates a new toaster service.
	 * 
	 */
	public ToasterService(String name, int temp) {
		super(name, "toaster._udp.local.");
		status = "toaster Online";
		temperature = temp;
		timer = new Timer();
		ui = new ServiceUI(this, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see services.Service#performAction(java.lang.String)
	 */
	@Override
	public void performAction(String a) {
		if (a.equals("get_status")) {
			sendBack(getStatus());
		} else if (a.startsWith("level-")) {
			String[] s = a.split("-");
			sendBack("OK");
			ui.updateArea("level " + s[1]);
		} else if (a.equals("getinitial")) {
			sendBack("temperature:" + temperature);

		} else if (a.equals("Start Toasting")) {
			timer.schedule(new RemindTask(), 0, 2000);
			sendBack("OK");
		} else {
			sendBack(BAD_COMMAND);
		}
	}

	public String sendMessage(String a_message, String serverHost,
			int serverPort) {
		String msg = "";
		try {
			Socket toServer = new Socket(serverHost, serverPort);
			PrintWriter out = new PrintWriter(toServer.getOutputStream(), true);
			out.println(a_message);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					toServer.getInputStream()));
			msg = in.readLine();
			out.close();
			toServer.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return msg;
	}

	@Override
	public String getStatus() {
		return status;
	}

	/**
	 * The Class RemindTask.
	 */
	class RemindTask extends TimerTask {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
			if (percenttoasted < 100) {
				percenttoasted += 10;
				status = "toast " + percenttoasted + "% done";
			}
		}
	}

	public static void main(String[] args) {
		new ToasterService("kitchen toaster", 20);
	}
}