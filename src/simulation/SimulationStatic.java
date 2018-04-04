package simulation;

import lib.data_structures.CopyType;
import lib.data_structures.RunCommand;
import model.Universe;

import static lib.data_structures.CopyType.COPY_DEEP;

public class SimulationStatic {
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

    public SimulationStatic(SimulationStatic s, CopyType copy_type) {
        copy(s, copy_type);
    }

    public void copy(SimulationStatic s, CopyType copy_type) {
        if (copy_type == CopyType.COPY_SHALLOW)
            universe = s.universe;
        else
            universe = new Universe(s.universe, COPY_DEEP);

        dt_real = s.dt_real;
        dt_sim = s.dt_sim;
        init_run_command = s.init_run_command;
    }
}
