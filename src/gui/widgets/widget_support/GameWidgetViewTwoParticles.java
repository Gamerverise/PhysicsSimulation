package gui.widgets.widget_support;

import model.Particle;

public class GameWidgetViewTwoParticles extends GameWidgetView {
    public Particle center;
    public Particle p;
    public Particle q;
    public double zoom;
    public Enums.ScaleOp2D scale_op;

    public GameWidgetViewTwoParticles(Particle center, Particle p, Particle q, double zoom, Enums.ScaleOp2D scale_op) {
        this.center = center;
        this.p = p;
        this.q = q;
        this.zoom = zoom;
        this.scale_op = scale_op;
    }
}
