package particle_model.simulation;

import lib.tokens.enums.CopyType;
import lib.tokens.enums.RunCommand;
import particle_model.Particle;
import particle_model.Universe;

import static lib.tokens.enums.CopyType.COPY_DEEP;

public class SimulationStatic<T extends Particle> {
    public Universe universe;
    public double dt_real;
    public double dt_sim;
    RunCommand init_run_command;

    public SimulationStatic(Universe universe, double dt_real, double dt_sim, RunCommand init_run_command) {

        this.universe = universe;
        this.dt_real = dt_real;
        this.dt_sim = dt_sim;
        this.init_run_command = init_run_command;
    }

    public SimulationStatic(SimulationStatic<T> s, CopyType copy_type) {
        copy_in(s, copy_type);
    }

    public void copy_in(SimulationStatic<T> s, CopyType copy_type) {
        if (copy_type == CopyType.COPY_SHALLOW)
            universe = s.universe;
        else
            universe = new Universe(s.universe, COPY_DEEP);

        dt_real = s.dt_real;
        dt_sim = s.dt_sim;
        init_run_command = s.init_run_command;
    }
}
