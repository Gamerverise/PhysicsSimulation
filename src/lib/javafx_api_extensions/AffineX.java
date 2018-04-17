package lib.javafx_api_extensions;

import javafx.scene.transform.Affine;

public class AffineX extends Affine {

    public AffineX(Affine affine) {
        super(affine);
    }

    public AffineX() {
        super(1, 0, 0, 0, 1, 0);
    }
}
