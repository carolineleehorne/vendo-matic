package com.techelevator.services;

import java.text.DecimalFormat;

public class AccountManager {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    private double vendingMachineBalance = 0.00;

    public double getVendingMachineBalance() {
        return vendingMachineBalance;
    }
    public double setVendingMachineBalance() {
        return vendingMachineBalance;
    }

    public void depositMoney(double amount) {
        vendingMachineBalance += amount;
    }

    public void deductFromBalance(double amount) {
        if (amount <= vendingMachineBalance) {
            vendingMachineBalance -= amount;
        } else {
            // You might want to throw an exception or handle the error in some other way
            System.out.println("Insufficient funds!");
        }
    }

    public String dispenseChange() {
        double currentBalance = getVendingMachineBalance();

        int quarters = (int) (currentBalance / 0.25);
        currentBalance = currentBalance % 0.25;

        int dimes = (int) (currentBalance / 0.10);
        currentBalance = currentBalance % 0.10;

        int nickels = (int) (currentBalance / 0.05);

        String formattedBalance = String.format("%.2f", getVendingMachineBalance());
        setVendingMachineBalance();

        return String.format("Your change is $%s: %d quarters, %d dimes, and %d nickels.", formattedBalance, quarters, dimes, nickels);
    }
}