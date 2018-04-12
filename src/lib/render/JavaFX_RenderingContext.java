package lib.render;

import javafx.scene.canvas.GraphicsContext;
import lib.javafx_api_extensions.GraphicsContextX;

public class JavaFX_RenderingContext extends RenderingContext {

    // The device for a JavaFX_RenderingContext is a JavaJX Canvas

    double device_width_px;
    double device_height_px;

    double device_transform_x;
    double device_transform_y;
    double device_transform_scale_x = 1;
    double device_transform_scale_y = -1;

    public double viewport_center_x_model;
    public double viewport_center_y_model;
    public double viewport_px_per_unit_model;

    public double viewport_width_px;
    public double viewport_height_px;

    public JavaFX_RenderingContext(GraphicsContext gc) {
        super(gc);

        // Default viewport is the identity operation

        set_viewport_transform(0, 0, 1);
    }

    // Coordinates get transformed as follows:
    //
    //     model -> normalized view -> actual view (in pixels)
    //
    // The first step, "model -> normalized view", is a function of the application
    //
    // The viewport_transform does the last step, "normalized view -> actual view (in pixels)"

    public void set_viewport_transform(double center_x_model, double center_y_model, double px_per_unit_model) {

        // _model suffix means units in model space

        this.center_x_model = center_x_model;
        this.center_y_model = center_y_model;
        this.px_per_unit_model = px_per_unit_model;
    }

    public void begin_render() {
        gc.save();

        gc.translate(center_x_model, center_y_model);
        gc.scale(px_per_unit_model, px_per_unit_model);
    }

    public void end_render() {
        gc.restore();
    }
}
