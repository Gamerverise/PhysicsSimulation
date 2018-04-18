package missions;

import lib.render.Viewport;
import particle_model.simulation.SimulationStatic;

public class Mission {

    public SimulationStatic init_sim;
    public Viewport init_view;

    public Mission(SimulationStatic init_sim, Viewport init_view) {
        this.init_sim = init_sim;
        this.init_view = init_view;
    }
}
