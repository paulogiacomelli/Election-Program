package Events;

public abstract class Issues {
	
	protected String name;
	protected String effect;
	protected double effectValue;
	
	
	public Issues(String name, String effect, double effectValue) {
		this.name = name;
		this.effect = effect;
		this.effectValue = effectValue;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public double getEffectValue() {
		return effectValue;
	}

	public void setEffectValue(double effectValue) {
		this.effectValue = effectValue;
	}

	
}
