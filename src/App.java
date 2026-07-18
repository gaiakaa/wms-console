import java.util.Scanner;

public class App {
   
    enum AppState{
        MAIN_MENU,
        REGISTERING_ITEM,
        RECORDING_ENTRY,
        RECORDING_EXIT,
        DISPLAYING_STOCK,
        TERMINATED
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        StockService stockService = new StockService();

        AppState currentState = AppState.MAIN_MENU;

        System.out.println("=========================================");
        System.out.println("        WMS - CONSOLE APPLICATION        ");
        System.out.println("=========================================");

        while (currentState != AppState.TERMINATED) {

            switch (currentState) {
                case MAIN_MENU:
                    currentState = processMainMenu(scanner);
                    break;

                 case REGISTERING_ITEM:
                    stockService.registerItem(scanner);
                    currentState = AppState.MAIN_MENU;
                   break;

                case RECORDING_ENTRY:
                    System.out.println("\n--- [State: recording Stock Entry] ---");
                    System.out.println("Feature under development....");
                    currentState = AppState.MAIN_MENU;
                    break;
                    
                case RECORDING_EXIT:
                    System.out.println("\n---[State: Recording Exit] ---");
                    System.out.println("Feature under development....");
                    currentState = AppState.MAIN_MENU;
                    break;

                case DISPLAYING_STOCK:
                    
                    stockService.displayStock();
                    currentState = AppState.MAIN_MENU;
                    break;
            
                default:
                    currentState = AppState.MAIN_MENU;
                    break;
            }
            
        }

        System.out.println("\nExiting system... GoodBye !");
        scanner.close();
    }

    private static AppState processMainMenu(Scanner scanner) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Register New Item");
            System.out.println("2. Record Stock Entry");
            System.out.println("3. Record Stock Exit");
            System.out.println("4. Display Complete Stock");
            System.out.println("5. Exit System");
            System.out.println("Your Option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    return AppState.REGISTERING_ITEM;
                case "2":
                    return AppState.RECORDING_ENTRY;
                case "3":
                    return AppState.RECORDING_EXIT;
                case "4":
                    return AppState.DISPLAYING_STOCK;
                case "5":
                    return AppState.TERMINATED;
            
                default:
                    System.out.println("\n[Error] Invalid option!! Try again.");
                    return AppState.MAIN_MENU;
            }
        }   
}
