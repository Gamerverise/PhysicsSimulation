import javafx.scene.canvas.GraphicsContext;

public class GraphicsContextX {
    GraphicsContext gc;

    void fill_circle(GraphicsContext gc, double x, double y, double radius) {
        this.gc = gc;

        // The location is offset by the radius because for this function (x, y) specify
        // the center, but for fillOval (x, y) specifies the top-left of the bounding box
        // of the oval

        gc.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }
}
