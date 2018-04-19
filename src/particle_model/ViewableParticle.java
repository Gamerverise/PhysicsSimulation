package particle_model;

import javafx.scene.paint.Color;
import lib.graphics.CustomGraphicsContext;
import lib.javafx_api_extensions.GraphicsContextX;
import lib.render.Viewport;
import lib.render.graphics_context.CanvasRenderingGraphicsContext;

public class ViewableParticle extends Particle {

    public Color color;

    public ViewableParticle(String name, double x, double y, double vx, double vy, double ax, double ay, double mass, double radius, Color color) {
        super(name, x, y, vx, vy, ax, ay, mass, radius);
        this.color = color;
    }

    public ViewableParticle(ViewableParticle p) {
        this(p.name, p.x, p.y, p.vx, p.vy, p.ax, p.ay, p.mass, p.radius, p.color);
    }

    public void init(String name, double x, double y, double vx, double vy, double ax, double ay, double mass, double radius, Color color) {
        super.init(name, x, y, vx, vy, ax, ay, mass, radius);
        this.color = color;
    }

    @SuppressWarnings("unchecked")
    public <T extends Particle> T new_copy() {
        return (T) new ViewableParticle(this);
    }

    public Viewport view(Viewport viewport, double x_offset_pct, double y_offset_pct, double zoom) {
        double viewport_aspect_ratio = viewport.basis_width_model / viewport.basis_height_model;

        double p_diameter_model = 2 * radius;

        if (viewport_aspect_ratio > 1) {
            viewport.basis_height_model = p_diameter_model / zoom;
            viewport.basis_width_model = viewport.basis_height_model * viewport_aspect_ratio;
        } else {
            viewport.basis_width_model = p_diameter_model / zoom;
            viewport.basis_height_model = viewport.basis_width_model * viewport_aspect_ratio;
        }

        viewport.basis_center_x_model = x - x_offset_pct * viewport.basis_width_model / 2;
        viewport.basis_center_y_model = y - y_offset_pct * viewport.basis_height_model / 2;

        return viewport;
    }

    public Viewport view_orbit(Viewport viewport, Particle orbiter, double radius_pct_of_half_wh) {
        double viewport_width_model;
        double viewport_height_model;

        double half_wh_model = distance(orbiter) / radius_pct_of_half_wh;
        double wh_model = 2 * half_wh_model;

        double viewport_aspect_ratio = viewport.basis_width_model / viewport.basis_height_model;

        if (viewport_aspect_ratio > 1) {
            viewport_height_model = wh_model;
            viewport_width_model = viewport_height_model * viewport_aspect_ratio;
        } else {
            viewport_width_model = wh_model;
            viewport_height_model = viewport_width_model * viewport_aspect_ratio;
        }

        viewport.set_basis_geometry(x, y, viewport_width_model, viewport_height_model);

        return viewport;
    }

    public void draw(CanvasRenderingGraphicsContext rgc) {
        CustomGraphicsContext cgc = rgc.device.cgc;

        cgc.gc.setFill(color);
        rgc.fill_circle(x, y, radius);

        cgc.draw_arrow(x, y, vx, vy, Color.YELLOWGREEN);
    }
}