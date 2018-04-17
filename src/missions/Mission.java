package missions;

import particle_model.simulation.SimulationStatic;
import views.ParticleView;

public class Mission {

    public ParticleView init_view;
    public SimulationStatic init_sim;
    
    public Mission(double init_arena_width, double init_arena_height, SimulationStatic init_sim) {
        this.init_view = new ParticleView(init_arena_width, init_arena_height);
        this.init_sim = init_sim;
    }
}
