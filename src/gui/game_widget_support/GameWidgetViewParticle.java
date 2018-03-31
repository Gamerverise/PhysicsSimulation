package gui.game_widget_support;

import model.Particle;

public class GameWidgetViewParticle extends GameWidgetView {
    Particle p;
    double zoom;
    Enum.Dimension dim;

    GameWidgetViewParticle(Particle p, double zoom, Enum.Dimension dim) {
        this.p = p;
        this.zoom = zoom;
        this.dim = dim;
    }
}
