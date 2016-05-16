package Personnel;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Democrat extends Candidate {
	
	NumberFormat fmt1 = DecimalFormat.getCurrencyInstance();

	
	public Democrat(String party, String firstName, String lastName, int age, String gender, String email, int moneyAmount, double winPercentage) {
		super(party, firstName, lastName, age, gender, email, moneyAmount, winPercentage);
	
	}

	public String toString() {
		return "Name: " + firstName + " " + lastName +
				" Age: " + age +
				" Gender: " + gender +
				" Email: " + email +
				" MoneyAmount: " + fmt1.format(moneyAmount)
				+ " Winning Percentage: " + fmt.format(winPercentage);
	}
	
	
	
}
