import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainView;
import controller.MainController;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        MainView mainView = new MainView();

        new MainController(mainView);

        Scene scene = new Scene(mainView, 800, 550);
        
        primaryStage.setTitle("WMS - Warehouse Management System v1.0.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}