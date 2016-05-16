package Election;
import Personnel.Democrat;
import Personnel.Republican;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import Events.Advertising;
import Events.RandomEvents;

public class driver {

	String firstName, lastName, gender, email;
	static double winPercentage = 0.5;
	static String currentName;
	static String previousName = "";
	static String party;
	static String name, effect;
	static double effectValue;
	int age;
	static int moneyAmount;
	static int counter = 1, cost;
	static int added = 0;
	static Timer myTimer = new Timer();
	static Advertising myAds;
	static Republican myRepublican;
	static Democrat myDemocrat;
	static RandomEvents myEvents;
	static Money republicanMoney, democratMoney;
	static ArrayList<Advertising> database = new ArrayList<Advertising>();
	static ArrayList<RandomEvents> eventsData = new ArrayList<RandomEvents>();
	static ArrayList<RandomEvents> allRandomEvents = new ArrayList<RandomEvents>();
	static ArrayList<Double> moneyArray = new ArrayList<Double>();
	static Scanner myScan = new Scanner(System.in);
	static NumberFormat fmt = DecimalFormat.getCurrencyInstance();

	public static void main(String[] args) throws FileNotFoundException {

		readAd();
		addCandidate();
		readEvents();

		myTimer.scheduleAtFixedRate(new keepTrack(), 1000, 500);

		do {

			menu();


		}while(driver.counter < 180);



	}

	// Method that adds the candidates and starting values.
	private static void addCandidate() {

		myRepublican = new Republican("Repulican", "Abraham", "Lincoln", 45, "Male", "abraham.lincoln@republican.com", moneyAmount, winPercentage);
		myDemocrat = new Democrat("Democrat", "John F", "Kennedy", 40, "Male", "jonhf.kennedy@democract.com", moneyAmount, winPercentage);

		// Keep track of the money
		republicanMoney = new Money(moneyAmount);
		democratMoney = new Money(moneyAmount);

		// Give a starting amount of money to the parties.
		myRepublican.setMoneyAmount(500.0);
		republicanMoney.setMoneyReceived(moneyAmount);
		myDemocrat.setMoneyAmount(500.0);
		democratMoney.setMoneyReceived(moneyAmount);


		System.out.println(myRepublican);
		System.out.println(myDemocrat);
	}

	// Method reads all the events from a file and adds to an array.
	private static void readEvents() throws FileNotFoundException {

		// Read in the file
		File events = new File("events.txt");
		Scanner eventsScan = new Scanner(events);

		eventsScan.nextLine(); // picks up the header;

		while (eventsScan.hasNext()) // Read all the data inside of the file.
		{
			Scanner eventsLine = new Scanner(eventsScan.nextLine()).useDelimiter(",");

			name = eventsLine.next();
			currentName = name;
			effect = eventsLine.next();
			String value = eventsLine.next();

			effectValue = Double.parseDouble(value);

			//	effectValue = eventsLine.nextDouble();


			// Checks the file, and adds the object to the correct spot in the array.
			if(previousName.equals(currentName)) {
				added++;

			}

			else {


				if(!previousName.equals("")) {
					myEvents = new RandomEvents(name, effect, effectValue);
					added=0;
					eventsData.add(myEvents);
				}
			}

			eventsLine.close();
			previousName = currentName;

		}// outside loop
	}

	// Method reads all the ads from a file and adds to an array.
	private static void readAd() throws FileNotFoundException {

		// Read in the file
		File ads = new File("adfile.txt");
		Scanner lineScan = new Scanner(ads);

		lineScan.nextLine(); // picks up the header;

		while (lineScan.hasNext()) // Read all the data inside of the file.
		{
			Scanner line = new Scanner(lineScan.nextLine()).useDelimiter(",");

			name = line.next();
			currentName = name;
			effectValue = line.nextDouble();
			cost = line.nextInt();


			// Checks the file, and adds the object to the correct spot in the array.
			if(previousName.equals(currentName)) {

				added++;

			}

			else {

				if(currentName.equals("TVAd")) {
					myAds = new Advertising(name, effect, effectValue, cost);
					database.add(myAds);
				}


				if(!previousName.equals("")) {
					myAds = new Advertising(name, effect, effectValue, cost);
					added=0;
					database.add(myAds);


				}
			}



			line.close();
			previousName = currentName;

		}// outside loop

	}

	// Method that gets user input of what party they choose and run the republicanTrack/democratTrack.
	private static void chooseParty() {

		System.out.println("Which party would you like to donate money to?");
		// Preventive Error Handling for bad input.
		while(!myScan.hasNext()) {
			System.out.println("Sorry, Something went wrong. Please use Republican or Democrat.");
			chooseParty();
		}
		party = myScan.next();

		if(party.equalsIgnoreCase("Republican")) {

			republicanTrack();
		}
		else if (party.equalsIgnoreCase("Democrat")) {

			democratTrack();
		}

		else {
			System.out.println("Sorry, Something went wrong. Please use Republican or Democrat.");
			chooseParty();
		}


	}

	// Method that gets user input for the republican party and keeps track of the money amount.
	// Also runs the advertisement to cost check.
	public static void republicanTrack() {
		System.out.println("How much money would you like to donate? (10,000 or 1,000 or 750 or 500 or 250)");
		while (!myScan.hasNextInt()) {
			// Output error
			System.out.println("Invalid Donation Number. Please use (10,000 or 1,000 or 750 or 500 or 250).");
			myScan.next(); //Go to next
		}
		moneyAmount = myScan.nextInt();

		//republicanMoney = new Money(moneyAmount);
		double totalRepMoney = republicanMoney.getMoneyReceived() + moneyAmount;
		republicanMoney.setMoneyReceived(totalRepMoney);
		myRepublican.setMoneyAmount(totalRepMoney);

		boolean amountFound = false;
		for (int i = 0; i < database.size(); i++) {

			if(moneyAmount == database.get(i).getCost()){
				amountFound = true;
				System.out.println("Here is the Advertising you can create:");
				System.out.println(database.get(i).toString());

				System.out.println("Would you like to run a Ad in your favor or against you? Favor = 1 / Against = 2");
				// Input prevent error handling.
				while (!myScan.hasNextInt()) {
					// Output error
					System.out.println("Invalid Menu Number. Please use Favor = 1 / Against = 2.");
					myScan.next(); //Go to next
				}
				int userInput = myScan.nextInt();

				// Checks for what the candidate wants to do with the ad. (i.e: increase winPercentage)
				if(userInput == 1) {
					winPercentage = myRepublican.getWinPercentage() + database.get(i).getEffectValue();
					myRepublican.setWinPercentage(winPercentage);
					System.out.println("Republican Current Winning Percentage: " + winPercentage);
				}

				else if(userInput == 2) {

					winPercentage = myDemocrat.getWinPercentage() - database.get(i).getEffectValue();
					myDemocrat.setWinPercentage(winPercentage);
					System.out.println("Republican Current Winning Percentage: " + winPercentage);

				}

				else {
					System.out.println("Oops,Something went wrong! Make sure you use Favor = 1 / Against = 2");
					menu();
				}
			}

		}

		if (!amountFound) { 
			System.out.println("Sorry. You don't have enough money");
			menu();
		}
	}

	// Method that gets user input for the democrat party and keeps track of the money amount.
	// Also runs the advertisement to cost check.
	public static void democratTrack() {
		System.out.println("How much money would you like to donate? Please use (10,000 or 1,000 or 750 or 500 or 250)");
		while (!myScan.hasNextInt()) {
			// Output error
			System.out.println("Invalid Donation Number. Please use (10,000 or 1,000 or 750 or 500 or 250).");
			myScan.next(); //Go to next
		}
		moneyAmount = myScan.nextInt();


		//democratMoney = new Money(moneyAmount);
		double totalDemMoney = democratMoney.getMoneyReceived() + moneyAmount;
		democratMoney.setMoneyReceived(totalDemMoney);
		myDemocrat.setMoneyAmount(totalDemMoney);

		boolean amountFound = false;
		for (int i = 0; i < database.size(); i++) {

			if(moneyAmount == database.get(i).getCost()){
				amountFound = true;
				System.out.println("Here is the Advertising you can create:");
				System.out.println(database.get(i).toString());

				System.out.println("Would you like to run a Ad in your favor or against you? Favor = 1 / Against = 2");
				
				// Input prevent error handling.
				while (!myScan.hasNextInt()) {
					// Output error
					System.out.println("Invalid Menu Number. Please use Favor = 1 / Against = 2.");
					myScan.next(); //Go to next
				}
				int userInput = myScan.nextInt();
				
				// Checks for what the candidate wants to do with the ad. (i.e: increase winPercentage)
				if(userInput == 1) {
					winPercentage = myDemocrat.getWinPercentage() + database.get(i).getEffectValue();
					myDemocrat.setWinPercentage(winPercentage);
					System.out.println("Democrat Current Winning Percentage: " + winPercentage);
				}

				else if(userInput == 2) {

					winPercentage = myRepublican.getWinPercentage() - database.get(i).getEffectValue();
					myRepublican.setWinPercentage(winPercentage);
					System.out.println("Democrat Current Winning Percentage: " + winPercentage);

				}

				else {
					System.out.println("Oops,Something went wrong! Make sure you use Favor = 1 / Against = 2");
					menu();
				}
			}

		}

		if (!amountFound) { 
			System.out.println("Sorry. You don't have enough money");
			menu();
		}
	}

	// Method that randomize the event occurrences in specific times.
	public static void randomizeEvent() {

		if(driver.counter == 20 || driver.counter == 40 || driver.counter == 60 || driver.counter == 80 || 
				driver.counter == 100 || driver.counter == 120 || driver.counter == 140 || driver.counter == 160 ) {
			Collections.shuffle(driver.eventsData);
			//System.out.println(driver.eventsData.get(0));

			int oneParty = (int)(Math.random()*2);
			//System.out.println(oneParty);

			if(oneParty == 0) {
				winPercentage = myRepublican.getWinPercentage() + eventsData.get(0).getEffectValue();
				myRepublican.setWinPercentage(winPercentage);
				allRandomEvents.add(eventsData.get(0));
				//System.out.println("Republican Current Winning Percentage: " + driver.winPercentage);

			}

			if(oneParty == 1) {
				winPercentage = myDemocrat.getWinPercentage() + eventsData.get(0).getEffectValue();
				myDemocrat.setWinPercentage(winPercentage);
				allRandomEvents.add(eventsData.get(0));

				//System.out.println("Democrat Current Winning Percentage: " + driver.winPercentage);
			}
		}
	}

	// Method that allows user to just random donate amount of money to the specific party.
	public static void randomMoney() {

		int randomCash = (int)(Math.random()*2);
		// Add all the Possible Cash Donation Values.
		moneyArray.add(10000.0);
		moneyArray.add(1000.0);
		moneyArray.add(750.0);
		moneyArray.add(500.0);
		moneyArray.add(250.0);

		Collections.shuffle(moneyArray);

		// For republicans
		if(randomCash == 0) {
			System.out.println("You Donated: " + moneyArray.get(0) + " to the Republican Party");

			double totalRepMoney = republicanMoney.getMoneyReceived() + moneyArray.get(0);
			republicanMoney.setMoneyReceived(totalRepMoney);
			myRepublican.setMoneyAmount(totalRepMoney);

			for (int i = 0; i < database.size(); i++) {
				if(moneyArray.get(0) == database.get(i).getCost()) {

					winPercentage = myRepublican.getWinPercentage() + database.get(i).getEffectValue();
					myRepublican.setWinPercentage(winPercentage);	

				}



			}// for loop


		}

		// For democrat
		else if (randomCash == 1) {
			//Collections.shuffle(moneyArray);
			System.out.println("You Donated: " + moneyArray.get(0) + " to the Democrat Party");

			double totalDemMoney = democratMoney.getMoneyReceived() + moneyArray.get(0);
			democratMoney.setMoneyReceived(totalDemMoney);
			myDemocrat.setMoneyAmount(totalDemMoney);

			for (int i = 0; i < database.size(); i++) {
				if(moneyArray.get(0) == database.get(i).getCost()) {

					winPercentage = myDemocrat.getWinPercentage() + database.get(i).getEffectValue();
					myDemocrat.setWinPercentage(winPercentage);	

				}



			}// for loop
		}

		else {
			System.out.println("Oops,Something went wrong!");
			menu();
		}


	}

	public static void save() {


		FileWriter writer = null;

		// Try to create a file to save the current state of the program, if IOException found, then handle nicely.
		try {
			File file = new File("saved.txt");

			// creates the file
			file.createNewFile();

			// creates a FileWriter Object
			writer = new FileWriter(file); 

			// Writes the content to the file
			writer.write("time, republican money, democrat money, republican win percentage, democrat win percentage\n");
			writer.write(driver.counter + " " + myRepublican.getMoneyAmount() + " " + myDemocrat.getMoneyAmount()
			+ " " + myRepublican.getWinPercentage() + " " + myDemocrat.getWinPercentage()); 
			writer.flush();
			writer.close();
			myTimer.cancel();
			System.out.println("Thanks for simulating your election program with us.");
			System.out.println("Your Progress has been saved.");

		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("Sorry we could not save your progress");
			menu();	
		}



	}
	public static void menu() {
		System.out.println("What would you like to do?");
		System.out.println("1. Check Time?");
		System.out.println("2. Winning Chances?");
		System.out.println("3. Donate Money?");
		System.out.println("4. Random Money?");
		System.out.println("5. Unexpected Events?");
		System.out.println("6. Ad Prices?");
		System.out.println("7. Save Election");

		// Input error handling for Strings when you want ints.
		int userinput = 0;
		while (!myScan.hasNextInt()) {
			// Output error
			System.out.println("Invalid Menu Number. Please enter only digits (1-7).");
			myScan.next(); //Go to next
		}
		userinput = myScan.nextInt();



		if(userinput == 1) {
			keepTrack.time();
		}

		else if(userinput == 2) {
			System.out.println("Republican Current Winning Percentage: " + myRepublican.getWinPercentage());
			System.out.println("Democrat Current Winning Percentage: " + myDemocrat.getWinPercentage());

		}

		else if(userinput == 3) {
			chooseParty();
		}

		else if(userinput == 4) {
			randomMoney();
		}

		else if(userinput == 5) {
			// Prints out the Random Events.
			for(int i=0; i < eventsData.size() ; i++) {
				String info = eventsData.get(i).toString();
				System.out.println(info);
			}
		}

		else if(userinput == 6) {
			// Prints out the Ads.
			for(int i=0; i < database.size() ; i++) {
				String info = database.get(i).toString();
				System.out.println(info);

			}
		}
		else if (userinput == 7) {
			save();
			driver.counter = 180;

		}



	}
}

// Runs a Timer for the Election (6 months).
class keepTrack extends TimerTask
{

	public void run() {

		if(driver.counter >= 185)
		{
			driver.myTimer.cancel();
		}

		driver.counter++;

		driver.randomizeEvent();
		displayResults();


	}

	// Methods that tells the time at 3 months, and at 6 months.
	public static void time() {
		System.out.println("Current time is: " + driver.counter);
	}

	// Method that display the results when the time runs off.
	private static void displayResults() {
		if(driver.counter == 185) {

			System.out.println();
			if(driver.myRepublican.getWinPercentage() > driver.myDemocrat.getWinPercentage())
			{
				System.out.println("Republican Wins: " + driver.myRepublican.toString());

			}
			else {
				System.out.println("Democrat Wins: " + driver.myDemocrat.toString());
			}

			System.out.println();
			System.out.println("Election Summary:");
			System.out.println();

			System.out.println("Events that happened in 6 months:");
			for (int i = 0; i < driver.allRandomEvents.size(); i++) {
				System.out.println(driver.allRandomEvents.get(i));
			}

			System.out.println();
			System.out.println("Total Money Donated");
			System.out.println("Republican Party: " + driver.fmt.format(driver.republicanMoney.getMoneyReceived()));
			System.out.println("Democrat Party: " + driver.fmt.format(driver.democratMoney.getMoneyReceived()));

			System.out.println();
			System.out.println("Final Winning Percentages");
			System.out.println("Republican: " + driver.myRepublican.getWinPercentage());
			System.out.println("Democrat: " + driver.myDemocrat.getWinPercentage());

		}
	}

}

