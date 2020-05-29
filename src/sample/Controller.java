package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import sample.model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Pane drawing; // Where we draw
    private Model model; // Will contain shapes

    @FXML
    private RadioButton selectMoveRadio, ellipseRadio, rectangleRadio, lineRadio; // All radio buttons

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button deleteButton, cloneButton;

    private double xMouseMemory, yMouseMemory; // Used to translate a stack of shapes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new Model();

        initPaneListeners();
        initColorPickerListener();
        initRadioShapesListeners();
        initSelectMoveListener();
        initButtonsListeners();

        Rectangle clip = new Rectangle(0, 0, 420, 400); // Cliping so drawings don't go outside of pane
        drawing.setClip(clip);

        colorPicker.setValue(Color.BLACK);
        ellipseRadio.fire(); // Select ellipse per default
        deleteButton.setDisable(true); // By default nothing is selected so this button is disabled
        cloneButton.setDisable(true); // By default nothing is selected so this button is disabled
    }

    /**
     * This method initializes listeners over the drawing pane. There is:
     * - A mouse pressed listener which is used for:
     *      1) If the model is on drawing mod: Create a new shape and draw the "first version" of it.
     *      2) Else save first positions of mouse click in order to track translation.
     *
     * - A mouse dragged listener which is used for:
     *      1) If the model is on drawing mod: Update the shape's size while drawing it.
     *      2) Else move all selected shapes and keep track of movement while mouse is dragged.
     */
    private void initPaneListeners() {
        drawing.setOnMousePressed(mouseEvent -> {
            if (!model.getSelectingMovingOption()) // If we are on drawing option
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
            if (!model.getSelectingMovingOption()) { // If we are on drawing option
                drawing.getChildren().remove(model.getLastShape());
                switch (model.getCurrentDrawingShape()) {
                    case "ellipse":
                        Ellipse e = (Ellipse) model.getLastShape();
                        model.changeSizeOfShapeInCreation(e, Math.abs(e.getCenterX() - mouseEvent.getX()), Math.abs(e.getCenterY() - mouseEvent.getY()));
                        break;
                    case "rectangle":
                        Rectangle r = (Rectangle) model.getLastShape();
                        model.changeSizeOfShapeInCreation(r, mouseEvent.getX(), mouseEvent.getY());
                        break;
                    case "line":
                        model.changeSizeOfShapeInCreation(model.getLastShape(), mouseEvent.getX(), mouseEvent.getY());
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

    /**
     * Listener to color picker to update model and if model is on selection mod, change color of selected shapes.
     */
    private void initColorPickerListener() {
        colorPicker.valueProperty().addListener((observableValue, color, t1) -> {
            model.setCurrentColor(t1);
            if (model.getSelectingMovingOption()) model.changeColorOfAllSelectedShapes();
        });
    }

    /**
     * Add listeners to radio buttons, according to the selected button, it updates the model so it knows which shape to create.
     */
    private void initRadioShapesListeners() {
        ellipseRadio.setOnAction(actionEvent -> model.setCurrentDrawingShape("ellipse"));

        rectangleRadio.setOnAction(actionEvent -> model.setCurrentDrawingShape("rectangle"));

        lineRadio.setOnAction(actionEvent -> model.setCurrentDrawingShape("line"));
    }

    /**
     * Listen move / select button, update model and disable or able delete and clone buttons.
     */
    private void initSelectMoveListener() {
        selectMoveRadio.setOnAction(actionEvent -> {
            model.setSelectingMovingOption(!model.getSelectingMovingOption()); // Switch to move or draw
            if (model.getSelectingMovingOption()) {
                deleteButton.setDisable(false);
                cloneButton.setDisable(false);
            } else {
                deleteButton.setDisable(true);
                cloneButton.setDisable(true);
            }
        });
    }

    /**
     * Delete button is listened so when fired it deletes all selected (in the model) shapes.
     * Clone button, when fired, clone one by one each selected shape with a small offset.
     */
    private void initButtonsListeners() {
        deleteButton.setOnAction(actionEvent -> {
            drawing.getChildren().removeAll(model.getAllSelectedShapes());
            model.deleteAllSelectedShapes();
        });

        cloneButton.setOnAction(actionEvent -> {
            for (Shape s : model.getAllSelectedShapes()) {
                if (s instanceof Ellipse) {
                    Ellipse e = new Ellipse(((Ellipse) s).getCenterX(), ((Ellipse) s).getCenterY(), ((Ellipse) s).getRadiusX(), ((Ellipse) s).getRadiusY());
                    e.setStroke(s.getFill());
                    e.setFill(s.getFill()); // did it on purpose, cloned objects are not selected
                    model.addShape(e);
                    drawing.getChildren().add(e);
                }
                else if (s instanceof Rectangle) {
                    Rectangle r = new Rectangle(((Rectangle) s).getX(), ((Rectangle) s).getY(), ((Rectangle) s).getWidth(), ((Rectangle) s).getHeight());
                    r.setStroke(s.getFill());
                    r.setFill(s.getFill()); // did it on purpose, cloned objects are not selected
                    drawing.getChildren().add(r);
                    model.addShape(r);
                }
                else {
                    Line l = new Line(((Line) s).getStartX(), ((Line) s).getStartY(), ((Line) s).getEndX(), ((Line) s).getEndY());
                    l.setStroke(s.getFill());
                    l.setFill(s.getFill()); // did it on purpose, cloned objects are not selected
                    drawing.getChildren().add(l);
                    model.addShape(l);
                }
            }
            model.moveAllSelectedShapes(5, 5);
        });
    }
}
