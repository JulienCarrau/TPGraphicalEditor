package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Graphical editor");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            System.out.println(keyEvent);
            if (keyEvent.getText().equals("z") && keyEvent.isShortcutDown())
                Controller.manageCtrlZ();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
