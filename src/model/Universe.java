package model;

import java.util.LinkedList;

import lib.data_structures.CopyType;

import static lib.data_structures.CopyType.SHALLOW;

public class Universe {

    public double acceleration_constant;
    public double max_speed;

    public LinkedList<Particle> particles = new LinkedList<>();

    public Universe(double acceleration_constant, double max_speed, Particle... particles) {
        this.acceleration_constant = acceleration_constant;
        this.max_speed = max_speed;
        add_particles(particles);
    }

    public Universe(Universe u, CopyType copy_type) {
        acceleration_constant = u.acceleration_constant;
        max_speed = u.max_speed;

        if (copy_type == SHALLOW)
            particles = u.particles;
        else
            add_particles(u.particles);
    }

    public void add_particles(Particle... particles) {
        for (Particle p : particles)
            this.particles.add(new Particle(p));
    }

    public void add_particles(LinkedList<Particle> particles) {
        for (Particle p : particles)
            this.particles.add(new Particle(p));
    }
}
