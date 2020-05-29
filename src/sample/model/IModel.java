package sample.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public interface IModel {
    void setCurrentColor(Color c);
    Color getCurrentColor();

    void setCurrentShape(String s);
    String getCurrentShape();
}
