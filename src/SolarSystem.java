import javafx.scene.paint.Color;

public class SolarSystem {
    static double G = 6.67408e-11;    // pretend gravitation constant
    static double c = 299_792_458;    // speed of light in m/s
    static double max_speed = c - 1;    // naive max speed

    static double sun_mass = 1.989e30;
    static double sun_radius = 6.957e8;

    static double earth_sun_dist = 1.496e11;
    static double earth_tangential_speed = 3e4;
    static double earth_mass = 5.972e24;
    static double earth_radius = 6.371e6;

    static Particle sun = new Particle("sun", 0, 0, 0, 0, 0, 0, sun_mass, sun_radius, Color.rgb(0, 0, 0, 1));
    static Particle earth = new Particle("earth", earth_sun_dist, 0, 0, earth_tangential_speed, 0, 0, earth_mass, earth_radius, Color.rgb(0, 0, 0, 1));

    static public Universe solar_system_universe = new Universe(sun, earth);
}
