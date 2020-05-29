package sample.model;

import javafx.scene.canvas.Canvas;

public class Model implements IModel {
    private Canvas drawing;

    public Model(Canvas c) {
        drawing = c;
    }
}
