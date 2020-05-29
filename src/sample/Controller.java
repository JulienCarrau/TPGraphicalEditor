package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sample.model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Canvas drawing;
    private GraphicsContext cg;
    private Model model; // Will contain shapes
    private double mouseStartX, mouseStartY;

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
        cg = drawing.getGraphicsContext2D(); // Initialize graphic context
        cg.setLineWidth(3); // Set line width

        model = new Model();

        initCanvasListeners();
        initColorPickerListener();
        initRadioShapesListeners();

        colorPicker.setValue(Color.BLACK);
        ellipseRadio.fire(); // Select ellipse per default
    }

    private void initCanvasListeners() {
        drawing.setOnMousePressed(mouseEvent -> {
            mouseStartX = mouseEvent.getX();
            mouseStartY = mouseEvent.getY();
            cg.setStroke(model.getCurrentColor());
        });

        drawing.setOnMouseReleased(mouseEvent -> {
            switch (model.getCurrentShape()) {
                case "ellipse":

                    break;
                case "rectangle":

                    break;
                case "line":
                    cg.strokeLine(mouseStartX, mouseStartY, mouseEvent.getX(), mouseEvent.getY());
                    break;
            }
        });
    }

    private void initColorPickerListener() {
        colorPicker.valueProperty().addListener((observableValue, color, t1) -> model.setCurrentColor(t1));
    }

    private void initRadioShapesListeners() {
        ellipseRadio.setOnAction(actionEvent -> {
            model.setCurrentShape("ellipse");
        });

        rectangleRadio.setOnAction(actionEvent -> {
            model.setCurrentShape("rectangle");
        });

        lineRadio.setOnAction(actionEvent -> {
            model.setCurrentShape("line");
        });
    }
}
