package lib.render;

import static lib.render.Viewport.AspectRatioOption.*;
public class Viewport {

    public enum AspectRatioOption {
        SCALE_TO_FIT,
        MATCH_WIDTH,
        MATCH_HEIGHT
    }

    // _model suffix means units in model space

    public double viewport_center_x_model;
    public double viewport_center_y_model;
    public double viewport_width_model;
    public double viewport_height_model;

    public AspectRatioOption aspect_ratio_option;

    public Viewport() {
        // Default viewport size is zero-by-zero
        // Default viewport translation is the identity operation

        set_viewport(0, 0, 0, 0);
        aspect_ratio_option = SCALE_TO_FIT;
    }

    public void set_viewport(double viewport_center_x_model,
                             double viewport_center_y_model,
                             double viewport_width_model,
                             double viewport_height_model)
    {
        this.viewport_center_x_model = viewport_center_x_model;
        this.viewport_center_y_model = viewport_center_y_model;
        this.viewport_width_model = viewport_width_model;
        this.viewport_height_model = viewport_height_model;
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
        viewport_width_model = width_px;
        viewport_height_model = height_px;
    }
}
