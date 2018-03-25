class View {
    double world_x;
    double world_y;
    double scale;

    View() {
        world_x = 0;
        world_y = 0;
        scale = 1;
    }

    View(double x, double y, double scale) {
        world_x = x;
        world_y = y;
        this.scale = scale;
    }

    void set_view(double x, double y, double scale) {
        world_x = x;
        world_y = y;
        this.scale = scale;
    }
}
