package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RegisterItemView extends VBox {

    private final TextField txtName;
    private final TextField txtQuantity;
    private final TextField txtCategory;
    private final Button btnSave;
    private final Label lblMessage;

    public RegisterItemView() {
        this.setSpacing(15);
        this.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("Register New Item");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(10, 0, 10, 0));

        Label lblName = new Label("Item Name:");
        txtName = new TextField();
        txtName.setPromptText("Enter item name");

        Label lblQuantity = new Label("Initial Quantity:");
        txtQuantity = new TextField();
        txtQuantity.setPromptText("Enter initial quantity");

        Label lblCategory = new Label("Item Category:");
        txtCategory = new TextField();
        txtCategory.setPromptText("Enter item category");

        grid.add(lblName, 0, 0);
        grid.add(txtName, 1, 0);
        grid.add(lblQuantity, 0, 1);
        grid.add(txtQuantity, 1, 1);
        grid.add(lblCategory, 0, 2);
        grid.add(txtCategory, 1, 2);

        btnSave = new Button("💾 Save Item");
        btnSave.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white; -fx-font-weight: bold;");
        
        lblMessage = new Label();

        this.getChildren().addAll(title, grid, btnSave, lblMessage);
    }

    public TextField getTxtName() { return txtName; }
    public TextField getTxtQuantity() { return txtQuantity; }
    public TextField getTxtCategory() { return txtCategory; }
    public Button getBtnSave() { return btnSave; }
    public Label getLblMessage() { return lblMessage; }
}