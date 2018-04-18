package particle_model;

import java.util.LinkedList;

import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public class Universe<T extends Particle> {

    public double acceleration_constant;
    public double max_speed;

    public LinkedList<T> particles = new LinkedList<>();

    @SafeVarargs
    public Universe(double acceleration_constant, double max_speed, T... particles) {
        this.acceleration_constant = acceleration_constant;
        this.max_speed = max_speed;
        add_particles(particles);
    }

    public Universe(Universe<T> u, CopyType copy_type) {
        acceleration_constant = u.acceleration_constant;
        max_speed = u.max_speed;

        if (copy_type == COPY_SHALLOW)
            particles = u.particles;
        else
            add_particles(u.particles);
    }

    @SafeVarargs
    public final void add_particles(T... particles) {
        for (T p : particles)
            this.particles.add(p.copy());
    }

    public void add_particles(LinkedList<T> particles) {
        for (T p : particles)
            this.particles.add(p.copy());
    }
}
