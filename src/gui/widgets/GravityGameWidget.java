package gui.widgets;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lib.debug.MethodNameHack;
import lib.javafx_api_extensions.AffineX;
import lib.javafx_api_extensions.GraphicsContextX;
import lib.tokens.enums.RunCommand;
import lib.widget.AnimatedWidget;
import lib.widget.Widget;
import particle_model.Particle;
import particle_model.Universe;
import particle_model.simulation.SimulationDynamic;
import particle_model.simulation.SimulationStatic;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;
import static lib.tokens.enums.CopyType.COPY_DEEP;
import static lib.tokens.enums.RunCommand.RUN;
import static lib.tokens.enums.RunCommand.SUSPEND;

public class GravityGameWidget extends Widget implements AnimatedWidget {
    double min_radius_px = 1.1;

    AnimationTimer anim_timer;

    AffineX init_view;
    SimulationStatic init_simulation;

    Canvas canvas;

    GraphicsContextX gcx;
    GraphicsContext gc;

    SimulationDynamic simulation;

    public GravityGameWidget(AffineX init_view,
                             Universe universe,
                             double dt_real,
                             double dt_sim,
                             RunCommand init_run_command)
    {
        this(init_view,
             new SimulationStatic(universe, dt_real, dt_sim, init_run_command),
             init_run_command);
    }

    public void AnimatedWidget(AnimationTimer anim_timer) {
        this.anim_timer = anim_timer;
    }

    public GravityGameWidget(AffineX init_view,
                             SimulationStatic init_simulation,
                             RunCommand init_run_command)
    {
        AnimatedWidget.AnimatedWidget(this);

        this.init_view = init_view;
        this.init_simulation = init_simulation;

        simulation = new SimulationDynamic(init_simulation, COPY_DEEP, init_run_command);

        if (simulation.atomic_run_command.get() == RUN)
            anim_timer.start();

        init_graphics_context();
    }

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

        if (init_view != null)
            view(init_view);
    }

    public void init_graphics_context() {
        canvas = new Canvas();

        gc = canvas.getGraphicsContext2D();
        gcx = new GraphicsContextX(gc);

        gc.setFont(new Font(30));
        gc.setFill(Color.rgb(255, 0, 0, 0.5));

        // Set origin to center of canvas, instead of top left
        gc.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);

        // Change positive y direction from down to up
        gc.scale(1, -1);

        if (init_view != null)
            view(init_view);

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
