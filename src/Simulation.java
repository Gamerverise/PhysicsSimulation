public class Simulation {
    static Simulation solar_system = new Simulation(new Universe(SolarSystem.solar_system_universe),
            1,      // dt_real = 1              (ms)
            60*8    // double dt_sim = 60*8     (s) = 8 min
    );

    Simulation init_sim;

    Universe universe;
    double dt_real;
    double dt_sim;
    double time_step_counter;
    boolean is_playing;

    public Simulation(Universe universe, double dt_real, double dt_sim) {
        init_sim.init_sim = this;
        init_sim.universe = universe;
        init_sim.dt_real = dt_real;
        init_sim.dt_sim = dt_sim;
        init_sim.time_step_counter = 0;
        init_sim.is_playing = false;

        copy(init_sim, Misc.CopyType.DEEP);
    }

    public Simulation(Simulation s, Misc.CopyType copy_type) {
        copy(s, copy_type);
    }

    void copy(Simulation s, Misc.CopyType copy_type) {
        init_sim = s.init_sim;

        if (copy_type == Misc.CopyType.SHALLOW)
            universe = s.universe;
        else
            universe = new Universe(s.universe);

        dt_real = s.dt_real;
        dt_sim = s.dt_sim;
        time_step_counter = s.time_step_counter;
        is_playing = s.is_playing;
    }

    void reset() {
        copy(init_sim, Misc.CopyType.DEEP);
    }
}
