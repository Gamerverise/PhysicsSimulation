package gui.widgets;

import javafx.scene.transform.NonInvertibleTransformException;
import lib.java_lang_extensions.function_types.FunctionR1_NonInvertibleExc;
import lib.javafx_api_extensions.GraphicsContextX;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import lib.data_structures.RunCommand;
import lib.debug.MethodNameHack;
import lib.widgets.AnimatedWidget;
import lib.widgets.AnimatedWidgetState;
import lib.widgets.Widget;
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

public class GravityGameWidget extends Widget implements AnimatedWidget {
    double min_radius_px = 1.1;

    AnimatedWidgetState anim_state;

    GameWidgetView init_gv;
    SimulationStatic init_simulation;

    Canvas canvas;

    GraphicsContextX gcx;
    GraphicsContext gc;

    SimulationDynamic simulation;

    public GravityGameWidget(GameWidgetView init_gv,
                             Universe universe,
                             double dt_real,
                             double dt_sim,
                             RunCommand init_run_command)
    {
        this(init_gv,
             new SimulationStatic(universe, dt_real, dt_sim, init_run_command),
             init_run_command);
    }

    public GravityGameWidget(GameWidgetView init_gv,
                             SimulationStatic init_simulation,
                             RunCommand init_run_command)
    {
        this.init_gv = init_gv;
        this.init_simulation = init_simulation;

        simulation = new SimulationDynamic(init_simulation, COPY_DEEP, init_run_command);

        anim_state = new AnimatedWidgetState(this);

        if (simulation.atomic_run_command.get() == RUN)
            anim_state.anim_timer.start();

        init_graphics_context();
    }

    public void hmm(){
        this.min_radius_px = min_radius_px;
        public double min_radius_px;
        public double min_radius;

        min_radius = 0;
        if (radius < min_radius)
            radius = min_radius;

    }

    public void update_min_radius(FunctionR1_NonInvertibleExc<Double, Double> inverter) {

        // To avoid distortion from scaling, we require that the rectangle of the model mapped into the viewport has
        // same aspect ratio as the viewport. (In other words, a circle will remain a circle.) As such, we may use
        // either the x- or y-axis to determine the minimum radius allowed. Currently, we use the x-axis.

        try {
            double inverted_radius = inverter.call(min_radius_px);
            double inverted_origin = inverter.call(0d);
            min_radius = Math.abs(inverted_radius - inverted_origin);
        } catch (NonInvertibleTransformException e) {
            min_radius = 0;
        }
    }

    update_min_radius(this::invert_x);


    public void layout() {
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);

        gcx.set_viewport(width, height);

        init_transform();
    }

    public void init_transform() {
        // Set origin to center of canvas, instead of top left
        gc.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);

        // Change positive y direction from down to up
        gc.scale(1, -1);

        if (init_gv != null)
            view(init_gv);

        gcx.update_min_radius(gcx::invert_x);
    }

    public void init_graphics_context() {
        canvas = new Canvas();

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

        draw_frame();
    }

    public void reset() {
        simulation.reset(init_simulation);
        init_graphics_context();
    }

    public void toggle_run_suspend() {
        if (simulation.atomic_run_command.get() == RUN) {
            simulation.suspend();
            anim_state.anim_timer.stop();
        } else if (simulation.atomic_run_command.get() == SUSPEND){
            anim_state.anim_timer.start();
            simulation.run();
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

    void draw_particle(Particle p) {
        gc.setFill(p.color);
        gcx.fill_circle(p.x, p.y, p.radius);
    }

    public void draw_frame() {
        draw_frame(0);
    }

    public void draw_frame(long now) {
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
