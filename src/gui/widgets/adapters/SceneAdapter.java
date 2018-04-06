package gui.widgets.adapters;

import gui.widgets.Widget;
import javafx.scene.layout.Region;

public class SceneAdapter extends Widget {

    Region scene_root;

    public SceneAdapter(Widget... children) {
        super(children);
    }

    public void layout_children() {
    }

    public void draw_self() {
    }
}
