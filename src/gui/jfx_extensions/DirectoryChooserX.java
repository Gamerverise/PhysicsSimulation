package gui.jfx_extensions;

import javafx.stage.DirectoryChooser;

public class DirectoryChooserX {
    DirectoryChooser chooser;

    DirectoryChooserX(String title) {
        chooser = new DirectoryChooser();
        chooser.setTitle(title);
    }
}
