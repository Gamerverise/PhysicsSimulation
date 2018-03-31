public class SimulationStatic {
    Universe universe;
    double dt_real;
    double dt_sim;

    public SimulationStatic(Universe universe, double dt_real, double dt_sim) {

        this.universe = universe;
        this.dt_real = dt_real;
        this.dt_sim = dt_sim;
    }

    public SimulationStatic(SimulationStatic s, Misc.CopyType copy_type) {
        copy(s, copy_type);
    }

    void copy(SimulationStatic s, Misc.CopyType copy_type) {
        if (copy_type == Misc.CopyType.SHALLOW)
            universe = s.universe;
        else
            universe = new Universe(s.universe, Misc.CopyType.DEEP);

        dt_real = s.dt_real;
        dt_sim = s.dt_sim;
    }
}
