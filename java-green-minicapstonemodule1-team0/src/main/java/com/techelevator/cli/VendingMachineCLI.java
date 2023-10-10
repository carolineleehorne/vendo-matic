package com.techelevator.cli;

import com.techelevator.model.Product;
import com.techelevator.services.AccountManager;
import com.techelevator.services.InventoryManager;
import com.techelevator.services.LogManager;

import java.io.IOException;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String DISPLAY_ITEMS = "1";
	private static final String PURCHASE = "2";
	private static final String EXIT = "3";
	private static final String FEED_MONEY = "1";
	private static final String SELECT_PRODUCT = "2";
	private static final String FINISH_TRANSACTION = "3";

	private static AccountManager accountManager = new AccountManager();
	private static InventoryManager inventoryManager = new InventoryManager();
	private static LogManager logManager;
	private static int bogodoCounter = 0;


	static {
		try {
			logManager = new LogManager();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner userInput = new Scanner(System.in);

		// Initialize
		inventoryManager.setVendingMachineInventoryMap("main.csv"); // Change this to your main.csv path for the program to run correctly.
		inventoryManager.getVendingMachineInventoryMap();

		boolean vendingMachineRunning = true;
		while (vendingMachineRunning) {
			displayMainMenu();
			String mainMenuSelection = userInput.nextLine();

			switch(mainMenuSelection) {
				case DISPLAY_ITEMS:
					inventoryManager.displayChosenMapData();
					break;
				case PURCHASE:
					handlePurchaseMenu(userInput);
					break;
				case EXIT:
					vendingMachineRunning = false;
					break;
				default:
					System.out.println("Invalid choice, please try again.");
					break;
			}
		}

		userInput.close();
	}

	private static void displayMainMenu() {
		System.out.println("\nPlease select an option :\n");
		System.out.println("(1) Display Vending Machine Items");
		System.out.println("(2) Purchase");
		System.out.println("(3) Exit\n");
	}

	private static void displayPurchaseMenu() {
		System.out.println("\n(1) Feed Money");
		System.out.println("(2) Select Product");
		System.out.println("(3) Finish Transaction\n");
	}

	private static void handlePurchaseMenu(Scanner userInput) throws IOException {
		boolean insidePurchaseMenu = true;
		while (insidePurchaseMenu) {
			displayPurchaseMenu();
			String purchaseMenuSelection = userInput.nextLine();

			switch(purchaseMenuSelection) {
				case FEED_MONEY:
					System.out.println("\nHow much money would you like to deposit?");
					double depositAmount = Double.parseDouble(userInput.nextLine());
					accountManager.depositMoney(depositAmount);
					System.out.println("Your current balance is $" + String.format("%.2f", accountManager.getVendingMachineBalance()));
					logManager.logFeedMoney(depositAmount, accountManager.getVendingMachineBalance());
					break;
				case SELECT_PRODUCT:
					handleProductSelection(userInput);
					break;
				case FINISH_TRANSACTION:
					String changeMessage = accountManager.dispenseChange();
					System.out.println(changeMessage);
					logManager.logChange(accountManager.getVendingMachineBalance(), 0);
					insidePurchaseMenu = false;
					break;
				default:
					System.out.println("Invalid choice, please try again.");
					break;
			}
		}
	}

	private static void handleProductSelection(Scanner userInput) throws IOException {
		inventoryManager.displayChosenMapData();
		System.out.println("\nPlease make a selection: ");
		String selection = userInput.nextLine().trim().toUpperCase();

		Product product = (Product) inventoryManager.getVendingMachineInventoryMap().get(selection);

		if (product.isSoldOut()) {
			System.out.println("SELECTION IS SOLD OUT");
		} else {
			double itemPrice = product.getItemPrice();
			if (itemPrice > accountManager.getVendingMachineBalance()) {
				System.out.println("Insufficient funds. Please insert more money to continue with your purchase");
			} else {
				bogodoCounter++;
				if (bogodoCounter % 2 == 0) {
					System.out.println("BOGODO! You get a $1 off!");
					itemPrice -= 1.00;
				}
				inventoryManager.dispenseItem(selection);
				accountManager.deductFromBalance(itemPrice);
				System.out.println("Your current balance is now $" + String.format("%.2f", accountManager.getVendingMachineBalance()));
				logManager.logPurchase(product.getItemName(), selection, itemPrice, accountManager.getVendingMachineBalance());
			}
		}
	}

}