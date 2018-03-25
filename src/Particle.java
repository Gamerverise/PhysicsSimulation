import javafx.scene.paint.Color;

class Particle {
    String name;
    double x;
    double y;
    double vx;
    double vy;
    double ax;
    double ay;
    double mass;
    double radius;
    Color color;

    Particle(String name, double x, double y, double vx, double vy, double ax, double ay, double mass, double radius, Color color) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
        this.mass = mass;
        this.radius = radius;
        this.color = color;
    }

    Particle(Particle p) {
        this.name = p.name;
        this.x = p.x;
        this.y = p.y;
        this.vx = p.vx;
        this.vy = p.vy;
        this.ax = p.ax;
        this.ay = p.ay;
        this.mass = p.mass;
        this.radius = p.radius;
        this.color = p.color;
    }

    double distance(Particle p) {
        double dx = p.x - x;
        double dy = p.y - y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    void rotate_velocity_abs(double degrees) {
        double radians = Math.PI / 180 * degrees;
        double velocity_scalar = Math.sqrt(vx*vx + vy*vy);
        vx = velocity_scalar * Math.cos(radians);
        vy = velocity_scalar * Math.sin(radians);
    }
}
