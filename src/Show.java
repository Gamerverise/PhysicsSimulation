public class Show {
    static Show solar_system_show = new Show();

    double dist_earth_sun_px = canvas_width / 2 * 0.50;
    double dist_inset_px = 20;      // So the earth won't be at the exact edge

    // Register screen coordinates and simulation coordinates

    double canvas_width = 1305;
    double canvas_height = 795;
    double canvas_aspect_ratio = canvas_width / canvas_height;

    Simulation simulation;
    View view;

    public Show(double view_origin_x, double view_origin_y, double view_scale,
                Universe universe, double dt_real, double dt_sim)
    {
        simulation = new Simulation(universe, dt_real, dt_sim);
    }
}
