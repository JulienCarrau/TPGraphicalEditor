package sample.model.commands;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Move implements ICommand {

    private double dx = 0, dy = 0;
    private ArrayList<Shape> shapes = new ArrayList<>();

    public Move(double dx, double dy, ArrayList<Shape> s) {
        this.dx = dx;
        this.dy = dy;
        this.shapes = s;
    }

    public void set(double dx, double dy, ArrayList<Shape> s) {
        this.dx = dx;
        this.dy = dy;
        this.shapes = s;
    }

    @Override
    public void execute() {
        for (Shape s : shapes) {
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
    public void undo() {
        for (Shape s : shapes) {
            if (s instanceof Ellipse) {
                ((Ellipse) s).setCenterX(((Ellipse) s).getCenterX() - dx);
                ((Ellipse) s).setCenterY(((Ellipse) s).getCenterY() - dy);
            }
            else if (s instanceof Rectangle) {
                ((Rectangle) s).setX(((Rectangle) s).getX() - dx);
                ((Rectangle) s).setY(((Rectangle) s).getY() - dy);
            }
            else {
                ((Line) s).setStartX(((Line) s).getStartX() - dx);
                ((Line) s).setEndX(((Line) s).getEndX() - dx);
                ((Line) s).setStartY(((Line) s).getStartY() - dy);
                ((Line) s).setEndY(((Line) s).getEndY() - dy);
            }
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
