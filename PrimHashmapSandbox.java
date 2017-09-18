import java.util.*;

public class PrimHashmapSandbox {
	
	private PrimHashmap hashmap;
	private static int typeChoice;

	public PrimHashmapSandbox() {}

	public static void main(String[] args) {
		System.out.println("\nHello! Welcome to Tejas's KPCB Application.\n");
		PrimHashmapSandbox controller = new PrimHashmapSandbox();
		typeChoice = controller.promptForValueType();
		controller.create(typeChoice);
		controller.sandbox(typeChoice);
	}

	public int promptForValueType() {

		Scanner scanner = new Scanner(System.in);
		int choice;
		System.out.println("1. String");
		System.out.println("2. Integer\n");

		while(true) {
			System.out.print("To get started, choose a value object type (1 or 2): ");
			if (!scanner.hasNextInt()) {
				System.out.println("You must enter either 1 or 2!");
			} else {
				choice = scanner.nextInt();
				if (choice == 1 || choice == 2) return choice;
				System.out.println("Invalid choice...try again.");
			}
			scanner.nextLine();
		}
	}

	public void create(int choice) {

		Scanner scanner = new Scanner(System.in);
		int size = 0;

		while(true) {
			System.out.print("Please enter the size of your hashmap (greater than 0): ");
			if (!scanner.hasNextInt()) {
				System.out.println("You must enter a valid integer size!");
			} else {
				size = scanner.nextInt();
				if (size > 0) break;
				System.out.println("Invalid size...try again.");
			}
			
			scanner.nextLine();
		}

		if (choice == 1) hashmap = new PrimHashmap<String>(size);
		else hashmap = new PrimHashmap<Integer>(size);
	}

	public void sandbox(int choice) {
		String type = "";
		if (choice == 1) type = "String";
		else type = "Integer";

		Scanner scanner = new Scanner(System.in);
		int oper = 0;
		while (true) {
			System.out.println("----------------");
			System.out.println("1. Set");
			System.out.println("2. Get");
			System.out.println("3. Delete");
			System.out.println("4. Load");
			System.out.println("5. Exit");
			System.out.println("----------------\n");
			System.out.print("What will you do (1-5)? : ");
			if (!scanner.hasNextInt()) {
				System.out.println("You must enter a valid integer choice (1-5)!");
			} else {
				oper = scanner.nextInt();
				if (oper >= 1 && oper <= 5) break;
				System.out.println("Invalid choice...try again.");
			}
		}

		switch (oper) {
			case 1: 
				performSet();
				break;
			case 2:
				performGet();
				break;
			case 3:
				performDelete();
				break;
			case 4:
				performLoad();
				break;
			case 5:
				System.out.println("Thanks for using Tejas's hashmap!\n");
				System.exit(1);
		}
	}

	public void performSet() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nPlease enter a String key: ");
		String key = scanner.nextLine();
		System.out.println();

		if (typeChoice == 1) {
			System.out.print("Please enter a String value: ");
			String value = scanner.nextLine();
			hashmap.set(key, value);
		} else {
			System.out.print("Please enter an integer value: ");
			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.print("Not an integer, try again. Please enter an integer value: ");
			}
			hashmap.set(key, scanner.nextInt());
		} 
		
		this.sandbox(typeChoice);
	}

	public void performGet() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nPlease enter a String key: ");
		String key = scanner.nextLine();
		System.out.println();

		if(hashmap.get(key) == null) {
			System.out.println("That key doesn't exist in the map.");
		} else {
			System.out.println("Success! " + key + " maps to -> " + hashmap.get(key));
		}

		this.sandbox(typeChoice);
	}

	public void performDelete() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nPlease enter a String key: ");
		String key = scanner.nextLine();
		System.out.println();

		if(hashmap.get(key) == null) {
			System.out.println("That key doesn't exist in the map.");
		} else {
			System.out.println("Success! " + key + " -> " + hashmap.delete(key) + " was deleted.");
		}

		this.sandbox(typeChoice);
	}

	public void performLoad() {
		System.out.println("Current load factor is: " + hashmap.load());
		this.sandbox(typeChoice);
	}
}