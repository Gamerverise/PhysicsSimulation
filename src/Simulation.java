import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Simulation {
    Universe init_universe;
    double init_dt_real;
    double init_dt_sim;
    double init_time_step_counter;
    boolean init_is_playing;

    Universe universe;
    double dt_real;
    double dt_sim;
    double time_step_counter;

    Thread thread;

    Semaphore play_permit;
    ReentrantLock particles_pos_lock = new ReentrantLock();

    public Simulation(Universe universe, double dt_real, double dt_sim, Semaphore semaphore) {
        init_universe = universe;
        init_dt_real = dt_real;
        init_dt_sim = dt_sim;
        init_time_step_counter = 0;

        this.universe = new Universe(universe);

        this.dt_real = dt_real;
        this.dt_sim = dt_sim;
        time_step_counter = 0;

        thread = new Thread(this::time_step_wrapper);
    }

    void time_step_wrapper() {
        while (true) {
//                    if (time_step_counter > 1)
//                        continue;
            play_permit.acquireUninterruptibly();
            time_step();
            play_permit.release();

            try {
                Thread.sleep((long)dt_real);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public Simulation(Simulation s, Misc.CopyType copy_type) {
        copy(s, copy_type);
    }

    void copy(Simulation s, Misc.CopyType copy_type) {
        init_universe = s.init_universe;
        init_dt_real = s.init_dt_real;
        init_dt_sim = s.init_dt_sim;
        init_time_step_counter = s.init_time_step_counter;
        init_is_playing = s.init_is_playing;

        if (copy_type == Misc.CopyType.SHALLOW)
            universe = s.universe;
        else
            universe = new Universe(s.universe);

        dt_real = s.dt_real;
        dt_sim = s.dt_sim;
        time_step_counter = s.time_step_counter;
    }

    void reset() {
        thread.interrupt();

        universe = new Universe(init_universe);
        dt_real = init_dt_real;
        dt_sim = init_dt_sim;
        time_step_counter = init_time_step_counter;

        thread = new Thread(this::time_step_wrapper);
    }

    public void time_step() {
        // FIXME: Using subList instead of directly accessing the internal list references may cause unnecessary iteration,
        // FIXME: inflating the O(n^2) to 2*O(n^2)

        int i = 1;
        for (Particle pi : universe.particles) {
            for (Particle pj : universe.particles.subList(i, universe.particles.size())) {
                double dist = pi.distance(pj);

                double dir_pi_pj_x = pj.x - pi.x; // x-component of the (pi -> pj) vector
                double dir_pi_pj_y = pj.y - pi.y; // y-component of the (pi -> pj) vector

                double ux = dir_pi_pj_x / dist;   // x-component of the unit vector in the direction (pi -> pj)
                double uy = dir_pi_pj_y / dist;   // y-component of the unit vector in the direction (pi -> pj)

                // By physics,
                //
                // force = G * m1 * m2 / dist^2
                //
                // acc_m1 = force / m1 = G * m2 / dist^2

                double acc_factor = universe.acceleration_constant / dist / dist;

                double acc_pi = acc_factor * pj.mass;
                pi.ax = acc_pi * ux;             // x-component of the acceleration times the unit vector
                pi.ay = acc_pi * uy;             // y-component of the acceleration times the unit vector

                double acc_pj = -acc_factor * pi.mass;
                pj.ax = acc_pj * ux;             // x-component of the acceleration times the unit vector
                pj.ay = acc_pj * uy;             // y-component of the acceleration times the unit vector
            }
            i++;
        }

        particles_pos_lock.lock();

        for (Particle p : universe.particles) {
            p.vx += p.ax * dt_sim;
            p.vy += p.ay * dt_sim;

            double new_speed = Math.sqrt(p.vx * p.vx + p.vy * p.vy);

            if (new_speed > universe.max_speed) {
                p.vx = p.vx / new_speed * universe.max_speed;
                p.vy = p.vy / new_speed * universe.max_speed;
            }

            p.x += p.vx * dt_sim;
            p.y += p.vy * dt_sim;
        }

        particles_pos_lock.unlock();

        time_step_counter += 1;
    }
}
