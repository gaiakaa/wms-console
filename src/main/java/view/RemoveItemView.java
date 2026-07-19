package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RemoveItemView extends VBox {

    private final TextField txtId;
    private final Button btnRemove;
    private final Label lblMessage;

    public RemoveItemView() {
        this.setSpacing(15);
        this.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("Remove Product from System");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(10, 0, 10, 0));

        Label lblId = new Label("Enter Item ID to REMOVE:");
        txtId = new TextField();
        txtId.setPromptText("Enter item ID");

        grid.add(lblId, 0, 0);
        grid.add(txtId, 1, 0);

        btnRemove = new Button("Remove Product");
        btnRemove.setStyle("-fx-background-color: #C0392B; -fx-text-fill: white; -fx-font-weight: bold;");
        
        lblMessage = new Label();

        this.getChildren().addAll(title, grid, btnRemove, lblMessage);
    }

    public TextField getTxtId() { return txtId; }
    public Button getBtnRemove() { return btnRemove; }
    public Label getLblMessage() { return lblMessage; }
}