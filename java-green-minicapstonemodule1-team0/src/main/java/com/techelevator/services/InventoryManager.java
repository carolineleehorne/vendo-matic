package com.techelevator.services;

import com.techelevator.model.Buyable;
import com.techelevator.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    private static Map<String, Buyable> vendingMachineInventoryMap = new HashMap<>();


    public Map<String, Buyable> getVendingMachineInventoryMap() {
        return vendingMachineInventoryMap;
    }
    public void displayChosenMapData() {
        for(Map.Entry<String, Buyable> item : vendingMachineInventoryMap.entrySet()) {
            System.out.println(item.getKey() + " - " +  item.getValue().getItemName() + " - $" + item.getValue().getItemPrice() + " - " + item.getValue().getItemType() + " - " + item.getValue().getItemQuantity() + " available");
        }
    }

    public void dispenseItem(String selection) {

        Product product = (Product) vendingMachineInventoryMap.get(selection);

        if (product.isSoldOut()) {
            System.out.println("SOLD OUT");
        }
        else {
            System.out.print("Dispensing: " + product.getItemName() + " - $" + product.getItemPrice() + " - ");
            if (product instanceof Gum) {
                System.out.println("Chew Chew, Yum!");
            } else if (product instanceof Candy) {
                System.out.println("Yummy Yummy, So Sweet!");
            } else if (product instanceof Munchy) {
                System.out.println("Crunch Crunch, Yum!");
            } else if (product instanceof Drink) {
                System.out.println("Glug Glug, Yum!");
            }
            product.decreaseQuantityCounter();
        }
    }

    public Map<String, Buyable> setVendingMachineInventoryMap(String inventoryFromCsvFile) throws FileNotFoundException {
        try (BufferedReader newBufferedReader = new BufferedReader(new FileReader(inventoryFromCsvFile))) {
            String lineInFile;

            while (((lineInFile = newBufferedReader.readLine()) != null)) {

                String[] linesSeperatedByCommas = lineInFile.split(",");

                String slotNumber = linesSeperatedByCommas[0].trim();
                String itemName = linesSeperatedByCommas[1];
                double itemPrice = Double.parseDouble(linesSeperatedByCommas[2]);
                String itemType = linesSeperatedByCommas[3];
                int itemQuantity = 5;

                Buyable currentProduct;

                switch (itemType) {
                    case "Gum":
                        currentProduct = new Gum(slotNumber, itemName, itemType, itemPrice, itemQuantity);
                        break;
                    case "Candy":
                        currentProduct = new Candy(slotNumber, itemName, itemType, itemPrice, itemQuantity);
                        break;
                    case "Munchy":
                        currentProduct = new Munchy(slotNumber, itemName, itemType, itemPrice, itemQuantity);
                        break;
                    case "Drink":
                        currentProduct = new Drink(slotNumber, itemName, itemType, itemPrice, itemQuantity);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + itemType);
                }

                vendingMachineInventoryMap.put(slotNumber, currentProduct);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vendingMachineInventoryMap;
    }

}