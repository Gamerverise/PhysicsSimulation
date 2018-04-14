package lib.render.device;

public abstract class VirtualGraphicsDevice extends GraphicsDevice {

    public abstract void set_device_width(double width_px);

    public abstract void set_device_height(double height_px);

    public abstract void set_device_dimensions(double width_px, double height_px);
}
