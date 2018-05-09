package particle_model;

import lib.java_lang_extensions.parametrized_types.Constructor;

public class Particle
        extends ParticleBase<Particle>
{
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

    Particle new_instance(Object... args) {
        return Constructor.new_instance(args);
    }

    public Particle new_copy() {
        return new Particle(this);
    }
}
