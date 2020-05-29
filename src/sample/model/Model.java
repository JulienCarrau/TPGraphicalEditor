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
        lines = new ArrayList<>();
        rectangles = new ArrayList<>();
        ellipses = new ArrayList<>();
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
    public void addLine(Line l) {
        lines.add(l);
    }

    @Override
    public void updateLine(Line l, double endX, double endY) {
        l.setEndX(endX);
        l.setEndY(endY);
    }

    @Override
    public Line getLastLine() {
        return lines.get(lines.size() - 1);
    }

    @Override
    public void addEllipse(Ellipse e) {
        ellipses.add(e);
    }

    @Override
    public void updateEllipse(Ellipse e, double endX, double endY) {
        e.setRadiusX(endX);
        e.setRadiusY(endY);
    }

    @Override
    public Ellipse getLastEllipse() {
        return ellipses.get(ellipses.size() - 1);
    }

    @Override
    public void addRectangle(Rectangle r) {
        rectangles.add(r);
    }

    @Override
    public void updateRectangle(Rectangle r, double endX, double endY) {
        if (endX > r.getX()) r.setWidth(Math.abs(r.getX() - endX));
        //else r.setX(Math.abs(r.getX() - endX));
        if (endY > r.getY()) r.setHeight(Math.abs(r.getY() - endY));
        //else r.setY(Math.abs(r.getY() - endY));
    }

    @Override
    public Rectangle getLastRectangle() {
        return rectangles.get(rectangles.size() - 1);
    }
}
