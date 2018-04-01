package gui.debug;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;

class RegionProperties {
    Background background;
    Border border;
    BooleanProperty cacheShape;
    BooleanProperty            centerShape;
    double                     height;
    Insets insets;
    double                     maxHeight;
    double                     maxWidth;
    double                     minHeight;
    double                     minWidth;
    Insets                     opaqueInsets;
    Insets                     padding;
    double                     prefHeight;
    double                     prefWidth;
    BooleanProperty            scaleShape;
    Shape shape;
    BooleanProperty            snapToPixel;
    ReadOnlyDoubleProperty width;

    public RegionProperties(Region r) {
        background     = r.getBackground();
        border         = r.getBorder();
        cacheShape     = r.cacheShapeProperty();
        centerShape    = r.centerShapeProperty();
        height         = r.getHeight();
        insets         = r.getInsets();
        maxHeight      = r.getMaxHeight();
        maxWidth       = r.getMaxWidth();
        minHeight      = r.getMinHeight();
        minWidth       = r.getMinWidth();
        opaqueInsets   = r.getOpaqueInsets();
        padding        = r.getPadding();
        prefHeight     = r.getPrefHeight();
        prefWidth      = r.getPrefWidth();
        scaleShape     = r.scaleShapeProperty();
        shape          = r.getShape();
        snapToPixel    = r.snapToPixelProperty();
        width          = r.widthProperty();
    }
}
