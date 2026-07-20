package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableCell;

import model.Item;
import service.StockService;

import view.MainView;
import view.RegisterItemView;
import view.StockUpdateView;
import view.RemoveItemView;
import view.ConfirmationDialogView;

public class MainController {

    private final MainView view;
    private final StockService stockService;

    public MainController(MainView view) {
        this.view = view;
        this.stockService = new StockService();
        
        this.stockService.loadFromFile();

        initListeners();
        showDashboard();
    }

    private void initListeners() {

        view.getBtnDashboard().setOnAction(e -> showDashboard());
        view.getBtnRegisterItem().setOnAction(e -> showRegisterItemForm());
        view.getBtnStockEntry().setOnAction(e -> showStockUpdateForm("📥 Record Stock Entry", "#3498DB", "+"));
        view.getBtnStockExit().setOnAction(e -> showStockUpdateForm("📤 Record Stock Exit", "#E67E22", "-"));
        view.getBtnRemoveItem().setOnAction(e -> showRemoveItemForm());
        view.getBtnExit().setOnAction(e -> System.exit(0));
    }

    private void showDashboard() {
        VBox centerPanel = view.getCenterPanel();
        centerPanel.getChildren().clear(); 
        centerPanel.setSpacing(15);

        centerPanel.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        Label title = new Label("Current Stock Dashboard");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<Item> table = new TableView<>();
        
        table.setPlaceholder(new Label("No items registered yet."));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        javafx.scene.layout.VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);

        TableColumn<Item, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(100);
        idColumn.setStyle("-fx-alignment: CENTER;");

        idColumn.setCellFactory(column -> new TableCell<Item, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%05d", item));
                }
            }
        });
        
        TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(260);

        TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setPrefWidth(100);
        quantityColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setPrefWidth(160);
        categoryColumn.setStyle("-fx-alignment: CENTER;");

        table.getColumns().add(idColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(quantityColumn);
        table.getColumns().add(categoryColumn);

        ObservableList<Item> itemsData = FXCollections.observableArrayList(stockService.getStockList());
        table.setItems(itemsData);

        centerPanel.getChildren().addAll(title, table);
    }

    private void showRegisterItemForm() {
        VBox centerPanel = view.getCenterPanel();
        centerPanel.getChildren().clear();
        centerPanel.setAlignment(javafx.geometry.Pos.CENTER);

        RegisterItemView registerForm = new RegisterItemView();
        registerForm.getBtnSave().setDefaultButton(true);

        registerForm.getBtnSave().setOnAction(e -> {
            String name = registerForm.getTxtName().getText().trim();
            String quantityStr = registerForm.getTxtQuantity().getText().trim();
            String category = registerForm.getTxtCategory().getText().trim();
            Label msg = registerForm.getLblMessage();

            if (name.isEmpty()) {
                showErrorMessage(msg, "Item name cannot be empty.");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity < 0) {
                    showErrorMessage(msg, "Quantity cannot be negative.");
                    return;
                }
            } catch (NumberFormatException ex) {
                showErrorMessage(msg, "Invalid input! Please enter a valid number.");
                return;
            }

            if (category.isEmpty()) {
                showErrorMessage(msg, "Category cannot be empty.");
                return;
            }

            stockService.registerItem(name, quantity, category);

            registerForm.getTxtName().clear();
            registerForm.getTxtQuantity().clear();
            registerForm.getTxtCategory().clear();

            showSuccessMessage(msg, "Item '" + name + "' successfully registered!");
        });

        centerPanel.getChildren().add(registerForm);
    }

    private void showStockUpdateForm(String title, String buttonColor, String operation) {
        VBox centerPanel = view.getCenterPanel();
        centerPanel.getChildren().clear();
        centerPanel.setAlignment(javafx.geometry.Pos.CENTER);

        StockUpdateView updateForm = new StockUpdateView();

        
        
        updateForm.getLblTitle().setText(title);
        updateForm.getBtnSave().setText(operation.equals("+") ? "📥 Record Entry" : "📤 Record Exit");
        updateForm.getBtnSave().setStyle("-fx-background-color: " + buttonColor + "; -fx-text-fill: white; -fx-font-weight: bold;");
        updateForm.getBtnSave().setDefaultButton(true);

        updateForm.getBtnSave().setOnAction(e -> {
            String idStr = updateForm.getTxtId().getText().trim();
            String quantityStr = updateForm.getTxtQuantity().getText().trim();
            Label msg = updateForm.getLblMessage();

            int idToFind;
            try {
                idToFind = Integer.parseInt(idStr);
            } catch (NumberFormatException ex) {
                showErrorMessage(msg, "Invalid ID! Please enter a valid item ID.");
                return;
            }

            int inputQty;
            try {
                inputQty = Integer.parseInt(quantityStr);
                if (inputQty < 0) {
                    showErrorMessage(msg, "Quantity cannot be negative.");
                    return;
                }
            } catch (NumberFormatException ex) {
                showErrorMessage(msg, "Invalid quantity! Please enter a valid number.");
                return;
            }

            boolean success = stockService.updateItemQuantity(idToFind, inputQty, operation);

            if (success) {
                updateForm.getTxtId().clear();
                updateForm.getTxtQuantity().clear();
                
                showSuccessMessage(msg, "Stock successfully updated!");
            } else {
                showErrorMessage(msg, "Operation denied! Item not found or insufficient stock levels.");
            }
        });

        centerPanel.getChildren().add(updateForm);
    }

    private void showRemoveItemForm() {
        VBox centerPanel = view.getCenterPanel();
        centerPanel.getChildren().clear();
        centerPanel.setAlignment(javafx.geometry.Pos.CENTER);

        RemoveItemView removeForm = new RemoveItemView();
        removeForm.getBtnRemove().setDefaultButton(true);

        removeForm.getBtnRemove().setOnAction(e -> {
            String idStr = removeForm.getTxtId().getText().trim();
            Label msg = removeForm.getLblMessage();

            int idToRemove;
            try {
                idToRemove = Integer.parseInt(idStr);
            } catch (NumberFormatException ex) {
                showErrorMessage(msg, "Invalid ID! Please enter a valid item ID.");
                return;
            }

           
            javafx.stage.Stage mainStage = (javafx.stage.Stage) view.getScene().getWindow();

            
            boolean confirmed = ConfirmationDialogView.show(
                mainStage,
                "Are you sure you want to delete the item with ID " + idStr + "?"
            );

            if (confirmed) {
                boolean success = stockService.removeItem(idToRemove);

                if (success) {
                    removeForm.getTxtId().clear();
                    showSuccessMessage(msg, "Item with ID " + idStr + " successfully removed from the system!");
                } else {
                    showErrorMessage(msg, "Item with ID " + idStr + " not found.");
                }
            } else {
                showErrorMessage(msg, "Deletion canceled by the user.");
            }
        });

        centerPanel.getChildren().add(removeForm);
    }

    private void showErrorMessage(Label label, String message) {
        label.setText(message);
        label.setStyle("-fx-text-fill: #E74C3C; -fx-font-weight: normal;");
    }

    private void showSuccessMessage(Label label, String message) {
        label.setText(message);
        label.setStyle("-fx-text-fill: #2ECC71; -fx-font-weight: bold;");
    }

}