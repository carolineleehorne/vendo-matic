package com.techelevator.services;

import java.io.BufferedReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    private String vendingMachineCode;
    private String productName;
    private double productPrice;
    private String productCategory;

    public void readInventoryFile() throws IOException {

        File inventoryFile = new File("main.csv");

        if (inventoryFile.exists()) {
            inventoryFile.canRead();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inventoryFile))) {

            String linesInInventoryFile;
            while ((linesInInventoryFile = bufferedReader.readLine()) != null) {

                if (linesInInventoryFile.length() >= 4) {

                    String[] propertiesOfVendingMachineContents = linesInInventoryFile.split(",");

                    vendingMachineCode = propertiesOfVendingMachineContents[0];
                    productName = propertiesOfVendingMachineContents[1];
                    productPrice = Double.parseDouble(propertiesOfVendingMachineContents[2]);
                    productCategory = propertiesOfVendingMachineContents[3];

                }
            }
        }
    }

    public String getVendingMachineCode() {
        return vendingMachineCode;
    }
    public String getProductName() {
        return productName;
    }
    public double getProductPrice() {
        return productPrice;
    }
    public String getProductCategory() {
        return productCategory;
    }
}

