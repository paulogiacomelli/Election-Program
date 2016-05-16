package Election;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Money {
	
	
	private double moneyReceived;
	NumberFormat fmt = DecimalFormat.getCurrencyInstance();


	public Money(double moneyReceived) {
		super();
		this.moneyReceived = moneyReceived;
	}

	public double getMoneyReceived() {
		return moneyReceived;
	}

	public void setMoneyReceived(double moneyReceived) {
		this.moneyReceived = moneyReceived;
	}


	// Methods
	public String toString() {
		return "";
	}
	

	
	
}
