/* main game: planet builder with game element
(blowing up other people's planets / landing on planets and looting /
exploration / etc.)
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws InterruptedException {
        UI ui = new UI(stage);
    }
}
