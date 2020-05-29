package sample.model;

import javafx.animation.ScaleTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import sample.model.commands.Move;

import java.util.ArrayList;

public class Model implements IModel {
    private ArrayList<Shape> shapes;
    private ArrayList<Shape> selectedShapes;
    private String currentDrawingShape;
    private Color currentColor;
    private boolean moveOption; // if true, we only move shapes, else we can draw new ones
    private Move move;

    /**
     * Model constructor.
     */
    public Model() {
        shapes = new ArrayList<>();
        selectedShapes = new ArrayList<>();
        moveOption = false;
    }

    /**
     * Set model's color.
     * @param c New model's color.
     */
    @Override
    public void setCurrentColor(Color c) {
        currentColor = c;
    }

    /**
     * Get model's color.
     * @return Model's color.
     */
    @Override
    public Color getCurrentColor() {
        return currentColor;
    }

    /**
     * Set model's current drawing shape, so it knows which shape to draw when user click on the drawing space.
     * @param s New Model's shape, can be : line, ellipse or rectangle.
     */
    @Override
    public void setCurrentDrawingShape(String s) {
        currentDrawingShape = s;
    }

    /**
     * Get model's current drawing shape.
     * @return Model's current drawing shape.
     */
    @Override
    public String getCurrentDrawingShape() {
        return currentDrawingShape;
    }

    /**
     * Add a new shape in the model.
     * A listener is added to know when this shape is selected or unselected.
     * @param s Shape to add to the model.
     */
    @Override
    public void addShape(Shape s) {
        shapes.add(s);
        s.setOnMouseClicked(mouseEvent -> {
            if (moveOption) {
                if (!selectedShapes.contains(s)) selectShape(s);
                else unSelectShape(s);
            }
        });
    }

    /**
     * Change shape's stroke and do a little animation to notify the user that this shape has been selected.
     * @param s Selected shape.
     */
    private void selectShape(Shape s) {
        s.setStroke(Color.LIGHTGREEN);

        selectedShapes.add(s);

        ScaleTransition selectAnimation = new ScaleTransition(Duration.seconds(0.1), s);
        selectAnimation.setAutoReverse(true);
        selectAnimation.setCycleCount(2);
        selectAnimation.setToX(1.1);
        selectAnimation.setToY(1.1);
        selectAnimation.play();
    }

    /**
     * Set shape's stroke like its filling and do a little animation to notify the user that this shape has been unselected.
     * @param s Unselected shape.
     */
    private void unSelectShape(Shape s) {
        s.setStroke(s.getFill());
        selectedShapes.remove(s);

        ScaleTransition unSelectAnimation = new ScaleTransition(Duration.seconds(0.1), s);
        unSelectAnimation.setAutoReverse(true);
        unSelectAnimation.setCycleCount(2);
        unSelectAnimation.setToX(0.9);
        unSelectAnimation.setToY(0.9);
        unSelectAnimation.play();
    }

    /**
     * When user is drawing a new shape, while he's dragging on the drawing zone, we keep updating the preview.
     * @param s Shape to draw.
     * @param endX Last X position of the mouse.
     * @param endY Last Y position of the mouse.
     */
    @Override
    public void changeSizeOfShapeInCreation(Shape s, double endX, double endY) {
        switch (currentDrawingShape) {
            case "ellipse":
                ((Ellipse) s).setRadiusX(endX);
                ((Ellipse) s).setRadiusY(endY);
                break;
            case "rectangle":
                if (endX > ((Rectangle) s).getX()) ((Rectangle) s).setWidth(Math.abs(((Rectangle) s).getX() - endX));
                //else r.setX(Math.abs(r.getX() - endX));
                if (endY > ((Rectangle) s).getY()) ((Rectangle) s).setHeight(Math.abs(((Rectangle) s).getY() - endY));
                //else r.setY(Math.abs(r.getY() - endY));
                break;
            case "line":
                ((Line) s).setEndX(endX);
                ((Line) s).setEndY(endY);
                break;
        }
    }

    /**
     * Get all selected shapes.
     * @return An array containing selected shapes.
     */
    @Override
    public ArrayList<Shape> getAllSelectedShapes() {
        return selectedShapes;
    }

    /**
     * Get last created shape (often use while creating it)
     * @return Latest shape.
     */
    @Override
    public Shape getLastShape() {
        return shapes.get(shapes.size() - 1);
    }

    /**
     * The model can be in drawing or selecting / moving mod, here we set which mod the model is in.
     * @param b True -> model in selection mode, false -> model in drawing mode.
     */
    @Override
    public void setSelectingMovingOption(boolean b) {
        moveOption = b;
        if (!moveOption) {
            for (Shape s : selectedShapes) s.setStroke(getCurrentColor());
            selectedShapes.clear();
        }
    }

    /**
     * Get in which mod the model is in.
     * @return Moving mod (true or false).
     */
    @Override
    public boolean getSelectingMovingOption() {
        return moveOption;
    }

    /**
     * Change color filling of all selected shapes, not stroke because they are selected so they have a specific stroke.
     */
    @Override
    public void changeColorOfAllSelectedShapes() {
        for (Shape s : selectedShapes) {
            s.setFill(getCurrentColor());
        }
    }

    /**
     * Translate selected shapes by a delta x or delta y.
     * @param dx Delta x.
     * @param dy Delta y.
     */
    @Override
    public void moveAllSelectedShapes(double dx, double dy) {
        move.setTranslation(dx, dy);
        move.execute();
    }

    public void initMove() { move = new Move(0, 0, selectedShapes); }

    public Move getMove() { return move; }

    /**
     * Delete all selected shapes (when delete button is pressed).
     */
    @Override
    public void deleteAllSelectedShapes() {
        shapes.removeAll(selectedShapes);
        selectedShapes.clear();
    }
}
