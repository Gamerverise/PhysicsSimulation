package simulation;

import lib_2.Enums;
import model.Particle;
import model.Universe;
import lib_2.RuntimeErrors;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class SimulationDynamic extends SimulationStatic {
    Thread thread;

    public Semaphore run_suspend_permit;
    public ReentrantLock xy_data_rw_lock;

    double time_step_counter;

    public SimulationDynamic(Universe universe, double dt_real, double dt_sim) {
        super(universe, dt_real, dt_sim);
        shared_construction();
    }

    public SimulationDynamic(SimulationStatic s, Enums.CopyType copy_type) {
        super(s, copy_type);
        shared_construction();
    }

    public void shared_construction() {
        time_step_counter = 0;

        // FIXME: How many permits? 0 or -1?
        run_suspend_permit = new Semaphore(0);
        xy_data_rw_lock = new ReentrantLock();

        thread = new Thread(this::time_step_wrapper);

        thread.start();
    }

    public void time_step_wrapper() {

        while (true) {
            try {
                run_suspend_permit.acquire();
            } catch (InterruptedException e) {
                return;
            }

            time_step();

            try {
                Thread.sleep((long) dt_real);
            } catch (InterruptedException e) {
                assert false : "SimulationDynamic.time_step_wrapper: " + RuntimeErrors.BAD_CODE_PATH;
            }

            run_suspend_permit.release();
        }
    }

    public void run() {
        run_suspend_permit.release();
    }

    public void suspend() {
        run_suspend_permit.acquireUninterruptibly();
    }

    public void exit() {
        run_suspend_permit.acquireUninterruptibly();
        thread.interrupt();
    }

    public void reset(SimulationDynamic sim) {
        exit();
        copy(sim, Enums.CopyType.DEEP);
        time_step_counter = 0;
        // FIXME: Are the lock and semaphore in the correct state at this point?
        thread = new Thread(this::time_step_wrapper);
        thread.start();
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

        xy_data_rw_lock.lock();

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

        xy_data_rw_lock.unlock();

        time_step_counter += 1;
    }
}
