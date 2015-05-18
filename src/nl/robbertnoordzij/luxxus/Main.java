package nl.robbertnoordzij.luxxus;

import nl.robbertnoordzij.luxxus.events.events.GatewayConnectedEvent;
import nl.robbertnoordzij.luxxus.events.listeners.GatewayConnectedListener;

public class Main {

	public static void main(String[] args) {
		final LuxxusController controller = new LuxxusController();
		
		controller.getEventManager().addGatewayConnectedListener(new GatewayConnectedListener() {
			public void onGatewayConnected(GatewayConnectedEvent event) {
				Utility.sleep(1000);

				// Switch all off or off
				LuxxusLamp[] lamps = controller.getLamps();
				for (LuxxusLamp lamp : lamps) {
					lamp.setRGB(0, 0, 255, lamp.getIntensity() > 125 ? 0 : 255);
				}
				controller.updateLamps(lamps);
				
				System.exit(0);
			}
		});
	}

}
