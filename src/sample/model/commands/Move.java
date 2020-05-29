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

    /**
     * Class contructor.
     * @param dx First delta x positition.
     * @param dy First delta y positition.
     * @param s List of shapes linked to this movement.
     */
    public Move(double dx, double dy, ArrayList<Shape> s) {
        this.dx = dx;
        totalX = dx;
        this.dx = dy;
        totalY = dy;
        this.shapes = s;
        undoCount = 0;
    }

    /**
     * Update translation, and keep a track of the movement since its begining.
     * @param dx New delta x movement.
     * @param dy New delta y movement.
     */
    public void setTranslation(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        totalX += dx;
        totalY += dy;
    }

    /**
     * Execute translation by small tests.
     */
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

    /**
     * Undo all movement in a single translation.
     * Undo only if we never undid before (we can only undo once).
     */
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

    /**
     * Redo all movement in a single translation.
     * Redo only one time if we already undid.
     */
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
