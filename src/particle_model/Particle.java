package particle_model;

import lib.tokens.enums.CopyType;

public class Particle extends ParticleBase<Particle> {

    public String name;
    public double x;
    public double y;
    public double vx;
    public double vy;
    public double ax;
    public double ay;
    public double mass;
    public double radius;

    public Particle(String name, double x, double y, double vx, double vy, double ax, double ay, double mass, double radius) {
        super(name, x, y, vx, vy, ax, ay, mass, radius);
    }

    public Particle(Particle p) {
        super(p.name, p.x, p.y, p.vx, p.vy, p.ax, p.ay, p.mass, p.radius);
    }

    public Particle new_copy() {
        return new Particle(this);
    }

    public Particle new_copy(CopyType copy_type) {
        return new Particle(this);
    }
}
