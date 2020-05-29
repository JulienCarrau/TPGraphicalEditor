package sample.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Model implements IModel {
    private ArrayList<Ellipse> ellipses;
    private ArrayList<Rectangle> rectangles;
    private ArrayList<Line> lines;
    private String currentSelectedShape;
    private Color currentColor;

    public Model() {

    }

    @Override
    public void setCurrentColor(Color c) {
        currentColor = c;
    }

    @Override
    public Color getCurrentColor() {
        System.out.println(currentColor);
        return currentColor;
    }
}
