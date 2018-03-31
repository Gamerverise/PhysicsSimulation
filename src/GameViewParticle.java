public class GameViewParticle extends GameView {
    Particle p;
    double zoom;
    Misc.Dimension dim;

    GameViewParticle(Particle p, double zoom, Misc.Dimension dim) {
        this.p = p;
        this.zoom = zoom;
        this.dim = dim;
    }
}
