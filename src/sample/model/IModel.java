package sample.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public interface IModel {
    void setCurrentColor(Color c);
    Color getCurrentColor();
    void setCurrentDrawingShape(String s);
    String getCurrentDrawingShape();

    void addShape(Shape s);
    void changeSizeOfShapeInCreation(Shape s, double endX, double endY);
    ArrayList<Shape> getAllSelectedShapes();
    Shape getLastShape();

    void setMove(boolean b);
    boolean getMove();

    void changeColorOfAllSelectedShapes();
    void moveAllSelectedShapes(double x, double y);
    void deleteAllSelectedShapes();
}
