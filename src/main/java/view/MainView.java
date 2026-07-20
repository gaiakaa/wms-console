package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends BorderPane {

    private VBox centerPanel;
    private VBox sidebarMenu;
    private Button btnDashboard;
    private Button btnRegisterItem;
    private Button btnStockEntry;
    private Button btnStockExit;
    private Button btnRemoveItem;
    private Button btnExit;
        
    public MainView() {
        initSidebar();
        initCenterPanel();
    }

    private void initSidebar() {
        sidebarMenu = new VBox();
        sidebarMenu.getStyleClass().add("sidebar");
        sidebarMenu.setPadding(new Insets(20));
        sidebarMenu.setSpacing(15);
        sidebarMenu.setPrefWidth(200);

        Label menuTitle = new Label("WMS - Navigation");
        menuTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        menuTitle.setMaxWidth(Double.MAX_VALUE); 
        menuTitle.setAlignment(Pos.CENTER);

        btnDashboard = new Button("Dashboard");
        btnRegisterItem = new Button("Register Item");
        btnStockEntry = new Button("Stock Entry");  
        btnStockExit = new Button("Stock Exit");
        btnRemoveItem = new Button("Remove Item");
        btnExit = new Button("Exit System");


        btnDashboard.setMaxWidth(Double.MAX_VALUE);
        btnRegisterItem.setMaxWidth(Double.MAX_VALUE);
        btnStockEntry.setMaxWidth(Double.MAX_VALUE);
        btnStockExit.setMaxWidth(Double.MAX_VALUE);
        btnRemoveItem.setMaxWidth(Double.MAX_VALUE);
        btnExit.setMaxWidth(Double.MAX_VALUE);

        Region footerSpacer = new Region();
        VBox.setVgrow(footerSpacer, Priority.ALWAYS);
        
        Label watermark = new Label("Developed by @gaiakaa");
        watermark.getStyleClass().add("watermark-text");
        
        watermark.setMaxWidth(Double.MAX_VALUE);
        watermark.setAlignment(Pos.BOTTOM_CENTER); 

        sidebarMenu.getChildren().addAll(menuTitle, btnDashboard, btnRegisterItem, btnStockEntry, btnStockExit, btnRemoveItem, btnExit, footerSpacer, watermark);
        this.setLeft(sidebarMenu);
    }

    private void initCenterPanel() {
        centerPanel = new VBox();
        centerPanel.setPadding(new Insets(20));
        centerPanel.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome to Warehouse Management System");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #333333;");
        
        centerPanel.getChildren().add(welcomeLabel);
        this.setCenter(centerPanel);
    }

    public void bindTitleBar(Stage stage) {
        TitleBarView titleBar = new TitleBarView(stage);
        this.setTop(titleBar);
    }

    public Button getBtnDashboard() { return btnDashboard; }
    public Button getBtnRegisterItem() { return btnRegisterItem; }
    public Button getBtnStockEntry() { return btnStockEntry; }
    public Button getBtnStockExit() { return btnStockExit; }
    public Button getBtnRemoveItem() { return btnRemoveItem; }
    public Button getBtnExit() { return btnExit; }
    public VBox getCenterPanel() { return centerPanel; }
}