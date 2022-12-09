package services;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import serviceui.ServiceUI;

public class FridgeService extends Service {

	private final Vector<String> items;
	private final String needed = "You Need:-Coca Cola-Chicken Kievs-Onions-Eggs-Mustard";

	/**
	 * Fridge Service constructor
	 * 
	 * @param name
	 * @param port
	 */
	public FridgeService(String name) {
		super(name, "fridge._udp.local.");
		items = new Vector<String>();
		ui = new ServiceUI(this, name);
		addContents(new String[] { "Milk", "Cheese", "Bacon", "Chicken" });
	}

	@Override
	public void performAction(String a) {
		if (a.equals("get_status")) {
			sendBack(getStatus());
		} else if (a.equals("getContents")) {
			sendBack(items.toString());
		} else if (a.equals("getList")) {
			sendBack(needed);
		} else {
			sendBack(BAD_COMMAND);
		}
	}

	public void addContents(String[] strings) {
		List<String> a = Arrays.asList(strings);
		items.addAll(a);
	}

	@Override
	public String getStatus() {
		return "Fridge Operational";
	}

	public static void main(String[] args) {
		new FridgeService("Fridge");
	}
}
