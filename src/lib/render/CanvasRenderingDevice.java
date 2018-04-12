package lib.render;

import javafx.scene.canvas.Canvas;

import lib.javafx_api_extensions.GraphicsContextX;

public class CanvasRenderingDevice extends Canvas implements RenderingDevice {

    // set_width and set_height herein should be used instead of setWidth and setHeight from Canvas.
    // We should/would override setWidth and setHeight of Canvas, but since those methods are final,
    // we cannot do so.

    GraphicsContextX gcx;

    double device_transform_x_px;
    double device_transform_y_px;

    static double device_transform_scale_x = 1;
    static double device_transform_scale_y = -1;

    public CanvasRenderingDevice() {
        gcx = new GraphicsContextX(getGraphicsContext2D());

        // Default canvas dimensions are zero by zero

        device_transform_x_px = 0;
        device_transform_y_px = 0;
    }

    public void set_device_width(double width_px) {
        device_transform_x_px = width_px / 2;
        setWidth(width_px);
    }

    public void set_device_height(double height_px) {
        device_transform_y_px = height_px / 2;
        setHeight(height_px);
    }

    public void set_device_dimensions(double width_px, double height_px) {
        set_device_width(width_px);
        set_device_height(height_px);
    }
}
