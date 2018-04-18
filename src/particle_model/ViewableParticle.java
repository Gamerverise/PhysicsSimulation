package particle_model;

import javafx.scene.paint.Color;
import lib.render.Viewport;

public class ViewableParticle extends Particle {

    @SuppressWarnings("unchecked")
    public <T extends Particle> T copy() {
        return (T) new ViewableParticle(this);
    }

    public ViewableParticle(String name, double x, double y, double vx, double vy, double ax, double ay, double mass, double radius, Color color) {
        super(name, x, y, vx, vy, ax, ay, mass, radius, color);
    }

    public ViewableParticle(Particle particle) {
        super(particle);
    }

    public Viewport view(Viewport viewport, double x_offset_pct, double y_offset_pct, double zoom)
    {
        double viewport_aspect_ratio = viewport.basis_width_model / viewport.basis_height_model;

        double p_diameter_model = 2*radius;

        if (viewport_aspect_ratio > 1) {
            viewport.basis_height_model = p_diameter_model / zoom;
            viewport.basis_width_model = viewport.basis_height_model * viewport_aspect_ratio;
        } else {
            viewport.basis_width_model = p_diameter_model / zoom;
            viewport.basis_height_model =  viewport.basis_width_model * viewport_aspect_ratio;
        }

        viewport.basis_center_x_model = x - x_offset_pct * viewport.basis_width_model / 2;
        viewport.basis_center_y_model = y - y_offset_pct * viewport.basis_height_model /2 ;

        return viewport;
    }

    public Viewport view_orbit(Viewport viewport, Particle orbiter, double margin_pct_model)
    {
        double viewport_width_model;
        double viewport_height_model;

        double half_wh_model;

        half_wh_model= distance(orbiter) + orbiter.radius;
        half_wh_model = half_wh_model / (1 - margin_pct_model);

        double viewport_aspect_ratio = viewport.basis_width_model / viewport.basis_height_model;

        if (viewport_aspect_ratio > 1) {
            viewport_height_model = half_wh_model;
            viewport_width_model = viewport_height_model * viewport_aspect_ratio;
        } else {
            viewport_width_model = half_wh_model;
            viewport_height_model =  viewport_width_model * viewport_aspect_ratio;
        }

        viewport.set_basis_geometry(x, y, viewport_width_model, viewport_height_model);

        return viewport;
    }
}
