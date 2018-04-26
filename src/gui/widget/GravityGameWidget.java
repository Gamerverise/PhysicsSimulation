package gui.widget;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lib.debug.MethodNameHack;
import lib.graphics.CustomGraphicsContext;
import lib.graphics_device.CanvasGraphicsDevice;
import lib.javafx_api_extensions.AffineX;
import lib.render.Viewport;
import lib.render.graphics_context.CanvasRenderingGraphicsContext;
import lib.tokens.enums.RunCommand;
import lib.widget.AnimatedWidget;
import lib.widget.AnimatedWidgetMulti;
import lib.widget.Widget;
import missions.Mission;
import particle_model.RenderableParticle;
import particle_model.Universe;
import particle_model.simulation.SimulationDynamic;
import particle_model.simulation.SimulationStatic;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;
import static lib.java_api_extensions.MathX.max;
import static lib.render.graphics_context.RenderingGraphicsContext.MODEL_MODE.MODEL_MODE;
import static lib.tokens.enums.CopyType.COPY_DEEP;
import static lib.tokens.enums.RunCommand.RUN;
import static lib.tokens.enums.RunCommand.SUSPEND;

public class GravityGameWidget extends Widget implements AnimatedWidget {
    double min_radius_px = 1.1;

    public AnimatedWidgetMulti anim_multi;

    Mission init_mission;

    CanvasGraphicsDevice canvas_device;
    Canvas canvas;

    CanvasRenderingGraphicsContext rgc;
    CustomGraphicsContext cgc;
    GraphicsContext gc;

    Viewport viewport;

    SimulationDynamic simulation;

    double arrow_scale;

    public GravityGameWidget(Universe universe,
                             double dt_real,
                             double dt_sim,
                             RunCommand init_run_command,
                             Viewport init_view)
    {
        this(new Mission(new SimulationStatic<>(universe, dt_real, dt_sim, init_run_command), init_view),
             init_run_command);
    }

    public GravityGameWidget(Mission init_mission, RunCommand init_run_command) {
        anim_multi = new AnimatedWidgetMulti(this);

        this.init_mission = init_mission;

        simulation = new SimulationDynamic(init_mission.init_sim, COPY_DEEP, init_run_command);

        double view_width = init_mission.init_view.get_width_model();
        double view_height = init_mission.init_view.get_height_model();

        arrow_scale = max(view_height, view_height) / init_mission.init_sim.universe.max_starting_velocity;

        if (simulation.atomic_run_command.get() == RUN)
            anim_multi.anim_timer.start();

        init_graphics_context();
    }

    public void init_graphics_context() {
        canvas_device = new CanvasGraphicsDevice();
        canvas = canvas_device.canvas;

        rgc = new CanvasRenderingGraphicsContext(canvas_device);
        cgc = rgc.device.cgc;
        gc = cgc.gc;

        if (init_mission.init_view != null)
            rgc.viewport.init(init_mission.init_view);

        viewport = rgc.viewport;

        gc.setFont(new Font(30));
        gc.setFill(Color.rgb(255, 0, 0, 0.5));
    }

    public void layout(boolean match_aspect_ratios) {
        canvas_device.set_device_geometry(x, y, width, height);

        if (match_aspect_ratios) {
            if (canvas_device.get_width_px() != 0 && canvas_device.get_height_px() != 0) {
                double canvas_aspect_ratio = canvas_device.get_aspect_ratio();
                double viewport_width_model = viewport.get_width_model();
                double viewport_height_model = viewport.get_height_model();
                double viewport_aspect_ratio = viewport_width_model / viewport_height_model;

                if (viewport_aspect_ratio > canvas_aspect_ratio)
                    viewport.set_basis_dimensions(viewport_width_model, viewport_width_model / canvas_aspect_ratio);
                else
                    viewport.set_basis_dimensions(viewport_height_model * canvas_aspect_ratio, viewport_height_model);

                // FIXME: Is this draw_frame needed?
                draw_frame();
            }
        }
    }

    public void layout() {
        layout(true);
    }

    public void draw_self() {
        draw_frame();
    }

    public void reset() {
        simulation.reset(init_mission.init_sim);
        init_graphics_context();
        layout_before_next_frame();
    }

    public void toggle_run_suspend() {
        RunCommand command = simulation.atomic_run_command.get();

        if (command == RUN) {
            simulation.suspend();
            anim_multi.anim_timer.stop();
            simulation.atomic_run_command.set(SUSPEND);
            draw_frame();
        } else if (command == SUSPEND) {
            anim_multi.anim_timer.start();
            simulation.run();
            simulation.atomic_run_command.set(RUN);
        } else
            assert false : assert_msg(
                    this.getClass(),
                    new MethodNameHack() {}.method_name(),
                    BAD_CODE_PATH);
    }

    public void view(AffineX affine) {
        cgc.gc.transform(affine);
    }

    public void draw_frame() {
        draw_frame(0);
    }

    public void draw_frame(long now) {
//        gc.clearRect(0, 0, canvas_width, canvas_height);
//        gc.setFill(Color.BLACK);

        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0, 0, width, height);

        simulation.xy_data_rw_lock.lock();
        {
            rgc.begin_render(MODEL_MODE);
            {
                for (RenderableParticle p : simulation.universe.contents)
                    p.draw(rgc, arrow_scale);

            }
            rgc.end_render();
//
//            rgc.cache_model_to_device_map();
//
//            rgc.begin_render(DEVICE_MODE);
//            {
//                for (RenderableParticle p : simulation.universe.contents)
//                    p.draw_overlay(rgc, 1);
//
//            }
//            rgc.end_render();

        } simulation.xy_data_rw_lock.unlock();

        gc.clearRect(0, 0, 100, 100);

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
