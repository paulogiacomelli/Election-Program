package Personnel;

import java.text.DecimalFormat;

public abstract class Candidate {
	protected String party;

	protected String firstName;
	protected String lastName;
	protected int age;
	protected String gender;
	protected String email;
	protected double moneyAmount;
	protected double winPercentage;
	DecimalFormat fmt = new DecimalFormat("#,###");
	
	public Candidate(String party, String firstName, String lastName, int age, String gender, String email, double moneyAmount, double winPercentage) {
		this.party = party;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.email = email;
		this.moneyAmount = moneyAmount;
		this.winPercentage = winPercentage;
	}
	
	
	public String getParty() {
		return party;
	}


	public void setParty(String party) {
		this.party = party;
	}


	public double getMoneyAmount() {
		return moneyAmount;
	}


	public void setMoneyAmount(double moneyAmount) {
		this.moneyAmount = moneyAmount;
	}


	public double getWinPercentage() {
		return winPercentage;
	}


	public void setWinPercentage(double winPercentage) {
		this.winPercentage = winPercentage;
	}


	public abstract String toString();
	

}
