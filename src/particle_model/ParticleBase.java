package particle_model;

import lib.java_lang_extensions.parametrized_types.ConstructorShallowDataStructure;

public abstract
class ParticleBase
        implements ConstructorShallowDataStructure<ParticleBase>
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

    public ParticleBase(String name, double x, double y, double vx, double vy, double ax, double ay, double mass, double radius) {
        init(name, x, y, vx, vy, ax, ay, mass, radius);
    }

    public ParticleBase(ParticleBase p) {
        init(p.name, p.x, p.y, p.vx, p.vy, p.ax, p.ay, p.mass, p.radius);
    }

    public void init(String name, double x, double y, double vx, double vy, double ax, double ay, double mass, double radius) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
        this.mass = mass;
        this.radius = radius;
    }

    public double velocity() {
        return Math.sqrt(vx*vx + vy*vy);
    }

    public double distance(ParticleBase p) {
        double dx = p.x - x;
        double dy = p.y - y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public void rotate_velocity_abs(double degrees) {
        double radians = Math.PI / 180 * degrees;
        double velocity_scalar = Math.sqrt(vx*vx + vy*vy);
        vx = velocity_scalar * Math.cos(radians);
        vy = velocity_scalar * Math.sin(radians);
    }
}
