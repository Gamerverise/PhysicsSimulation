package lib.gui.multi_class;

import javafx.scene.layout.Region;

import java_lang_extensions.multi_class.*;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class _RegionAncestor extends _Ancestor<Region> {

    public _RegionAncestor(Region region) {
        super(region);

        set_size_constraints_to_default();
    }

    // FIXME: Do we need these size constraint functions? If so, are they correct?

    public void set_size_constraints_to_default() {
        set_size_constraints_to_default(descendant);
    }

    public static void set_size_constraints_to_default(Region r) {
        r.setMinSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        r.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        r.setMaxSize(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    static public Class get_class() {
        return _RegionAncestor.class;
    }
}
