package sample.model;

import javafx.animation.ScaleTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;

public class Model implements IModel {
    private ArrayList<Shape> shapes;
    private ArrayList<Shape> selectedShapes;
    private String currentDrawingShape;
    private Color currentColor;
    private boolean moveOption; // if true, we only move shapes, else we can draw new ones

    public Model() {
        shapes = new ArrayList<>();
        selectedShapes = new ArrayList<>();
        moveOption = false;
    }

    @Override
    public void setCurrentColor(Color c) {
        currentColor = c;
    }

    @Override
    public Color getCurrentColor() {
        return currentColor;
    }

    @Override
    public void setCurrentDrawingShape(String s) {
        currentDrawingShape = s;
    }

    @Override
    public String getCurrentDrawingShape() {
        return currentDrawingShape;
    }

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

    @Override
    public void changerSizeOfShapeInCreation(Shape s, double endX, double endY) {
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

    @Override
    public ArrayList<Shape> getAllSelectedShapes() {
        return selectedShapes;
    }

    @Override
    public Shape getLastShape() {
        return shapes.get(shapes.size() - 1);
    }

    @Override
    public void setMove(boolean b) {
        moveOption = b;
        if (!moveOption) {
            for (Shape s : selectedShapes) s.setStroke(getCurrentColor());
            selectedShapes.clear();
        }
    }

    @Override
    public boolean getMove() {
        return moveOption;
    }

    @Override
    public void changeColorOfAllSelectedShapes() {
        for (Shape s : selectedShapes) {
            s.setFill(getCurrentColor());
        }
    }

    @Override
    public void moveAllSelectedShapes(double dx, double dy) {
        for (Shape s : selectedShapes) {
            if (s instanceof Ellipse) {
                ((Ellipse) s).setCenterX(((Ellipse) s).getCenterX() + dx);
                ((Ellipse) s).setCenterY(((Ellipse) s).getCenterY() + dy);
            }
            else if (s instanceof Rectangle) {
                ((Rectangle) s).setX(((Rectangle) s).getX() + dx);
                ((Rectangle) s).setY(((Rectangle) s).getY() + dy);
            }
            else {
                ((Line) s).setStartX(((Line) s).getStartX() + dx);
                ((Line) s).setEndX(((Line) s).getEndX() + dx);
                ((Line) s).setStartY(((Line) s).getStartY() + dy);
                ((Line) s).setEndY(((Line) s).getEndY() + dy);
            }
        }
    }

    @Override
    public void deleteAllSelectedShapes() {
        shapes.removeAll(selectedShapes);
        selectedShapes.clear();
    }
}
