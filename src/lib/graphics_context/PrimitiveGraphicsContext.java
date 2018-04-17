package lib.graphics_context;

import lib.java_lang_extensions.tuples.WidthHeight;
import lib.graphics_device.GraphicsDevice;

public abstract class PrimitiveGraphicsContext<DEVICE extends GraphicsDevice> {

    // Drawing primitives for a device should be organized into a sub-class of PrimitiveGraphicsContext

    DEVICE device;

    public PrimitiveGraphicsContext(DEVICE device) {
        this.device = device;
    }

    public double get_width_px() {
        return device.get_width_px();
    }

    public double get_height_px() {
        return device.get_height_px();
    }

    public WidthHeight<Double> get_device_dimensions() {
        return device.get_dimensions_px();
    }

    public double get_aspect_ratio() {
        return device.get_aspect_ratio();
    }
}
