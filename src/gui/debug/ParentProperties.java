package gui.debug;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Parent;

class ParentProperties {
    ReadOnlyBooleanProperty needsLayout;

    public ParentProperties(Parent p) {
        needsLayout = p.needsLayoutProperty();
    }
}
