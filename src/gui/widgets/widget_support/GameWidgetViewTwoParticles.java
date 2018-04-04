package gui.widgets.widget_support;

import model.Particle;

import static gui.javafx_api_extensions.javafx_support.Enums.ScaleOp2D;

public class GameWidgetViewTwoParticles extends GameWidgetView {
    public Particle center;
    public Particle p;
    public Particle q;
    public double zoom;
    public ScaleOp2D scale_op;

    public GameWidgetViewTwoParticles(Particle center, Particle p, Particle q, double zoom, ScaleOp2D scale_op) {
        this.center = center;
        this.p = p;
        this.q = q;
        this.zoom = zoom;
        this.scale_op = scale_op;
    }
}
