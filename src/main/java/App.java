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

        primaryStage.initStyle(javafx.stage.StageStyle.UNDECORATED);

        Scene scene = new Scene(mainView, 800, 550, javafx.scene.paint.Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        mainView.bindTitleBar(primaryStage);
        primaryStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
        
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(780);
        primaryStage.setMinHeight(400);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}