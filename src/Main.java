package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("" +
                "sample.fxml"));
        src.Controller controller = new src.Controller();
        loader.setController(controller);
        Parent root = loader.load();
        primaryStage.setTitle("PhoneBook Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
