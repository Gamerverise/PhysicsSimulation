package gui.game_widget_support;

import model.Particle;
import lib_2.Enums;

public class GameWidgetViewParticle extends GameWidgetView {
    public Particle p;
    public double zoom;
    public Enums.Dimension dim;

    public GameWidgetViewParticle(Particle p, double zoom, Enums.Dimension dim) {
        this.p = p;
        this.zoom = zoom;
        this.dim = dim;
    }
}
