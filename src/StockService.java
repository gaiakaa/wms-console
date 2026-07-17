import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class StockService {
    private final List<Item> stockList = new ArrayList<>();

    public void registerItem(Scanner scanner) {
        System.out.println("\n--- REGISTER NEW ITEM ---");
        
        System.out.print("Enter Item ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        
        System.out.print("Enter Item Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Initial Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();// Limpa o buffer do teclado novamente
        
        System.out.print("Enter Item Category: ");
        String category = scanner.nextLine();
        
        Item newItem = new Item(id, name, quantity, category);
        
        stockList.add(newItem);
        
        System.out.println("\n✅ Item '" + name + "' successfully registered!");
    }
}
