package lib.render.device;

public abstract class VirtualGraphicsDevice extends GraphicsDevice {

    public abstract void set_device_width(double width_px);

    public abstract void set_device_height(double height_px);

    public abstract void set_device_dimensions(double width_px, double height_px);

    void scale_to_width(double width) {
        double height = width / get_device_aspect_ratio();
        set_device_dimensions(width, height);
    }

    void scale_to_height(double height) {
        double width = height * get_device_aspect_ratio();
        set_device_dimensions(width, height);
    }
}
