import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class StockService {
    private final List<Item> stockList = new ArrayList<>();

    private int nextId = 1;

    private static final String FILE_PATH = "stock.json";

    public void registerItem(Scanner scanner) {
        System.out.println("\n--- REGISTER NEW ITEM ---");
        
        int id = nextId; 
        System.out.printf("Generated Item ID: %05d%n", id);
        
        //Não deixa o nome ficar vazio
        String name = "";
        while (name.trim().isEmpty()) {
           System.out.print("Enter Item Name: ");
           name = scanner.nextLine(); 
           if(name.trim().isEmpty()) {
            System.out.print("Item name cannot be empty");
           }
        }
        
        //Não aceita numeros negativos e nem 0
        int quantity = 0;
        while(true) {
             System.out.print("Enter Initial Quantity: ");
             if (scanner.hasNextInt()) {
                quantity = scanner.nextInt();
                scanner.nextLine();
                if (quantity >= 0) {
                    break;
                }
                System.out.println("Quantity cannot be negative.");
             } else {
                System.out.println("invalid input! Please enter a valid number!!");
                scanner.nextLine();
             }
        }
       
        
       
        String category = "";
        while (category.trim().isEmpty()) {
            System.out.print("Enter Item Category: ");
            category = scanner.nextLine();
            if (category.trim().isEmpty()) {
                System.out.println("Category cannot be empty.");
            }
        }
        
        Item newItem = new Item(id, name, quantity, category);

        
        stockList.add(newItem);
        saveToFile();
        
        nextId++;
        
        System.out.println("\nItem '" + name + "' successfully registered!");
    }

    public void displayStock() {
        System.out.println("\n--- CURRENT STOCK ---");

        if (stockList.isEmpty()) {
            System.out.println("The stock is currently empty.");
            return;
            }

        System.out.printf("%-7s | %-20s | %-10s | %-15s%n", "ID", "Name", "Quantity", "Category" );
        System.out.println("------------------------------------------------------------");

        for (Item item : stockList) {
            String formattedId = String.format("%05d", item.getId());
            System.out.printf("%-7s | %-20s | %-10d | %-15s%n", formattedId, item.getName(), item.getQuantity(), item.getCategory());
        }
    }

    private Item findItemById(int id) {
        for (Item item : stockList) {
            if (item.getId() == id){
                return item;
            }
        }
        return null;
    }

    public void updateItemQuantity(Scanner scanner, String operation) {

        String title = operation.equals("+") ? "RECORDING ENTRY" : "RECORDING EXIT";
        System.out.println("\n--- " + title + " ---");
       
        displayStock();

        if(stockList.isEmpty()) {
            return;
        }

        int idToFind = 0;
        while (true) {
            System.out.println("\n Enter the ID of the item to update: ");
            if (scanner.hasNextInt()) {
                idToFind = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("invalid input!! Please enter a valid number!!");
                scanner.nextLine();
            }
        }

        Item targetItem = findItemById(idToFind);

        if (targetItem == null) {
            System.out.println("Item with ID " + String.format("%05d", idToFind) + " not found.");
            return;
        }

        System.out.println("Selected Item: " + targetItem.getName() + "(Current Qty: " + targetItem.getQuantity() + ")");

        int newQuantity = 0;
        while (true) {
            System.out.print("Enter Quantity to " + (operation.equals("+") ? "add" : "subtract") + ": ");
          if (scanner.hasNextInt()) {
                int inputQty = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                if (inputQty < 0) {
                    System.out.println("Quantity cannot be negative.");
                    continue;
                }

                if (operation.equals("+")) {
                    newQuantity = targetItem.getQuantity() + inputQty;
                } else {
                    newQuantity = targetItem.getQuantity() - inputQty;
                }

                if (newQuantity < 0) {
                    System.out.println("Operation denied! Stock cannot fall below zero. Current stock is " + targetItem.getQuantity());
                    continue;
                }

                break;
                
            } else {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine();
            }
        }

        targetItem.setQuantity(newQuantity);
        saveToFile();

        System.out.println("\n Quantity for '" + targetItem.getName() + "' successfully updated to " + newQuantity + "!");

        if (newQuantity == 0) {
            System.out.println("ATTENTION: The item ' " + targetItem.getName() + " '  is out of stock!");
        }
    }

    public void removeItem(Scanner scanner) {
        System.out.println("\n--- REMOVE PRODUCT FROM SYSTEM ---");
        displayStock();

        if (stockList.isEmpty()) {
            return;
        }

        int idToFind = 0;
        while (true) {
            System.out.print("\nEnter the ID of the item to REMOVE: ");
            if (scanner.hasNextInt()) {
                idToFind = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                break;
            } else {
                System.out.println("Invalid input! Please enter a valid number!");
                scanner.nextLine();
            }
        }

        Item targetItem = findItemById(idToFind);

        if (targetItem == null) {
            System.out.println("Item with ID " + String.format("%05d", idToFind) + " not found.");
            return;
        }

        stockList.remove(targetItem);
        saveToFile();
        System.out.println("\nItem '" + targetItem.getName() + "' successfully removed from the system!");
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write("[\n");
            for (int i = 0; i < stockList.size(); i++) {
                Item item = stockList.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": " + item.getId() + ",\n");
                writer.write("    \"name\": \"" + item.getName() + "\",\n");
                writer.write("    \"quantity\": " + item.getQuantity() + ",\n");
                writer.write("    \"category\": " + item.getCategory() + "\n");
                writer.write("  }");
                
                if (i < stockList.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]");
        } catch (IOException e) {
            System.out.println("WARNING: Could not save data to file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line.trim());
            }


            String content = jsonContent.toString().replace("[", "").replace("]", "");
            if (content.isEmpty()) return;

            String[] objects = content.split("\\},\\{");
            for (String obj : objects) {
                obj = obj.replace("{", "").replace("}", "");
                String[] fields = obj.split(",");

                int id = 0;
                String name = "";
                int quantity = 0;
                String category = "";

                for (String field : fields) {
                    String[] parts = field.split(":");
                    String key = parts[0].replace("\"", "").trim();
                    String value = parts[1].replace("\"", "").trim();

                    if (key.equals("id")) id = Integer.parseInt(value);
                    if (key.equals("name")) name = value;
                    if (key.equals("quantity")) quantity = Integer.parseInt(value);
                    if (key.equals("category")) category = value;
                }

                stockList.add(new Item(id, name, quantity, category));
                if (id >= nextId) {
                    nextId = id + 1;
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ WARNING: Could not load data from file. Starting with empty stock.");
        }
    }

}
