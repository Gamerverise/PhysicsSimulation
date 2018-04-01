package gui.game_widget_support;

import lib_2.Enums;
import model.Particle;

public class GameWidgetViewTwoParticles extends GameWidgetView {
    public Particle center;
    public Particle p;
    public Particle q;
    public double zoom;
    public Enums.Dimension dim;

    public GameWidgetViewTwoParticles(Particle center, Particle p, Particle q, double zoom, Enums.Dimension dim) {
        this.center = center;
        this.p = p;
        this.q = q;
        this.zoom = zoom;
        this.dim = dim;
    }
}
