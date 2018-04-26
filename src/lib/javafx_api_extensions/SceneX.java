package lib.javafx_api_extensions;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import lib.debug.MethodNameHack;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public class SceneX extends Scene {

    public SceneX(Region root) {
        // The width and height are hardcoded to 100 as part of a JavaFX workaround hack
        super(root, 100, 100);
    }

    public void set_wh(double width, double height) {
        Parent root = getRoot();
        Region region;

        if (root instanceof Region)
            region = (Region) root;
        else {
            assert false : assert_msg(
                    this.getClass(),
                    new MethodNameHack() {}.method_name(),
                    BAD_CODE_PATH);

            region = null;
        }

        // Here we have a hack to work around JavaFX mess. We want to change the size of a scene after it is
        // created. Unfortunately, JavaFX does not allow doing so. However, a scene will take the preferred size
        // of its root when the scene's window is shown. Thus, to change the size of a scene after it is created,
        // we change the preferred size of its root.

        region.setPrefWidth(width);
        region.setPrefHeight(height);

        if (getWindow().isShowing())
            getRoot().requestLayout();
    }
}
