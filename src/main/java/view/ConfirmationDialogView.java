package view; // Ajuste para o seu pacote correto

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfirmationDialogView {

    public static boolean show(Stage ownerStage, String message) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(ownerStage);
        dialog.initStyle(StageStyle.UNDECORATED); 

        Label titleLabel = new Label("Confirm Deletion");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #E74C3C;"); 

        Label msgLabel = new Label(message);
        msgLabel.setStyle("-fx-text-fill: #ECF0F1; -fx-font-size: 13px;");
        msgLabel.setWrapText(true);

        Button btnConfirm = new Button("Confirm");
        btnConfirm.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6px 20px; -fx-background-radius: 5px;");
        
        Button btnCancel = new Button("Cancel");
        btnCancel.setStyle("-fx-background-color: #34495E; -fx-text-fill: #ECF0F1; -fx-padding: 6px 20px; -fx-background-radius: 5px;");

        final boolean[] result = {false};
        
        btnConfirm.setOnAction(e -> {
            result[0] = true;
            dialog.close();
        });
        
        btnCancel.setOnAction(e -> {
            result[0] = false;
            dialog.close();
        });

        HBox actions = new HBox(15, btnConfirm, btnCancel);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(10, 0, 0, 0));

        VBox root = new VBox(15, titleLabel, msgLabel, actions);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #1E272C; -fx-border-color: #34495E; -fx-border-width: 2px; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        root.setPrefWidth(350);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                result[0] = false; // Define como falso (cancelar)
                dialog.close();    // Fecha o modal
            }
        });

        dialog.setScene(scene);
        
        dialog.setOnShown(e -> {
            dialog.setX(ownerStage.getX() + (ownerStage.getWidth() / 2) - (dialog.getWidth() / 2));
            dialog.setY(ownerStage.getY() + (ownerStage.getHeight() / 2) - (dialog.getHeight() / 2));
        });

        dialog.showAndWait();
        return result[0];
    }
}