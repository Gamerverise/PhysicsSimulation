package lib.javafx_api_extensions;

import javafx.collections.ObservableList;
import javafx.stage.Screen;

public class ScreenX {

    public static Screen get_screen(int screen_num) {
        // For the primary screen, pass 0 for screen_num

        // If screen_num is greater than the max screen number of the system, then the max screen number
        // will be used

        if (screen_num == 0) {
            return Screen.getPrimary();
        }

        ObservableList<Screen> screen_list = Screen.getScreens();

        if (screen_num >= screen_list.size())
            screen_num = screen_list.size() - 1;

        return screen_list.get(screen_num);
    }
}
