package gui.game_widget_support;

import model.Particle;
import lib_2.Enums;

public class GameWidgetViewParticle extends GameWidgetView {
    public Particle p;
    public double zoom;
    public Enums.ScaleOp2D scale_op;

    public GameWidgetViewParticle(Particle p, double zoom, Enums.ScaleOp2D scale_op) {
        this.p = p;
        this.zoom = zoom;
        this.scale_op = scale_op;
    }
}
