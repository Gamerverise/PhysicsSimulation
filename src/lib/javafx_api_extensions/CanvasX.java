package lib.javafx_api_extensions;

import javafx.scene.canvas.Canvas;

public class CanvasX {
    Canvas canvas;
    GraphicsContextX gcx;

    public CanvasX(Canvas canvas, double min_radius_px) {
        this.canvas = canvas;
        this.gcx = new GraphicsContextX(canvas.getGraphicsContext2D(), min_radius_px);
        gcx.gc.save();
    }

    void set_viewport_width(double width) {
        gcx.gc.restore();
        gcx.gc.save();

        canvas.setWidth(width);
        gcx.gc.scale(width, 1);
    }

    public void set_viewport_height(double height) {
        gcx.gc.restore();
        gcx.gc.save();

        canvas.setHeight(height);
        gcx.gc.scale(1, height);
    }

    public void set_viewport(double width, double height) {
        gcx.gc.restore();
        gcx.gc.save();

        canvas.setWidth(width);
        canvas.setHeight(height);
        gcx.gc.scale(width, height);
    }
}
