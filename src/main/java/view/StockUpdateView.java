package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class StockUpdateView extends VBox {

    private final Label lblTitle;
    private final TextField txtId;
    private final TextField txtQuantity;
    private final Button btnSave;
    private final Label lblMessage;

    public StockUpdateView() {
        this.setSpacing(15);
        this.setAlignment(Pos.TOP_LEFT);

        
        lblTitle = new Label("Stock Management");
        lblTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(10, 0, 10, 0));

        Label lblId = new Label("Item ID:");
        txtId = new TextField();
        txtId.setPromptText("Enter item ID");

        Label lblQuantity = new Label("Quantity:");
        txtQuantity = new TextField();
        txtQuantity.setPromptText("Enter quantity");

        grid.add(lblId, 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(lblQuantity, 0, 1);
        grid.add(txtQuantity, 1, 1);

        btnSave = new Button("Execute");
        btnSave.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        lblMessage = new Label();

        this.getChildren().addAll(lblTitle, grid, btnSave, lblMessage);
    }

    public Label getLblTitle() { return lblTitle; }
    public TextField getTxtId() { return txtId; }
    public TextField getTxtQuantity() { return txtQuantity; }
    public Button getBtnSave() { return btnSave; }
    public Label getLblMessage() { return lblMessage; }
}