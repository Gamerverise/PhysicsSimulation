package gui.widget;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lib.debug.MethodNameHack;
import lib.graphics_context.CanvasRenderingGraphicsContext;
import lib.graphics_device.CanvasGraphicsDevice;
import lib.javafx_api_extensions.AffineX;
import lib.javafx_api_extensions.GraphicsContextX;
import lib.javafx_api_extensions.javafx_api_extensions_support.ScaleOp;
import lib.render.Viewport;
import lib.tokens.enums.RunCommand;
import lib.transforms.TransformMulti;
import lib.widget.AnimatedWidget;
import lib.widget.Widget;
import missions.Mission;
import particle_model.Particle;
import particle_model.Universe;
import particle_model.simulation.SimulationDynamic;
import particle_model.simulation.SimulationStatic;
import views.ParticleView;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;
import static lib.tokens.enums.CopyType.COPY_DEEP;
import static lib.tokens.enums.RunCommand.RUN;
import static lib.tokens.enums.RunCommand.SUSPEND;

public class GravityGameWidget extends Widget implements AnimatedWidget {
    double min_radius_px = 1.1;

    AnimationTimer anim_timer;

    Mission init_mission;

    CanvasGraphicsDevice canvas_device;
    Canvas canvas;

    CanvasRenderingGraphicsContext rgc;
    GraphicsContextX gcx;
    GraphicsContext gc;

    Viewport viewport;

    SimulationDynamic simulation;

    public void AnimatedWidget(AnimationTimer anim_timer) {
        this.anim_timer = anim_timer;
    }

    public GravityGameWidget(double arena_width,
                             double arena_height,
                             ParticleView init_view,
                             Universe universe,
                             double dt_real,
                             double dt_sim,
                             RunCommand init_run_command)
    {
        this(new Mission(init_view,
                         new SimulationStatic(universe, dt_real, dt_sim, init_run_command)),
             init_run_command);
    }

    public GravityGameWidget(Mission init_mission,
                             RunCommand init_run_command)
    {
        AnimatedWidget.AnimatedWidget(this);

        this.init_mission = init_mission;

        simulation = new SimulationDynamic(init_mission.init_sim, COPY_DEEP, init_run_command);

        if (simulation.atomic_run_command.get() == RUN)
            anim_timer.start();

        init_graphics_context();
    }

    public void init_graphics_context() {
        canvas_device = new CanvasGraphicsDevice();
        canvas = canvas_device.canvas;

        rgc = new CanvasRenderingGraphicsContext(canvas_device);
        gcx = rgc.device.gcx;
        gc = gcx.gc;

        viewport = rgc.viewport;

        gc.setFont(new Font(30));
        gc.setFill(Color.rgb(255, 0, 0, 0.5));

        if (init_mission.init_view != null)
            if (init_mission.init_view.transform != null)
                gc.setTransform(init_mission.init_view.transform);

        draw_frame();
    }

    public void layout() {
        canvas_device.set_device_geometry(x, y, width, height);
        viewport.set_basis_geometry(0, 0, width / height, 1);

        if (init_mission.init_view != null)
            if (init_mission.init_view.transform != null)
                gc.setTransform(init_mission.init_view.transform);
    }

    public void reset() {
        simulation.reset(init_mission.init_sim);
        init_graphics_context();
    }

    public void toggle_run_suspend() {
        if (simulation.atomic_run_command.get() == RUN) {
            simulation.suspend();
            anim_timer.stop();
        } else if (simulation.atomic_run_command.get() == SUSPEND) {
            anim_timer.start();
            simulation.run();
        } else
            assert false : assert_msg(
                    this.getClass(),
                    new MethodNameHack() {}.method_name(),
                    BAD_CODE_PATH);
    }

    public void view(AffineX affine) {
        gcx.gc.transform(affine);
    }

    public void view_particle(TransformMulti transform, Particle p, double zoom, ScaleOp scale_op)
    {
        ParticleView.view_particle(gcx, p, zoom, canvas.getWidth(), canvas.getHeight(), scale_op);
    }

    public void view_two_particles(TransformMulti transform, Particle center, Particle p, Particle q,
                                   double zoom, ScaleOp scale_op)
    {
        ParticleView.view_two_particles(gcx, center, p, q, zoom,
                canvas.getWidth(), canvas.getHeight(), scale_op);
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
