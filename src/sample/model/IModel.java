package sample.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public interface IModel {
    void setCurrentColor(Color c);
    Color getCurrentColor();
    void setCurrentShape(String s);
    String getCurrentShape();

    void addLine(Line l);
    void updateLine(Line l, double endX, double endY);
    Line getLastLine();

    void addEllipse(Ellipse e);
    void updateEllipse(Ellipse e, double endX, double endY);
    Ellipse getLastEllipse();

    void addRectangle(Rectangle r);
    void updateRectangle(Rectangle r, double endX, double endY);
    Rectangle getLastRectangle();
}
