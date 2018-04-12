package lib.render;

import javafx.scene.canvas.Canvas;
import lib.debug.MethodNameHack;
import lib.javafx_api_extensions.GraphicsContextX;
import lib.javafx_api_extensions.javafx_support.Enums;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public class RenderingCanvas extends Canvas {

    GraphicsContextX gcx;

    // set_width and set_height herein should be used instead of setWidth and setHeight from Canvas.
    // We should/would override setWidth and setHeight of Canvas, but since those methods are final,
    // we cannot do so.

    public RenderingCanvas(double aspect_ratio) {
        this.gcx = new GraphicsContextX(getGraphicsContext2D());
        this.aspect_ratio = aspect_ratio;

        // The initial translation for the device transform is zero, since the initial canvas size is zero

        this.device_transform_x = 0;
        this.device_transform_y = 0;
    }

    void set_width(double width) {
        setWidth(width);
        device_transform_x = getWidth()/2;
    }

    public void set_height(double height) {
        setHeight(height);
        device_transform_y = getHeight()/2;
    }

    public void set_dimensions(double width, double height) {
        set_width(width);
        set_height(height);
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

    public Enums.Dimension2D scale(double scale_rel, Enums.ScaleOp2D scale_op) {
        Canvas canvas = gc.getCanvas();
        double scale;

        switch (scale_op) {
            case WIDTH:
                scale = canvas.getWidth() * scale_rel;
                gc.scale(scale, scale);
                update_min_radius(this::invert_x);
                return Enums.Dimension2D.WIDTH;
            case HEIGHT:
                scale = canvas.getHeight() * scale_rel;
                gc.scale(scale, scale);
                update_min_radius(this::invert_y);
                return Enums.Dimension2D.HEIGHT;

            case HALF_WIDTH:
                return scale(scale_rel/2, Enums.ScaleOp2D.WIDTH);
            case HALF_HEIGHT:
                return scale(scale_rel/2, Enums.ScaleOp2D.HEIGHT);

            case MAX:
                if (canvas.getWidth() > canvas.getHeight())
                    return scale(scale_rel, Enums.ScaleOp2D.WIDTH);
                else
                    return scale(scale_rel, Enums.ScaleOp2D.HEIGHT);
            case MIN:
                if (canvas.getWidth() < canvas.getHeight())
                    return scale(scale_rel, Enums.ScaleOp2D.WIDTH);
                else
                    return scale(scale_rel, Enums.ScaleOp2D.HEIGHT);

            case HALF_MAX:
                if (canvas.getWidth() > canvas.getHeight())
                    return scale(scale_rel/2, Enums.ScaleOp2D.WIDTH);
                else
                    return scale(scale_rel/2, Enums.ScaleOp2D.HEIGHT);
            case HALF_MIN:
                if (canvas.getWidth() < canvas.getHeight())
                    return scale(scale_rel/2, Enums.ScaleOp2D.WIDTH);
                else
                    return scale(scale_rel/2, Enums.ScaleOp2D.HEIGHT);
        }

        assert false : assert_msg(
                this.getClass(),
                new MethodNameHack(){}.method_name(),
                BAD_CODE_PATH);
        return null;
    }
}
