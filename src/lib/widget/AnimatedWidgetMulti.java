package lib.widget;

import javafx.animation.AnimationTimer;

public class AnimatedWidgetMulti {

    public AnimationTimer anim_timer;

    public AnimatedWidgetMulti(AnimatedWidget anim_widget) {

        anim_timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                anim_widget.draw_frame(now);
            }
        };
    }
}
