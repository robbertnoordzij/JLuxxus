package nl.robbertnoordzij.luxxus;

public class Main {

	public static void main(String[] args) {
		LuxxusController controller = new LuxxusController();
		
		controller.getEventManager().addGatewayConnectedListener((event) -> {
			Utility.sleep(1000);

			// Switch all off or off
			LuxxusLamp[] lamps = controller.getLamps();
			for (LuxxusLamp lamp : lamps) {
				lamp.setRGB(0, 0, 255, lamp.getIntensity() > 125 ? 0 : 255);
			}
			controller.updateLamps(lamps);
			
			System.exit(0);
		});
	}

}
