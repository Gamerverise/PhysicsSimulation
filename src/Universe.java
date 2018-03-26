import java.util.LinkedList;

public class Universe {

    double acceleration_constant;
    double max_speed;

    LinkedList<Particle> particles = new LinkedList<>();

    Universe(double acceleration_constant, double max_speed, Particle... particles) {
        this.acceleration_constant = acceleration_constant;
        this.max_speed = max_speed;
        add_particles(particles);
    }

    Universe(Universe u) {
        acceleration_constant = u.acceleration_constant;
        max_speed = u.max_speed;
        add_particles(u.particles);
    }

    void add_particles(Particle... particles) {
        for (Particle p : particles)
            this.particles.add(new Particle(p));
    }

    void add_particles(LinkedList<Particle> particles) {
        for (Particle p : particles)
            this.particles.add(new Particle(p));
    }
}
