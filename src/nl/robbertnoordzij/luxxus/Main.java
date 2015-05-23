package nl.robbertnoordzij.luxxus;

public class Main {

	public static void main(String[] args) {
		LuxxusController controller = new LuxxusController();
		
		controller.getEventManager().addGatewayConnectedListener((event) -> {
			// Switch all off or off
			LuxxusLamp[] lamps = controller.getLamps();
			for (LuxxusLamp lamp : lamps) {
				lamp.setIntensity(lamp.getIntensity() > 125 ? 0 : 255);
			}
			controller.updateLamps(lamps);
			
			System.exit(0);
		});
	}

}
