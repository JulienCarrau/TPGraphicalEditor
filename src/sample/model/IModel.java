package sample.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public interface IModel {
    void setCurrentColor(Color c);
    Color getCurrentColor();
    void setCurrentDrawingShape(String s);
    String getCurrentDrawingShape();

    void addShape(Shape s);
    void updateShape(Shape s, double endX, double endY);
    Shape getLastShape();

    void setMove(boolean b);
    boolean getMove();

    void changeColorOfAllSelectedShapes();
}
