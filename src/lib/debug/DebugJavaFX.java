package lib.debug;

import javafx.scene.Node;
import javafx.scene.Parent;

import static javafx.scene.text.FontPosture.REGULAR;
import static javafx.scene.text.FontWeight.NORMAL;

import lib.text_io.*;

public class DebugJavaFX {

    public static String JavaFX_node_tree_debug(Node node, int tree_level) {
        String node_info = FormattedText.spaces(tree_level) + "ID: " + node.getId() + " Classes: " + node.getStyleClass().toString() + "\n";

        if (node instanceof Parent)
            for (Node child : ((Parent) node).getChildrenUnmodifiable())
                node_info += JavaFX_node_tree_debug(child, tree_level + 1);

        return node_info;
    }
}
