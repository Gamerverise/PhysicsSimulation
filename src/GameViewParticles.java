public class GameViewParticles extends GameView {
    Particle p;
    Particle q;
    double zoom;
    Misc.Dimension dim;

    GameViewParticles(Particle p, Particle q, double zoom, Misc.Dimension dim) {
        this.p = p;
        this.q = q;
        this.zoom = zoom;
        this.dim = dim;
    }
    public void change_view(GameWidget gw) {
        gw.view_particles(p, q, zoom, dim);
    }
}
