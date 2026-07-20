package view;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class TitleBarView extends HBox {

    private double xOffset = 0;
    private double yOffset = 0;

    private final int border = 8; 
    private Cursor cursorEvent = Cursor.DEFAULT;

    public TitleBarView(Stage stage) {
        this.setStyle("-fx-background-color: #22313F; -fx-padding: 8px 15px; -fx-alignment: CENTER_LEFT; -fx-background-radius: 12px 12px 0px 0px;");
        this.getStyleClass().add("title-bar-custom");

        Label titleLabel = new Label("WMS - Warehouse Management System v1.0.0");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #ECF0F1; -fx-font-size: 12px;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        String btnStyle = "-fx-background-color: transparent; -fx-text-fill: #ECF0F1; -fx-font-weight: bold; -fx-padding: 4px 12px; -fx-background-radius: 10px; -fx-font-size: 12px;";
        
        Button btnMinimize = new Button("—");
        btnMinimize.setStyle(btnStyle);
        btnMinimize.setOnMouseEntered(e -> btnMinimize.setStyle(btnStyle + "-fx-background-color: #34495E;"));
        btnMinimize.setOnMouseExited(e -> btnMinimize.setStyle(btnStyle));
        btnMinimize.setOnAction(e -> stage.setIconified(true));

        Button btnMaximize = new Button("🗖");
        btnMaximize.setStyle(btnStyle);
        btnMaximize.setOnMouseEntered(e -> btnMaximize.setStyle(btnStyle + "-fx-background-color: #34495E;"));
        btnMaximize.setOnMouseExited(e -> btnMaximize.setStyle(btnStyle));
        btnMaximize.setOnAction(e -> toggleMaximize(stage, btnMaximize));
        
        Button btnClose = new Button("✕");
        btnClose.setStyle(btnStyle);
        btnClose.setOnMouseEntered(e -> btnClose.setStyle(btnStyle + "-fx-background-color: #E74C3C; -fx-text-fill: white;"));
        btnClose.setOnMouseExited(e -> btnClose.setStyle(btnStyle));
        btnClose.setOnAction(e -> System.exit(0));
        
        HBox windowControls = new HBox(2);
        windowControls.setAlignment(Pos.CENTER);
        windowControls.getChildren().addAll(btnMinimize, btnMaximize, btnClose);
        
        this.getChildren().addAll(titleLabel, spacer, windowControls);
        
        setupDragEvents(stage);

        this.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventHandler(MouseEvent.MOUSE_MOVED, e -> handleMouseMoved(stage, e));
                newScene.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> handleMouseDragged(stage, e));
            }
        });
    }

    private void toggleMaximize(Stage stage, Button btnMaximize) {
        if (stage.isMaximized()) {
            stage.setMaximized(false);
            btnMaximize.setText("🗖");
        } else {
            stage.setMaximized(true);
            btnMaximize.setText("🗗");
        }
    }

    private void setupDragEvents(Stage stage) {
        this.setOnMousePressed(event -> {
            if (!stage.isMaximized()) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        
        this.setOnMouseDragged(event -> {
            if (!stage.isMaximized()) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        
        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Button btnMax = (Button) ((HBox) this.getChildren().get(2)).getChildren().get(1);
                toggleMaximize(stage, btnMax);
            }
        });
    }

    //tem alguma coisa errada aqui, não sei oq é mas tem
   private void handleMouseMoved(Stage stage, MouseEvent mouseEvent) {
        if (stage.isMaximized()) {
            stage.getScene().setCursor(Cursor.DEFAULT);
            return;
        }

        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        double width = stage.getScene().getWidth();
        double height = stage.getScene().getHeight();

        boolean borderLeft = x <= border;
        boolean borderRight = x >= width - border;
        boolean borderTop = y <= border;
        boolean borderBottom = y >= height - border;

        if (borderLeft && borderTop) {
            cursorEvent = Cursor.NW_RESIZE;
        } else if (borderLeft && borderBottom) {
            cursorEvent = Cursor.SW_RESIZE;
        } else if (borderRight && borderTop) {
            cursorEvent = Cursor.NE_RESIZE;
        } else if (borderRight && borderBottom) {
            cursorEvent = Cursor.SE_RESIZE;
        } else if (borderLeft) {
            cursorEvent = Cursor.W_RESIZE;
        } else if (borderRight) {
            cursorEvent = Cursor.E_RESIZE;
        } else if (borderTop) {
            cursorEvent = Cursor.N_RESIZE;
        } else if (borderBottom) {
            cursorEvent = Cursor.S_RESIZE;
        } else {
            cursorEvent = Cursor.DEFAULT;
        }
        
        stage.getScene().setCursor(cursorEvent);
    }

    private void handleMouseDragged(Stage stage, MouseEvent mouseEvent) {
        if (stage.isMaximized() || cursorEvent == Cursor.DEFAULT) {
            return;
        }

        double mouseX = mouseEvent.getScreenX();
        double mouseY = mouseEvent.getScreenY();
        
        double currentX = stage.getX();
        double currentY = stage.getY();
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();

        if (cursorEvent == Cursor.E_RESIZE || cursorEvent == Cursor.SE_RESIZE || cursorEvent == Cursor.NE_RESIZE) {
            double newWidth = mouseEvent.getX() + border;
            if (newWidth >= stage.getMinWidth()) {
                stage.setWidth(newWidth);
            }
        } else if (cursorEvent == Cursor.W_RESIZE || cursorEvent == Cursor.NW_RESIZE || cursorEvent == Cursor.SW_RESIZE) {
            double newWidth = currentX + currentWidth - mouseX;
            if (newWidth >= stage.getMinWidth()) {
                stage.setX(mouseX);
                stage.setWidth(newWidth);
            }
        }

        if (cursorEvent == Cursor.S_RESIZE || cursorEvent == Cursor.SE_RESIZE || cursorEvent == Cursor.SW_RESIZE) {
            double newHeight = mouseEvent.getY() + border;
            if (newHeight >= stage.getMinHeight()) {
                stage.setHeight(newHeight);
            }
        } else if (cursorEvent == Cursor.N_RESIZE || cursorEvent == Cursor.NW_RESIZE || cursorEvent == Cursor.NE_RESIZE) {
            double newHeight = currentY + currentHeight - mouseY;
            if (newHeight >= stage.getMinHeight()) {
                stage.setY(mouseY);
                stage.setHeight(newHeight);
            }
        }
    }
}