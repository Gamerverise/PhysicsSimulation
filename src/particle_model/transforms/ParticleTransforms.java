package particle_model.transforms;

import lib.javafx_api_extensions.AffineX;
import lib.javafx_api_extensions.javafx_api_extensions_support.ScaleOp;
import lib.transforms.TransformMulti;
import particle_model.Particle;

import static lib.tokens.enums.Identity.IDENTITY;

public class ParticleTransforms {

    // view_particle
    //     Change the current view (aka transform) so that:
    //
    //         (center_px, center_py) = (canvas_origin_x, canvas_origin_x)
    //     and
    //         diameter_px(p, q) = zoom * canvas_dim

    public static AffineX view_particle(TransformMulti transform, Particle p, double zoom,
                                        double width, double height, ScaleOp scale_op)
    {
        AffineX affine = new AffineX(IDENTITY);

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

    public AffineX view_two_particles(TransformMulti transform, Particle center, Particle p, Particle q, double zoom,
                                      double width, double height, ScaleOp scale_op)
    {
        AffineX affine = new AffineX(IDENTITY);

        double scale_rel = 1 / p.distance(q) * zoom;
        transform.translate(center.x, center.y);
        TransformMulti.scale(transform, scale_rel, width, height, scale_op);

        return affine;
    }
}
