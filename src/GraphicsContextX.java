import javafx.scene.canvas.GraphicsContext;

public class GraphicsContextX {
    GraphicsContext gc;

    GraphicsContextX(GraphicsContext gc) {
        this.gc = gc;
    }

    void fill_circle(double x, double y, double radius) {
        gc.fillOval(x, y, radius, radius);
    }
}
