package com.techelevator.services;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LogManager {
    private FileWriter writer;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public LogManager() throws IOException {
        this.writer = new FileWriter("Log.txt", true);
    }

    public void logFeedMoney(double depositAmount, double balance) throws IOException {
        String logMessage = String.format("%s %s FEED MONEY: $%.2f $%.2f\n",
                LocalDate.now().format(DATE_FORMATTER),
                LocalTime.now().format(TIME_FORMATTER),
                depositAmount,
                balance);

        writer.write(logMessage);
        writer.flush();
    }

    public void logPurchase(String itemName, String slotNumber, double itemPrice, double balance) throws IOException {
        String logMessage = String.format("%s %s %s %s $%.2f $%.2f\n",
                LocalDate.now().format(DATE_FORMATTER),
                LocalTime.now().format(TIME_FORMATTER),
                itemName,
                slotNumber,
                itemPrice,
                balance);

        writer.write(logMessage);
        writer.flush();
    }

    public void logChange(double previousBalance, double newBalance) throws IOException {
        String logMessage = String.format("%s %s GIVE CHANGE: $%.2f $%.2f\n",
                LocalDate.now().format(DATE_FORMATTER),
                LocalTime.now().format(TIME_FORMATTER),
                previousBalance,
                newBalance);

        writer.write(logMessage);
        writer.flush();
    }
}