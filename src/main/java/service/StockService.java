package service;

import model.Item;

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

    public void registerItem(String name, int quantity, String category) {
        int id = nextId; 

        Item newItem = new Item(id, name, quantity, category); 
        
        nextId++;

        stockList.add(newItem);
        saveToFile();   
    }

    private Item findItemById(int id) {
        for (Item item : stockList) {
            if (item.getId() == id){
                return item;
            }
        }
        return null;
    }

    public boolean updateItemQuantity(int id, int amount, String operation) {
        Item targetItem = findItemById(id);

        if (targetItem == null) {
            return false; 
        }

        int newQuantity;
        if (operation.equals("+")) {
            newQuantity = targetItem.getQuantity() + amount;
        } else {
            newQuantity = targetItem.getQuantity() - amount;
        }

        if (newQuantity < 0) {
            return false; 
        }

        targetItem.setQuantity(newQuantity);
        saveToFile();
        return true; 
    }

    public boolean removeItem(int id) {
        Item targItem = findItemById(id);

        if (targItem == null){
            return false;
        }

        stockList.remove(targItem);
        saveToFile();
        return true;
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write("{\n");
            writer.write("  \"nextId\": " + nextId + ",\n");
            writer.write("  \"items\": [\n");
            
            for (int i = 0; i < stockList.size(); i++) {
                Item item = stockList.get(i);
                writer.write("    {\n");
                writer.write("      \"id\": " + item.getId() + ",\n");
                writer.write("      \"name\": \"" + item.getName() + "\",\n");
                writer.write("      \"quantity\": " + item.getQuantity() + ",\n");
                writer.write("      \"category\": \"" + item.getCategory() + "\"\n"); 
                writer.write("    }");
                
                if (i < stockList.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            
            writer.write("  ]\n");
            writer.write("}");
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

            String content = jsonContent.toString();
            if (content.equals("{}") || content.isEmpty()) return;

            if (content.contains("\"nextId\"")) {
                String[] parts = content.split("\"items\"");
                String metadata = parts[0];
                String nextIdStr = metadata.substring(metadata.indexOf(":") + 1, metadata.indexOf(",")).trim();
                this.nextId = Integer.parseInt(nextIdStr);
            }

            int itemsStartIndex = content.indexOf("[");
            int itemsEndIndex = content.lastIndexOf("]");
            if (itemsStartIndex == -1 || itemsEndIndex == -1) return;
            
            String itemsContent = content.substring(itemsStartIndex + 1, itemsEndIndex).trim();
            if (itemsContent.isEmpty()) return; 

            String[] objects = itemsContent.split("\\},\\s*\\{");
            for (String obj : objects) {
                obj = obj.replace("{", "").replace("}", "");
                String[] fields = obj.split(",");

                int id = 0;
                String name = "";
                int quantity = 0;
                String category = "";

                for (String field : fields) {
                    String[] parts = field.split(":");
                    if (parts.length < 2) continue;
                    
                    String key = parts[0].replace("\"", "").trim();
                    String value = parts[1].replace("\"", "").trim();

                    if (key.equals("id")) id = Integer.parseInt(value);
                    if (key.equals("name")) name = value;
                    if (key.equals("quantity")) quantity = Integer.parseInt(value);
                    if (key.equals("category")) category = value;
                }

                stockList.add(new Item(id, name, quantity, category));
            }
        } catch (Exception e) {
            System.out.println("WARNING: Error parsing file. Resetting safely.");
            nextId = 1;
        }
    }

    public List<Item> getStockList() {
        return this.stockList;
    }
}