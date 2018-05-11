package GravityGame;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class GravityGameMediaStore {

    static String user_dir = System.getProperty("user.dir");

    static Image ship_img = new Image(user_dir + ".idea/data_files/Map/RealTimeMap_Test.png");

    static Image sun_img = new Image(user_dir + ".idea/data_files/Map/Robot.png");
    static Image earth_img = new Image(user_dir + ".idea/data_files/Map/Robot.png");
    static Image moon_img = new Image(user_dir + ".idea/data_files/Map/Robot.png");

    static Media bg_music_mp3 = new Media(user_dir + ".idea/data_files/Sounds/bark_sound.mp3");
}
