package Events;

public class Advertising extends Issues {
	
	private int cost;
	
	public Advertising(String name, String effect, double effectValue, int cost) {
		super(name, effect, effectValue);
		this.cost = cost;
	}
	
	public double playEffect() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String toString() {
		
		return "Ad Name: " + name + 
				" Effect Value: " + effectValue +
				" Cost: " + cost;
	}
	
	public double costToAdCheck() {
		return 0;
	}
	
	public double percentAddition() {
		return 0;
	}




}
