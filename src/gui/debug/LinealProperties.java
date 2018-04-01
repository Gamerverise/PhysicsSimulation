package gui.debug;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Region;

class LinealProperties {
    NodeProperties node_props;
    ParentProperties parent_props;
    RegionProperties region_props;

    public LinealProperties(Node n) {
        node_props = new NodeProperties(n);

        if (n instanceof Region) {
            Region r = (Region)n;
            parent_props = new ParentProperties(r);
            region_props = new RegionProperties(r);
            return;
        }

        if (n instanceof Parent) {
            Parent p = (Parent)n;
            parent_props = new ParentProperties(p);
            return;
        }
    }
}
