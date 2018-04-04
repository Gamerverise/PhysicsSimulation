package gui.stylesheets;

public class GravityGameStylesheets {
    static String URI_relative_prefix = ".idea/src/gui/css/AEMBOT";

    static public String URI_filenames[] = {
            /*
             **** IMPORTANT! These stylesheets must be listed in order of preference desired, from
             ****            lowest precedence to highest! (Lowest precedence is most generic stylesheet, and
             ****            highest precedence is most specific stylesheet.)
             ****            and lowest precedence is most generic stylesheet.)
             ****
            */

//            "Node",
//            "Parent",
            "Region",
//
//            "Pane",
//            "StackPane",
            "TilePane",

//            "Control",
//            "Labeled",
            "TitledPane",
            "Axis",
            "ValueAxis",
            "NumberAxis",

//            "Shape",
//            "Font",
//            "Text",

            ""      //  Special case for AEMBOT.css, because it does not follow the AEMBOT_ naming convention
    };

//    static String URI_suffix = ".css";
//
//    public static String[] get_URIs(String URI_absolute_prefix) {
//        String URIs[] = new String[URI_filenames.length];
//
//        int i;
//        for (i = 0; i < URI_filenames.length - 1; i++)
//            URIs[i] = URI_absolute_prefix + URI_relative_prefix + "_" + URI_filenames[i] + URI_suffix;
//        URIs[i] = URI_absolute_prefix + URI_relative_prefix + URI_suffix;
//
//        return URIs;
//    }
}
