package Events;

public class RandomEvents extends Issues {
	

	public RandomEvents(String name, String effect, double effectValue) {
		super(name, effect, effectValue);
	}


	public double playEffect() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
public String toString() {
		
		return "Event Name: " + name + 
				" Effect:" + effect +
				" Effect Value: " + effectValue;
	}
	
	

}
