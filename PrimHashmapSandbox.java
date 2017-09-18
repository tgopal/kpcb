import java.util.*;

/*
 * Author: @tgopal - Tejas Gopal, KPCB Engineering Fellow Applicant
 *
 * Class PrimHashmapSandbox will set up a sandbox test environment where the user is 
 * free to experiment with the PrimHashmap with value types (for the sake of 
 * simplicity) String or Integer. The user, after selecting a size and value type, 
 * will have the following options:
 *
 * set(key, val) - Maps a String key to an ArbObj val
 * get(key) - Retrieves the ArbObj that this String key maps to
 * delete(key) - deletes the key-val mapping
 * load() - finds the load factor, a measure of how full the map is
 * exit - ends the sandbox program
 */
public class PrimHashmapSandbox {
	
	private PrimHashmap hashmap;	// hashmap reference to use in all operations
	private static int typeChoice;	// value obj type string or int selected

	/**
	 * Default empty constructor for sandbox.
	 */
	public PrimHashmapSandbox() {}

	/**
	 * Function main will be the main execution point of the sandbox program. It will 
	 * delegate to helper functions for functionality.
	 *
	 * @param args gives array of string command line arguments
	 */
	public static void main(String[] args) {
		System.out.println("\nHello! Welcome to Tejas's KPCB Application.\n");

		PrimHashmapSandbox controller = new PrimHashmapSandbox();
		typeChoice = controller.promptForValueType();
		controller.create(typeChoice);
		controller.sandbox(typeChoice);
	}

	/**
	 * Function promptForValueType will prompt the user for the type of the value
	 * to be used in the sandbox hashmap (String or Integer)
	 *
	 * @return 1 for String, 2 for Integer.
	 */
	public int promptForValueType() {

		Scanner scanner = new Scanner(System.in);
		int choice;

		System.out.println("1. String");
		System.out.println("2. Integer\n");

		while(true) {
			System.out.print("To get started, choose a value object type (1 or 2): ");

			if (!scanner.hasNextInt()) {
				// An integer wasn't entered.
				System.out.println("You must enter either 1 or 2!");
			} else {
				// Get the int entered, validity check against available options.
				choice = scanner.nextInt();
				if (choice == 1 || choice == 2) return choice;
				System.out.println("Invalid choice...try again.");
			}
			scanner.nextLine();	// Discards input and reprompts if integer that is not 1 or 2
		}
	}

	/**
	 * Function create will prompt the user for the intended size of the hashmap and 
	 * then, based on the choice of value type, will create a new hashmap object.
	 *
	 * @param choice - 1 for String, 2 for Integer (user selected value type)
	 */
	public void create(int choice) {

		Scanner scanner = new Scanner(System.in);
		int size = 0;

		while(true) {

			System.out.print("Please enter the size of your hashmap (greater than 0): ");
			if (!scanner.hasNextInt()) {
				// An integer was not entered!
				System.out.println("You must enter a valid integer size!");
			} else {
				// Get the int entered and validity check.
				size = scanner.nextInt();
				if (size > 0) break;
				System.out.println("Invalid size...try again.");
			}
			
			scanner.nextLine();	// Discard input and reprompt if integer is not valid.
		}

		// Construct the appropriately typed hashmap based on user's value type choice
		if (choice == 1) hashmap = new PrimHashmap<String>(size);
		else hashmap = new PrimHashmap<Integer>(size);
	}

	/**
	 * Function create will prompt the user for the intended size of the hashmap and 
	 * then, based on the choice of value type, will create a new hashmap object.
	 *
	 * @param choice - 1 for String, 2 for Integer (user selected value type)
	 */
	public void sandbox(int choice) {
		String type = "";
		if (choice == 1) type = "String";
		else type = "Integer";

		Scanner scanner = new Scanner(System.in);
		int oper = 0;
		while (true) {

			// Menu of options!
			System.out.println("----------------");
			System.out.println("1. Set");
			System.out.println("2. Get");
			System.out.println("3. Delete");
			System.out.println("4. Load");
			System.out.println("5. Exit");
			System.out.println("----------------\n");


			System.out.print("What will you do (1-5)? : ");
			if (!scanner.hasNextInt()) {
				// An integer wasn't entered.
				System.out.println("You must enter a valid integer choice (1-5)!");
			} else {
				// Retrieve input and validity check.
				oper = scanner.nextInt();
				if (oper >= 1 && oper <= 5) break;
				System.out.println("Invalid choice...try again.");
			}
		}

		// Based on choice, delegate to execution functions to prompt for info.
		switch (oper) {
			case 1: 
				performSet();		// set
				break;
			case 2:
				performGet();		// get
				break;
			case 3:
				performDelete();	// delete
				break;
			case 4:
				performLoad();		// load
				break;
			case 5:
				// Exits the program!
				System.out.println("Thanks for using Tejas's hashmap!\n");
				System.exit(1);
		}
	}

	/**
	 * Function performSet will prompt the user for a String key and an ArbObj 
	 * value (depends on their choice) and set() using PrimHashmap.
	 */
	public void performSet() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nPlease enter a String key: ");
		String key = scanner.nextLine();
		System.out.println();

		if (typeChoice == 1) {
			// If object value type is String, then we prompt for a string and 
			// set whatever we read!
			System.out.print("Please enter a String value: ");
			String value = scanner.nextLine();

			// Failure check - map already full
			boolean res = hashmap.set(key, value);
			if (!res) {
				System.out.println("Hashmap is full, consider deleting an entry.");
			}
		} else {
			// If object value type is Integer, then we prompt for a int, validity check,
			// then set whatever we read!
			System.out.print("Please enter an integer value: ");
			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.print("Not an integer, try again. Please enter an integer value: ");
			}

			// Failure check - map already full
			boolean res = hashmap.set(key, scanner.nextInt());
			if (!res) {
				System.out.println("Hashmap is full, consider deleting an entry.");
			}
		} 
		
		this.sandbox(typeChoice);
	}

	/**
	 * Function performGet will prompt the user for a String key, and will then
	 * notify the user of the key's value or if nothing exists.
	 */
	public void performGet() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nPlease enter a String key: ");
		String key = scanner.nextLine();
		System.out.println();

		// Query using input key, checking existence.
		if(hashmap.get(key) == null) {
			System.out.println("That key doesn't exist in the map.");
		} else {
			System.out.println("Success! " + key + " maps to -> " + hashmap.get(key));
		}

		this.sandbox(typeChoice);
	}

	/**
	 * Function performDelete will prompt the user for a String key, and will then
	 * notify the user of the key's value or if nothing exists before deleting the
	 * key-value entry in the map.
	 */
	public void performDelete() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nPlease enter a String key: ");
		String key = scanner.nextLine();
		System.out.println();

		// Query using input key, checking existence. Then delete if exists!
		if(hashmap.get(key) == null) {
			System.out.println("That key doesn't exist in the map.");
		} else {
			System.out.println("Success! " + key + " -> " + hashmap.delete(key) + " was deleted.");
		}

		this.sandbox(typeChoice);
	}

	/**
	 * Function performLoad will retrieve load factor by delegating to PrimHashmap's load function.
	 */
	public void performLoad() {
		System.out.println("Current load factor is: " + hashmap.load());
		this.sandbox(typeChoice);
	}
}