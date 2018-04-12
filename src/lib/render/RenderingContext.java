package lib.render;

import javafx.scene.canvas.GraphicsContext;
import lib.javafx_api_extensions.GraphicsContextX;

public abstract class RenderingContext extends GraphicsContextX {

    double device_width_px;
    double device_height_px;

    double device_transform_x;
    double device_transform_y;
    double device_transform_scale_x;
    double device_transform_scale_y;

    public double viewport_width_px;
    public double viewport_height_px;

    // _model suffix means units in model space

    public double viewport_center_x_model;
    public double viewport_center_y_model;
    public double viewport_px_per_unit_model;

    public RenderingContext(GraphicsContext gc) {
        super(gc);

        viewport_width_px = 0;
        viewport_height_px = 0;

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

    public void set_viewport_transform(double viewport_center_x_model,
                                       double viewport_center_y_model,
                                       double viewport_px_per_unit_model) {

        this.viewport_center_x_model = viewport_center_x_model;
        this.viewport_center_y_model = viewport_center_y_model;
        this.viewport_px_per_unit_model = viewport_px_per_unit_model;
    }

    void set_device_width(double width) {
        device_transform_x = width/2;
    }

    public void set_device_height(double height) {
        device_transform_y = height/2;
    }

    public void set_device_dimensions(double width, double height) {
        device_transform_x = width/2;
        device_transform_y = height/2;
    }

    void scale_to_width(double width) {
        double height = width / aspect_ratio;
        set_dimensions(width, height);
    }

    void scale_to_height(double height) {
        double width = height * aspect_ratio;
        set_dimensions(width, height);
    }

    public void set_viewport_scale(double px_per_unit_model) {
        gcx.px_per_unit_model = px_per_unit_model;
    }

    public void set_viewport_dimensions(double width_px, double height_px) {
        viewport_width_px = width_px;
        viewport_height_px = height_px;
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
