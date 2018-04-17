package views;

import lib.javafx_api_extensions.AffineX;
import lib.javafx_api_extensions.AffineX_Clash;
import lib.javafx_api_extensions.javafx_api_extensions_support.ScaleOp;
import lib.transforms.TransformMulti;
import particle_model.Particle;

public class ParticleView {

    public enum VIEW_PARTICLE {VIEW_PARTICLE}
    public enum VIEW_TWO_PARTICLES {VIEW_TWO_PARTICLES}

    public double width;
    public double height;
    public AffineX transform;

    private ParticleView(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public ParticleView(VIEW_PARTICLE overload_const, Particle p, double zoom, double width, double height, ScaleOp scale_op)
    {
        this(width, height);
        this.transform = view_particle(new AffineX_Clash(), p, zoom, width, height, scale_op);
    }

    public ParticleView(VIEW_TWO_PARTICLES overload_const, Particle center, Particle p, Particle q, double zoom,
                        double width, double height, ScaleOp scale_op)
    {
        this(width, height);
        this.transform = view_two_particles(new AffineX_Clash(), center, p, q, zoom, width, height, scale_op);
    }

    public AffineX view_particle(TransformMulti transform, Particle p, double zoom, ScaleOp scale_op) {
        return view_particle(new AffineX_Clash(), p, zoom, width, height, scale_op);
    }

    public AffineX view_two_particles(TransformMulti transform, Particle center, Particle p, Particle q,
                                      double zoom, ScaleOp scale_op) {
        return view_two_particles(new AffineX_Clash(), center, p, q, zoom, width, height, scale_op);
    }

    // view_particle
    //     Change the current view (aka transform) so that:
    //
    //         (center_px, center_py) = (canvas_origin_x, canvas_origin_x)
    //     and
    //         diameter_px(p, q) = zoom * canvas_dim

    public static AffineX view_particle(TransformMulti transform, Particle p, double zoom,
                                        double width, double height, ScaleOp scale_op)
    {
        AffineX affine = new AffineX();

        double scale_rel = 1 / p.radius / 2 * zoom;
        transform.translate(p.x, p.y);
        TransformMulti.scale(transform, scale_rel, width, height, scale_op);

        return affine;
    }

    // FIXME: LOW PRIORITY: Old comment
    // view_particle
    //     Scale the current view (aka transform) so that:
    //
    //         dist_px(p, q) = zoom * canvas_dim

    public static AffineX view_two_particles(TransformMulti transform, Particle center, Particle p, Particle q,
                                             double zoom, double width, double height, ScaleOp scale_op)
    {
        AffineX affine = new AffineX();

        double scale_rel = 1 / p.distance(q) * zoom;
        transform.translate(center.x, center.y);
        TransformMulti.scale(transform, scale_rel, width, height, scale_op);

        return affine;
    }
}
