package gui.widgets.widget_support;

import phys_model.Particle;

import static lib.javafx_api_extensions.javafx_support.Enums.ScaleOp;

public class GameWidgetViewParticle extends GameWidgetView {

    // GameWidgetView and its subclasses describe transformations from model coordinates to normalized
    // view coordinates:
    //
    //     model coordinates -> normalized view coordinates

    public Particle p;
    public double zoom;
    public ScaleOp scale_op;

    public GameWidgetViewParticle(Particle p, double zoom, ScaleOp scale_op) {
        this.p = p;
        this.zoom = zoom;
        this.scale_op = scale_op;
    }
}
