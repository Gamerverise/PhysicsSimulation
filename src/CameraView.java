class CameraView {
    double world_x;
    double world_y;
    double scale;

    CameraView() {
        world_x = 0;
        world_y = 0;
        scale = 1;
    }

    CameraView(double x, double y, double scale) {
        world_x = x;
        world_y = y;
        this.scale = scale;
    }

    void set_view(double x, double y, double scale) {
        world_x = x;
        world_y = y;
        this.scale = scale;
    }
    // set_view is for viewing a particle:
    //     * the world coordinates of the view are set to the particles coordinates, and
    //     * and the view's scale is zoomed relative to the particle's diameter
    //
    // zoom of 1 means that the particle's diameter (in px) equals the canvas's height

    public void set_view(Particle p, double zoom, double canvas_height) {
        view.set_view(p.x, p.y, canvas_height / 2 / p.radius * zoom);
    }

    // set_view_scale is for zooming the view's scale relative to the distance between two particles
    //
    // zoom of 1 means that the distance (in px) between the two particles equals the canvas's height

    public void set_view_scale(Particle p, Particle q, double zoom, double canvas_height) {
        view.scale = canvas_height / p.distance(q) * zoom;
    }
}
