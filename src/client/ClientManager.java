package client;

import java.io.IOException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import clientui.ClientManagerUI;

/**
 * Client Manager class.
 * 
 * @author dominic
 */
public class ClientManager implements ServiceListener {

	private final ClientManagerUI ui;
	private JmDNS jmdns;
	private final BathClient bathclient = new BathClient();
	private final BedClient bedclient = new BedClient();
	private final OvenClient ovenclient = new OvenClient();
	private final DishwasherClient dishwasherclient = new DishwasherClient();
	private final PCClient pcclient = new PCClient();
	private final HeatingClient heatingclient = new HeatingClient();
	private final LightClient lightclient = new LightClient();
	private final MediaServerClient mediaserverclient = new MediaServerClient();
	private final SecurityClient securityClient = new SecurityClient();
	private final TVClient tvclient = new TVClient();
	private final KettleClient kettleclient = new KettleClient();
	private final FridgeFreezerClient fridgefreezerclient = new FridgeFreezerClient();
	private final ToasterClient toasterclient = new ToasterClient();

	/** The services. */
	private final Client[] clients = { bathclient, bedclient, ovenclient,
			dishwasherclient, pcclient, heatingclient, lightclient,
			mediaserverclient, securityClient, tvclient, fridgefreezerclient,
			kettleclient, toasterclient };

	/**
	 * The constructor for the client manager.
	 */
	public ClientManager() {
		try {
			jmdns = JmDNS.create();
			for (Client service : clients) {
				jmdns.addServiceListener(service.getServiceType(), this);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ui = new ClientManagerUI(this);
	}

	/**
	 * closes the jmdns.
	 */
	public void end() {
		jmdns.close();
	}

	/**
	 * handles services being added.
	 * 
	 * @param arg0
	 *            the arg0
	 */
	public void serviceAdded(ServiceEvent arg0) {
		arg0.getDNS().requestServiceInfo(arg0.getType(), arg0.getName(), 0);
	}

	/**
	 * handles services being removed.
	 * 
	 * @param arg0
	 *            the arg0
	 */
	public void serviceRemoved(ServiceEvent arg0) {
		String type = arg0.getType();
		String name = arg0.getName();
		ServiceInfo newService = null;
		for (Client client : clients) {
			if (client.getServiceType().equals(type) && client.hasMultiple()) {
				if (client.isCurrent(name)) {
					ServiceInfo[] a = jmdns.list(type);
					for (ServiceInfo in : a) {
						if (!in.getName().equals(name)) {
							newService = in;
						}
					}
					client.switchService(newService);
				}
				client.remove(name);
			} else if (client.getServiceType().equals(type)) {
				ui.removePanel(client.returnUI());
				client.disable();
				client.initialized = false;
			}
		}
	}

	/**
	 * handles services being resolved.
	 * 
	 * @param arg0
	 *            the arg0
	 */
	public void serviceResolved(ServiceEvent arg0) {
		String address = arg0.getInfo().getHostAddress();
		int port = arg0.getInfo().getPort();
		String type = arg0.getInfo().getType();
		for (Client client : clients) {
			if (client.getServiceType().equals(type) && !client.isInitialized()) {
				client.setUp(address, port);
				ui.addPanel(client.returnUI(), client.getName());
				client.setCurrent(arg0.getInfo());
				client.addChoice(arg0.getInfo());
			} else if (client.getServiceType().equals(type)
					&& client.isInitialized()) {
				client.addChoice(arg0.getInfo());
			}
		}
	}

	public static void main(String[] args) {
		new ClientManager();
	}
}