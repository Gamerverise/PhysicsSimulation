package lib.widget;

import javafx.animation.AnimationTimer;

public interface AnimatedWidget {

    static void AnimatedWidget(AnimatedWidget anim_widget) {

        AnimationTimer anim_timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                anim_widget.draw_frame(now);
            }
        };

        anim_widget.AnimatedWidget(anim_timer);
    }

    void AnimatedWidget(AnimationTimer anim_timer);

    void draw_frame(long now);
}
