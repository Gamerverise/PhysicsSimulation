package missions;

import javafx.scene.paint.Color;
import lib.javafx_api_extensions.AffineX;
import lib.javafx_api_extensions.AffineX_Clash;
import lib.javafx_api_extensions.javafx_api_extensions_support.ScaleOp;
import particle_model.Particle;
import particle_model.Universe;
import particle_model.simulation.SimulationStatic;
import views.ParticleView;

import static lib.tokens.enums.CopyType.COPY_DEEP;
import static lib.tokens.enums.CopyType.COPY_SHALLOW;
import static lib.tokens.enums.RunCommand.SUSPEND;
import static views.ParticleView.VIEW_TWO_PARTICLES.VIEW_TWO_PARTICLES;

public class SolarSystem {
    // Units are meters (m) and seconds (s)

    public static double G = 6.67408e-11;    // pretend gravitation constant
    public static double c = 299_792_458;    // speed of light in m/s
    public static double max_speed = c - 1;    // naive max speed

    public static double sun_mass = 1.989e30;
    public static double sun_radius = 6.957e8;

    public static double earth_sun_dist = 1.496e11;
    public static double earth_tangential_speed = 3e4;
    public static double earth_mass = 5.972e24;
    public static double earth_radius = 6.371e6;

    double sun_volume = 4 / 3.0 * Math.PI * Math.pow(sun.radius, 3);

    double earth_volume = 4 / 3.0 * Math.PI * Math.pow(earth.radius, 3);


    // Original configuration

    public static Particle sun = new Particle("sun", 0, 0, 0, 0, 0, 0, sun_mass, sun_radius, Color.rgb(0, 0, 0, 1));
    public static Particle earth = new Particle("earth", earth_sun_dist, 0, 0, earth_tangential_speed, 0, 0, earth_mass, earth_radius, Color.rgb(0, 0, 0, 1));

    public static Universe solar_sys = new Universe(G, max_speed, sun, earth);

    public static Universe solar_sys_debug_1 = new Universe(solar_sys, COPY_DEEP);
    public static Universe solar_sys_debug_2 = new Universe(solar_sys, COPY_DEEP);

    public static AffineX solar_system_init_view_sun_earth;

    public static Mission solar_system_mission;


    public static SimulationStatic solar_system_sim = new SimulationStatic(
            new Universe(solar_sys, COPY_SHALLOW),
            1,       // dt_real = 1              (ms)
            60*8,     // double dt_sim = 60*8     (s) = 8 min
            SUSPEND
    );

    public static Mission Mission(double arena_width, double arena_height) {

        return new Mission(
                new ParticleView(VIEW_TWO_PARTICLES, sun, sun, earth, 0.95,
                        arena_width, arena_height, ScaleOp.HALF_MAX),
                solar_system_sim
        );
    }

    static {
        // Symmetry of original setup about x = y
        Particle earth_2 = solar_sys_debug_2.particles.get(0);
        earth_2.y = earth_sun_dist;
        earth_2.x = 0;
        earth_2.vy = earth_tangential_speed;
        earth_2.vx = 0;

        // Make an elliptical orbit
        solar_sys_debug_1.particles.get(0).rotate_velocity_abs(100);
    }
}
