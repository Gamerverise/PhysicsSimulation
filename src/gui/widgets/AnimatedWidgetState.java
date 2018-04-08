package gui.widgets;

import javafx.animation.AnimationTimer;

public class AnimatedWidgetState {

    AnimationTimer anim_timer;

    public AnimatedWidgetState(AnimatedWidget widget) {
        anim_timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                widget.draw_frame(now);
            }
        };
    }
}
