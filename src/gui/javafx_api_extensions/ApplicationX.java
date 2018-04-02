package gui.javafx_api_extensions;

import gui.stylesheets.GravityGameStylesheets;
import javafx.application.Application;
import javafx.scene.Scene;

public abstract class ApplicationX extends Application {
    public static Scene dummy_scene_to_load_and_hold_stylesheet;

    public ApplicationX() {}

    public ApplicationX(String stylesheet_URI) {
        dummy_scene_to_load_and_hold_stylesheet
                .getStylesheets().addAll(GravityGameStylesheets.URI_filenames);
    }
}
