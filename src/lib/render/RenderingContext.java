package lib.render;

public abstract class RenderingContext {

    Viewport viewport;

    RenderingContext() {
        viewport = new Viewport();
    }

    public abstract void begin_render();

    public abstract void end_render();

    public abstract void set_device_transform();

    public abstract void set_viewport_transform();
}
