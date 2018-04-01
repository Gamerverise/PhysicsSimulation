package simulation;

import model.Universe;
import lib_2.Enums;

public class SimulationStatic {
    public Universe universe;
    public double dt_real;
    public double dt_sim;

    public SimulationStatic(Universe universe, double dt_real, double dt_sim) {

        this.universe = universe;
        this.dt_real = dt_real;
        this.dt_sim = dt_sim;
    }

    public SimulationStatic(SimulationStatic s, Enums.CopyType copy_type) {
        copy(s, copy_type);
    }

    public void copy(SimulationStatic s, Enums.CopyType copy_type) {
        if (copy_type == Enums.CopyType.SHALLOW)
            universe = s.universe;
        else
            universe = new Universe(s.universe, Enums.CopyType.DEEP);

        dt_real = s.dt_real;
        dt_sim = s.dt_sim;
    }
}
