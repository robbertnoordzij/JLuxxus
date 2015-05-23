package nl.robbertnoordzij.luxxus;

public class Main {

	public static void main(String[] args) {
		LuxxusController controller = new LuxxusController();
		
		controller.getEventManager().addGatewayConnectedListener((event) -> {
			LuxxusLamp[] lamps = controller.getLamps();
			
			for (LuxxusLamp lamp : lamps) {
				System.out.println(lamp);
				lamp.setRGB(0, 0, 255);
				lamp.setIntensity(lamp.getIntensity() > 0 ? 0 : 255);
			}
			
			controller.updateLamps(lamps);
			
			System.exit(0);
		});
	}

}
