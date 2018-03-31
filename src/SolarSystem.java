import javafx.scene.paint.Color;

public class SolarSystem {
    // Units are meters (m) and seconds (s)

    static double G = 6.67408e-11;    // pretend gravitation constant
    static double c = 299_792_458;    // speed of light in m/s
    static double max_speed = c - 1;    // naive max speed

    static double sun_mass = 1.989e30;
    static double sun_radius = 6.957e8;

    static double earth_sun_dist = 1.496e11;
    static double earth_tangential_speed = 3e4;
    static double earth_mass = 5.972e24;
    static double earth_radius = 6.371e6;

    double sun_volume = 4 / 3.0 * Math.PI * Math.pow(sun.radius, 3);

    double earth_volume = 4 / 3.0 * Math.PI * Math.pow(earth.radius, 3);


    // Original configuration

    static Particle sun = new Particle("sun", 0, 0, 0, 0, 0, 0, sun_mass, sun_radius, Color.rgb(0, 0, 0, 1));
    static Particle earth = new Particle("earth", earth_sun_dist, 0, 0, earth_tangential_speed, 0, 0, earth_mass, earth_radius, Color.rgb(0, 0, 0, 1));

    static public Universe solar_sys = new Universe(G, max_speed, sun, earth);

    static public Universe solar_sys_debug_1 = new Universe(solar_sys, Misc.CopyType.DEEP);
    static public Universe solar_sys_debug_2 = new Universe(solar_sys, Misc.CopyType.DEEP);

    {
        // Symmetry of original setup about x = y
        Particle earth_2 = solar_sys_debug_2.particles.get(0);
        earth_2.y = earth_sun_dist;
        earth_2.x = 0;
        earth_2.vy = earth_tangential_speed;
        earth_2.vx = 0;

        // Make an elliptical orbit
        solar_sys_debug_1.particles.get(0).rotate_velocity_abs(100);
    }

    static SimulationStatic solar_system_sim = new SimulationStatic(
            new Universe(solar_sys, Misc.CopyType.SHALLOW),
            1,       // dt_real = 1              (ms)
            60*8     // double dt_sim = 60*8     (s) = 8 min
    );

    static GameView solar_sys_game_view_sun_earth = new GameViewTwoParticles(sun, sun, earth, 0.95, Misc.Dimension.HALF_MAX);
}
