/*
 * 
 */
package test;

import services.DoorService;
import services.MediaServerService;
import services.SecurityService;
import services.SpeakersService;
import services.WindowService;

/**
 * Code testing environment.
 * 
 * @author dominic
 */
public class ServiceTest {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		// new BathService("Dominic-Ensuite");
		// new BedService("Dominic's");
		// new BedService("Sean's");
		// new BedService("Nula's");
		// new HeatingService("House", 40);
		// new PCService("Dominic");
		// new KettleService("Kitchen");
		// new FridgeService("Fridge");
		// new WashingMachineService("Washing Machine");
		 new MediaServerService("Living room server");
		// new WashingMachineService("Washing Machine");
		// new MediaServerService("Living room server");
		// new LightService("Living Room Light", "Living Room");
		//new DoorService("1");
		//new DoorService("2");
		// new DoorService("3");
		// new DoorService("4");
		// new DoorService("5");
		// new DoorService("6");
		// new DoorService("7");
		// new DoorService("8");

		// new WindowService("w1");
		// new WindowService("w2");
		// new WindowService("w3");
		// new WindowService("w4");
		// new WindowService("w5");
		//new WindowService("w6");
		//new SecurityService("house-security", "1", 10);
		 new SpeakersService("livingroom", "kitchen");
		// new TVService("living-room-tv", 1, 20);

	}
}
