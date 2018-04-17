package missions;

import particle_model.simulation.SimulationStatic;
import views.ParticleView;

public class Mission {

    public ParticleView init_view;
    public SimulationStatic init_sim;
    
    public Mission(ParticleView particle_view, SimulationStatic init_sim) {
        this.init_view = particle_view;
        this.init_sim = init_sim;
    }
}
