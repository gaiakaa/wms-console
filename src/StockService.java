import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class StockService {
    private final List<Item> stockList = new ArrayList<>();

    private int nextId = 1;

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

    public void updateItemQuantity(Scanner scanner) {
        System.out.println("\n--- UPDATE ITEM QUANTITY ---");
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
            System.out.print("Enter Quantity to add: ");
            if (scanner.hasNextInt()) {
                newQuantity = targetItem.getQuantity() + scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer
                if (newQuantity >= 0) {
                    break;
                }
                System.out.println("Quantity cannot be negative.");
            } else {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); 
            }
        }
        targetItem.setQuantity(newQuantity);
        System.out.println("\n[OK] Quantity for '" + targetItem.getName() + "' successfully updated to " + newQuantity + "!");

    }
}
