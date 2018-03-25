import java.util.LinkedList;

public class Universe {

    LinkedList<Particle> particles = new LinkedList<>();

    Universe(Particle... particles) {
        Particle new_earth;

        // Original configuration
        add_particle(PhysSim.Main.sun, PhysSim.Main.earth);

        // Symmetry of original setup about x = y
        new_earth = PhysSim.Main.earth.copy();
        new_earth.rotate_velocity_abs(100);
        add_particle(PhysSim.Main.sun, new_earth);

        // Symmetry of original setup about x = y
        new_earth = PhysSim.Main.earth.copy();
        new_earth.y = PhysSim.Main.earth_sun_dist;
        new_earth.x = 0;
        new_earth.vy = PhysSim.Main.earth_tangential_speed;
        new_earth.vx = 0;
        add_particle(PhysSim.Main.sun, new_earth);
    }

    Universe(Universe u) {
        for (Particle p : u.particles)
            this.particles.add(new Particle(p));
    }
}
