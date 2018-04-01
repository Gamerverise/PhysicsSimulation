package gui.debug;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

class TilePaneProperties {
    Pos alignment;
    double          hgap;
    Orientation orientation;
    int             prefColumns;
    int             prefRows;
    double          prefTileHeight;
    double          prefTileWidth;
    Pos             tileAlignment;
    double          tileHeight;
    double          tileWidth;
    double          vgap;

    TilePaneProperties(TilePane p) {
        alignment          = p.getAlignment();
        hgap               = p.getHgap();
        orientation        = p.getOrientation();
        prefColumns        = p.getPrefColumns();
        prefRows           = p.getPrefRows();
        prefTileHeight     = p.getPrefTileHeight();
        prefTileWidth      = p.getPrefTileWidth();
        tileAlignment      = p.getTileAlignment();
        tileHeight         = p.getTileHeight();
        tileWidth          = p.getTileWidth();
        vgap               = p.getVgap();
    }
}
