package gui.game_widget_support;

import model.Particle;

public class GameWidgetViewTwoParticles extends GameWidgetView {
    Particle center;
    Particle p;
    Particle q;
    double zoom;
    Enum.Dimension dim;

    GameWidgetViewTwoParticles(Particle center, Particle p, Particle q, double zoom, Enum.Dimension dim) {
        this.center = center;
        this.p = p;
        this.q = q;
        this.zoom = zoom;
        this.dim = dim;
    }
}
