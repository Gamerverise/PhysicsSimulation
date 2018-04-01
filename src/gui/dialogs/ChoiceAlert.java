package gui.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ChoiceAlert {
    Alert alert;

    ChoiceAlert(Alert.AlertType type, String title, String question, String... button_names) {
        alert = new Alert(type);

        ButtonType[] button_types = new ButtonType[button_names.length];

        int i;
        for (i = 0; i < button_names.length; i++)
            button_types[i] = new ButtonType(button_names[i]);

        alert.getButtonTypes().setAll(button_types);

        Button last_button = (Button) alert.getDialogPane().lookupButton(button_types[i-1]);
        last_button.setDefaultButton(true);

        alert.setTitle(title);
        alert.setHeaderText(question);
    }

    Optional<ButtonType> showAndWait() {
        return alert.showAndWait();
    }
}
