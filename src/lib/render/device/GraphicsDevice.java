package lib.render.device;

import lib.debug.MethodNameHack;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.AssertMessages.MUST_OVERRIDE_METHOD;
import static lib.debug.Debug.assert_msg;

public abstract class GraphicsDevice {
    // GraphicsDevice is the base class for all graphics device "drivers"

    // Device specific code goes in a sub-class of GraphicsDevice

    // Currently, we are only targeting a JavaFX canvas as a graphics device, so at the moment, this class
    // only serves aesthetic purposes

    public double device_transform_x_px;
    public double device_transform_y_px;

    private static double device_transform_scale_x;
    private static double device_transform_scale_y;

    public double get_delta_x() {
        return device_transform_x_px;
    }

    public double get_delta_y() {
        return device_transform_y_px;
    }

    public double get_scale_x() {

        assert false : assert_msg(
                this.getClass(),
                new MethodNameHack(){}.method_name(),
                BAD_CODE_PATH, MUST_OVERRIDE_METHOD);

        return device_transform_scale_x;
    }

    public double get_scale_y() {

        assert false : assert_msg(
                this.getClass(),
                new MethodNameHack(){}.method_name(),
                BAD_CODE_PATH, MUST_OVERRIDE_METHOD);

        return device_transform_scale_y;
    }
}
