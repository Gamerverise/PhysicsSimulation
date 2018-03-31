package gui.layout_options;

import javafx.scene.layout.Region;

public class CustomLayout {

    public Object object;
    public LayoutOption[] options;

    CustomLayout(Object object, LayoutOption... options) {
        this.object = object;
        this.options = options;
    }
}
