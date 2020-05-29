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

    private double xMouseMemory, yMouseMemory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new Model();

        initPaneListeners();
        initColorPickerListener();
        initRadioShapesListeners();
        initSelectMoveListener();

        colorPicker.setValue(Color.BLACK);
        ellipseRadio.fire(); // Select ellipse per default
    }

    private void initPaneListeners() {
        drawing.setOnMousePressed(mouseEvent -> {
            if (!model.getMove()) // If we are on drawing option
                switch (model.getCurrentDrawingShape()) {
                    case "ellipse":
                        Ellipse e = new Ellipse(mouseEvent.getX(), mouseEvent.getY(), 1, 1);
                        e.setStroke(model.getCurrentColor());
                        e.setFill(model.getCurrentColor());
                        drawing.getChildren().add(e);
                        model.addShape(e);
                        break;
                    case "rectangle":
                        Rectangle r = new Rectangle(mouseEvent.getX(), mouseEvent.getY(), 1, 1);
                        r.setStroke(model.getCurrentColor());
                        r.setFill(model.getCurrentColor());
                        drawing.getChildren().add(r);
                        model.addShape(r);
                        break;
                    case "line":
                        Line l = new Line(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
                        l.setStroke(model.getCurrentColor());
                        l.setFill(model.getCurrentColor());
                        drawing.getChildren().add(l);
                        model.addShape(l);
                        break;
                }
            else { // To move shapes together we need to save old mouse positions
                xMouseMemory = mouseEvent.getX();
                yMouseMemory = mouseEvent.getY();
            }
        });

        drawing.setOnMouseDragged(mouseEvent -> {
            if (!model.getMove()) { // If we are on drawing option
                drawing.getChildren().remove(model.getLastShape());
                switch (model.getCurrentDrawingShape()) {
                    case "ellipse":
                        Ellipse e = (Ellipse) model.getLastShape();
                        model.updateShape(e, Math.abs(e.getCenterX() - mouseEvent.getX()), Math.abs(e.getCenterY() - mouseEvent.getY()));
                        break;
                    case "rectangle":
                        Rectangle r = (Rectangle) model.getLastShape();
                        model.updateShape(r, mouseEvent.getX(), mouseEvent.getY());
                        break;
                    case "line":
                        model.updateShape(model.getLastShape(), mouseEvent.getX(), mouseEvent.getY());
                        break;
                }
                drawing.getChildren().add(model.getLastShape());
            }
            else { // If we are on moving option
                model.moveAllSelectedShapes(mouseEvent.getX() - xMouseMemory, mouseEvent.getY() - yMouseMemory);
                xMouseMemory = mouseEvent.getX();
                yMouseMemory = mouseEvent.getY();
            }
        });
    }

    private void initColorPickerListener() {
        colorPicker.valueProperty().addListener((observableValue, color, t1) -> {
            model.setCurrentColor(t1);
            if (model.getMove()) model.changeColorOfAllSelectedShapes();
        });
    }

    private void initRadioShapesListeners() {
        ellipseRadio.setOnAction(actionEvent -> {
            model.setCurrentDrawingShape("ellipse");
        });

        rectangleRadio.setOnAction(actionEvent -> {
            model.setCurrentDrawingShape("rectangle");
        });

        lineRadio.setOnAction(actionEvent -> {
            model.setCurrentDrawingShape("line");
        });
    }

    private void initSelectMoveListener() {
        selectMoveRadio.setOnAction(actionEvent -> {
            model.setMove(!model.getMove()); // Switch to move or draw
        });
    }
}
