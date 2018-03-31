public class GameViewTwoParticles extends GameView {
    Particle center;
    Particle p;
    Particle q;
    double zoom;
    Misc.Dimension dim;

    GameViewTwoParticles(Particle center, Particle p, Particle q, double zoom, Misc.Dimension dim) {
        this.center = center;
        this.p = p;
        this.q = q;
        this.zoom = zoom;
        this.dim = dim;
    }
}
