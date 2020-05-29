package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import sample.model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Pane drawing;
    private Model model; // Will contain shapes

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
        model = new Model();

        initPaneListeners();
        initColorPickerListener();
        initRadioShapesListeners();

        colorPicker.setValue(Color.BLACK);
        ellipseRadio.fire(); // Select ellipse per default
    }

    private void initPaneListeners() {
        drawing.setOnMousePressed(mouseEvent -> {
            switch (model.getCurrentShape()) {
                case "ellipse":
                    Ellipse e = new Ellipse(mouseEvent.getX(), mouseEvent.getY(), 1, 1);
                    e.setStroke(model.getCurrentColor());
                    e.setFill(model.getCurrentColor());
                    drawing.getChildren().add(e);
                    model.addEllipse(e);
                    break;
                case "rectangle":
                    Rectangle r = new Rectangle(mouseEvent.getX(), mouseEvent.getY(), 1, 1);
                    r.setStroke(model.getCurrentColor());
                    r.setFill(model.getCurrentColor());
                    drawing.getChildren().add(r);
                    model.addRectangle(r);
                    break;
                case "line":
                    Line l = new Line(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
                    l.setStroke(model.getCurrentColor());
                    l.setFill(model.getCurrentColor());
                    drawing.getChildren().add(l);
                    model.addLine(l);
                    break;
            }
        });

        drawing.setOnMouseDragged(mouseEvent -> {
            switch (model.getCurrentShape()) {
                case "ellipse":
                    drawing.getChildren().remove(model.getLastEllipse());
                    Ellipse e = model.getLastEllipse();
                    model.updateEllipse(e, Math.abs(e.getCenterX() - mouseEvent.getX()), Math.abs(e.getCenterY() - mouseEvent.getY()));
                    drawing.getChildren().add(e);
                    break;
                case "rectangle":
                    drawing.getChildren().remove(model.getLastRectangle());
                    Rectangle r = model.getLastRectangle();
                    model.updateRectangle(r, mouseEvent.getX(), mouseEvent.getY());
                    drawing.getChildren().add(r);
                    break;
                case "line":
                    drawing.getChildren().remove(model.getLastLine());
                    model.updateLine(model.getLastLine(), mouseEvent.getX(), mouseEvent.getY());
                    drawing.getChildren().add(model.getLastLine());
                    break;
            }
        });
    }

    private void initColorPickerListener() {
        colorPicker.valueProperty().addListener((observableValue, color, t1) -> {
            model.setCurrentColor(t1);
        });
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
