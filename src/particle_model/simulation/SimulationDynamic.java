package particle_model.simulation;

import lib.debug.MethodNameHack;
import lib.tokens.enums.CopyType;
import lib.tokens.enums.RunCommand;
import particle_model.Particle;
import particle_model.Universe;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;
import static lib.tokens.enums.CopyType.COPY_DEEP;
import static lib.tokens.enums.RunCommand.*;

public class SimulationDynamic<T extends Particle> extends SimulationStatic<T> {
    Thread thread;

    public AtomicReference<RunCommand> atomic_run_command;
    public ReentrantLock suspend_lock;
    public ReentrantLock xy_data_rw_lock;

    double time_step_counter;

    public SimulationDynamic(Universe<T> universe, double dt_real, double dt_sim, RunCommand init_run_command) {
        super(universe, dt_real, dt_sim, init_run_command);

        time_step_counter = 0;

        atomic_run_command = new AtomicReference<>(init_run_command);
        suspend_lock = new ReentrantLock();
        xy_data_rw_lock = new ReentrantLock();

        birth_thread();
    }

    public SimulationDynamic(SimulationStatic<T> s, RunCommand override) {
        this(s.universe, s.dt_real, s.dt_sim,
                override == null ? s.init_run_command : override);
    }

    public SimulationDynamic(SimulationStatic<T> s, CopyType copy_type, RunCommand override) {
        this(new SimulationStatic<>(s, copy_type), override);
    }

    public void time_step_wrapper() {

        while (true) {
            RunCommand cur_command = atomic_run_command.get();

            switch (cur_command) {
                case SUSPEND:
                    suspend_lock.lock();
                    suspend_lock.unlock();
                    break;
                case EXIT:
                    return;
            }

            time_step();

            try {
                Thread.sleep((long) dt_real);
            } catch (InterruptedException e) {
                assert false : assert_msg(
                        this.getClass(),
                        new MethodNameHack(){}.method_name(),
                        BAD_CODE_PATH);
            }
        }
    }

    public void birth_thread() {
        do_run_command(init_run_command);
        thread = new Thread(this::time_step_wrapper, "Simulation Thread");
        thread.start();
    }

    public void do_run_command(RunCommand run_command) {
        switch (run_command) {
            case RUN:
                run();
                break;
            case SUSPEND:
                suspend();
                break;
            case EXIT:
                exit();
                break;
        }
    }

    public void run() {
        atomic_run_command.set(RUN);
        suspend_lock.unlock();
    }

    public void suspend() {
        suspend_lock.lock();
        atomic_run_command.set(SUSPEND);
    }

    public void exit() {
        if (atomic_run_command.get() == EXIT)
            return;

        atomic_run_command.set(EXIT);
        suspend_lock.unlock();

        try {
            thread.join();
        } catch (InterruptedException e) {
            assert false : assert_msg(
                    this.getClass(),
                    new MethodNameHack(){}.method_name(),
                    BAD_CODE_PATH);
        }
    }

    public void reset(SimulationStatic<T> init_sim) {
        exit();

        copy_in(init_sim, COPY_DEEP);
        time_step_counter = 0;

        birth_thread();
    }

    public void time_step() {
        // FIXME: LOW PRIORITY: Using subList instead of directly accessing the internal list references may
        // FIXME: LOW PRIORITY: cause unnecessary iteration, inflating the O(n^2) to 2*O(n^2)

        int i = 1;
        for (T pi : universe.contents) {
            for (T pj : universe.contents.subList(i, universe.contents.size())) {
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

        for (T p : universe.contents) {
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
