package sample.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Model implements IModel {
    private ArrayList<Shape> shapes;
    private String currentSelectedShape;
    private Color currentColor;
    private boolean moveOption; // if true, we only move shapes, else we can draw new ones

    public Model() {
        shapes = new ArrayList<>();
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
    public void setCurrentShape(String s) {
        currentSelectedShape = s;
    }

    @Override
    public String getCurrentShape() {
        return currentSelectedShape;
    }

    @Override
    public void addShape(Shape s) {
        shapes.add(s);
        s.setOnMouseClicked(mouseEvent -> {
            if (moveOption) {
                s.setStroke(Color.RED);
            }
        });
    }

    private void makeBigger(Shape s) {

    }

    @Override
    public void updateShape(Shape s, double endX, double endY) {
        switch (currentSelectedShape) {
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
    public Shape getLastShape() {
        return shapes.get(shapes.size() - 1);
    }

    @Override
    public void setMove(boolean b) {
        moveOption = b;
    }

    @Override
    public boolean getMove() {
        return moveOption;
    }
}
