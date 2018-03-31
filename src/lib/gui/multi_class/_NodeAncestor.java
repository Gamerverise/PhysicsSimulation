package lib.gui.multi_class;

import javafx.scene.Node;
import javafx.scene.layout.Region;

import java_lang_extensions.multi_class._Ancestor;

public class _NodeAncestor extends _Ancestor<Node> {

    public _NodeAncestor(Node descendant) {
        super(descendant);
    }

    public Region get_nearest_region_ancestor() {
        while (true) {
            Node p = descendant.getParent();

            if (p == null)
                return null;

            if (p instanceof Region)
                return (Region)p;
        }
    }

    static public Class get_class() {
        return _NodeAncestor.class;
    }
}
