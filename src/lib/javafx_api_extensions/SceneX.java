package lib.javafx_api_extensions;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import lib.debug.MethodNameHack;

import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;

public class SceneX extends Scene {

    public SceneX(Region root) {
        super(root);
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

        region.resize(width, height);
    }
}
