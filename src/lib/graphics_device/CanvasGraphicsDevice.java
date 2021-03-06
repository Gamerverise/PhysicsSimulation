package lib.graphics_device;

import javafx.scene.canvas.Canvas;
import lib.graphics.CustomGraphicsContext;
import lib.java_lang_extensions.tuples.WidthHeight;

public class CanvasGraphicsDevice extends VirtualGraphicsDevice {

    public Canvas canvas;
    public CustomGraphicsContext cgc;

    public static double device_transform_scale_x = 1;
    public static double device_transform_scale_y = -1;

    public CanvasGraphicsDevice() {
        canvas = new Canvas();
        cgc = new CustomGraphicsContext(canvas.getGraphicsContext2D());

        // The initial translation for the device transform is zero, since the initial canvas size is zero

        device_transform_x_px = 0;
        device_transform_y_px = 0;
    }

    public double get_scale_x() {
        return device_transform_scale_x;
    }

    public double get_scale_y() {
        return device_transform_scale_y;
    }

    public void set_device_xy(double x_px, double y_px) {
        canvas.setLayoutX(x_px);
        canvas.setLayoutY(y_px);
    }

    public void set_device_width(double width_px) {
        device_transform_x_px = width_px / 2;
        canvas.setWidth(width_px);
    }

    public void set_device_height(double height_px) {
        device_transform_y_px = height_px / 2;
        canvas.setHeight(height_px);
    }

    public void set_device_dimensions(double width_px, double height_px) {
        set_device_width(width_px);
        set_device_height(height_px);
    }

    public void set_device_geometry(double x_px, double y_px, double width_px, double height_px) {
        set_device_xy(x_px, y_px);
        set_device_dimensions(width_px, height_px);
    }

    public double get_width_px() {
        return canvas.getWidth();
    }

    public double get_height_px() {
        return canvas.getHeight();
    }

    public WidthHeight<Double> get_dimensions_px() {
        return new WidthHeight<>(canvas.getWidth(), canvas.getHeight());
    }
}
