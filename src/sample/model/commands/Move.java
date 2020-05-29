package sample.model.commands;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Move implements ICommand {

    private double dx, dy;
    private double totalX, totalY;
    private ArrayList<Shape> shapes;
    private int undoCount;

    public Move(double dx, double dy, ArrayList<Shape> s) {
        this.dx = dx;
        totalX = dx;
        this.dx = dy;
        totalY = dy;
        this.shapes = s;
        undoCount = 0;
    }

    public void setTranslation(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        totalX += dx;
        totalY += dy;
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
        if (undoCount == 0) {
            for (Shape s : shapes) {
                if (s instanceof Ellipse) {
                    ((Ellipse) s).setCenterX(((Ellipse) s).getCenterX() - totalX);
                    ((Ellipse) s).setCenterY(((Ellipse) s).getCenterY() - totalY);
                } else if (s instanceof Rectangle) {
                    ((Rectangle) s).setX(((Rectangle) s).getX() - totalX);
                    ((Rectangle) s).setY(((Rectangle) s).getY() - totalY);
                } else {
                    ((Line) s).setStartX(((Line) s).getStartX() - totalX);
                    ((Line) s).setEndX(((Line) s).getEndX() - totalX);
                    ((Line) s).setStartY(((Line) s).getStartY() - totalY);
                    ((Line) s).setEndY(((Line) s).getEndY() - totalY);
                }
            }
            undoCount++;
        }
    }

    @Override
    public void redo() {
        if (undoCount > 0) {
            for (Shape s : shapes) {
                if (s instanceof Ellipse) {
                    ((Ellipse) s).setCenterX(((Ellipse) s).getCenterX() + totalX);
                    ((Ellipse) s).setCenterY(((Ellipse) s).getCenterY() + totalY);
                } else if (s instanceof Rectangle) {
                    ((Rectangle) s).setX(((Rectangle) s).getX() + totalX);
                    ((Rectangle) s).setY(((Rectangle) s).getY() + totalY);
                } else {
                    ((Line) s).setStartX(((Line) s).getStartX() + totalX);
                    ((Line) s).setEndX(((Line) s).getEndX() + totalX);
                    ((Line) s).setStartY(((Line) s).getStartY() + totalY);
                    ((Line) s).setEndY(((Line) s).getEndY() + totalY);
                }
            }
            undoCount--;
        }
    }
}
