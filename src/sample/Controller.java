package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Canvas drawing;

    @FXML
    private RadioButton selectMoveRadio, ellipseRadio, rectangleRadio, lineRadio;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button deleteButton, cloneButton;

    @FXML
    private ToggleGroup group; // unused

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ellipseRadio.fire(); // Select ellipse per default


    }
}
