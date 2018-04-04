package gui.widgets;

import lib.javafx_api_extensions.GraphicsContextX;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import lib.data_structures.RunCommand;
import lib.debug.MethodNameHack;
import model.Particle;
import model.Universe;
import simulation.SimulationDynamic;
import simulation.SimulationStatic;
import gui.widgets.widget_support.GameWidgetView;
import gui.widgets.widget_support.GameWidgetViewParticle;
import gui.widgets.widget_support.GameWidgetViewTwoParticles;

import static lib.debug.Debug.assert_msg;
import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.javafx_api_extensions.javafx_support.Enums.ScaleOp2D;
import static lib.data_structures.CopyType.COPY_DEEP;
import static lib.data_structures.RunCommand.*;

public class GravityGameWidget extends Region {
    double min_radius_px = 1.1;

    GameWidgetView init_gv;
    SimulationStatic init_simulation;

    Canvas canvas;

    GraphicsContextX gcx;
    GraphicsContext gc;
    AnimationTimer anim_timer;

    SimulationDynamic simulation;

    public GravityGameWidget(double width, double height,
                             GameWidgetView init_gv,
                             Universe universe,
                             double dt_real,
                             double dt_sim,
                             RunCommand init_run_command)
    {
        this(width, height, init_gv, new SimulationStatic(universe, dt_real, dt_sim, init_run_command), init_run_command);
    }

    public GravityGameWidget(double width, double height,
                             GameWidgetView init_gv,
                             SimulationStatic init_simulation,
                             RunCommand init_run_command)
    {
        this.init_gv = init_gv;
        this.init_simulation = init_simulation;

        canvas = new Canvas(width, height);
        simulation = new SimulationDynamic(init_simulation, COPY_DEEP, null);
        init_graphics_context();
    }

    public void init_graphics_context() {
        gc = canvas.getGraphicsContext2D();
        gcx = new GraphicsContextX(gc, min_radius_px);

        gc.setFont(new Font(30));
        gc.setFill(Color.rgb(255, 0, 0, 0.5));

        // Set origin to center of canvas, instead of top left
        gc.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);

        // Change positive y direction from down to up
        gc.scale(1, -1);

        if (init_gv != null)
            view(init_gv);

        anim_timer = new AnimationTimer() {
            public void handle(long now) {
                redraw();
            }
        };

        redraw();
    }

    public void reset() {
        anim_timer.stop();
        simulation.reset(init_simulation);
        init_graphics_context();
    }

    public void toggle_run_suspend() {
        if (simulation.atomic_run_command.get() == RUN) {
            simulation.suspend();
            anim_timer.stop();
        } else if (simulation.atomic_run_command.get() == SUSPEND){
            anim_timer.start();
            simulation.run();
        } else
            assert false : assert_msg(
                    this.getClass(),
                    new MethodNameHack(){}.method_name(),
                    BAD_CODE_PATH);
    }

    // view_particle
    //     Change the current view (aka transform) so that:
    //
    //         (center_px, center_py) = (canvas_origin_x, canvas_origin_x)
    //     and
    //         diameter_px(p, q) = zoom * canvas_dim

    public void view_particle(Particle p, double zoom, ScaleOp2D scale_op) {
        double scale_rel = 1 / p.radius / 2 * zoom;
        gc.translate(p.x, p.y);
        gcx.scale(scale_rel, scale_op);
    }

    // FIXME: LOW PRIORITY: Old comment
    // view_particle
    //     Scale the current view (aka transform) so that:
    //
    //         dist_px(p, q) = zoom * canvas_dim

    public void view_two_particles(Particle center, Particle p, Particle q, double zoom, ScaleOp2D scale_op) {
        double scale_rel = 1 / p.distance(q) * zoom;
        gc.translate(center.x, center.y);
        gcx.scale(scale_rel, scale_op);
    }

    public void view(GameWidgetView gv) {
        if (gv instanceof GameWidgetViewParticle) {
            GameWidgetViewParticle v = (GameWidgetViewParticle) gv;
            view_particle(v.p, v.zoom, v.scale_op);
        } else if (gv instanceof GameWidgetViewTwoParticles) {
            GameWidgetViewTwoParticles v = (GameWidgetViewTwoParticles) gv;
            view_two_particles(v.center, v.p, v.q, v.zoom, v.scale_op);
        } else
            assert false : assert_msg(
                    this.getClass(),
                    new MethodNameHack(){}.method_name(),
                    BAD_CODE_PATH);
    }

    public void view(GameWidgetViewParticle gv) {
        view_particle(gv.p, gv.zoom, gv.scale_op);
    }

    public void view(GameWidgetViewTwoParticles gv) {
        view_two_particles(gv.center, gv.p, gv.q, gv.zoom, gv.scale_op);
    }

    void draw_particle(Particle p) {
        gc.setFill(p.color);
        gcx.fill_circle(p.x, p.y, p.radius);
    }

    public void redraw() {
//        gc.clearRect(0, 0, canvas_width, canvas_height);
//        gc.setFill(Color.BLACK);

        gc.clearRect(0, 0, 100, 100);

        simulation.xy_data_rw_lock.lock();

        for (Particle p : simulation.universe.particles)
            draw_particle(p);

        simulation.xy_data_rw_lock.unlock();

        switch (simulation.atomic_run_command.get()) {
            case RUN:
                gc.fillText("Playing", 5, 50);
                break;
            case SUSPEND:
                gc.fillText("Paused", 5, 50);
                break;
            case EXIT:
                gc.fillText("Exited", 5, 50);
                break;
        }
    }
}
