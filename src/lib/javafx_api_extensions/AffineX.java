package lib.javafx_api_extensions;

import javafx.scene.transform.Affine;
import lib.tokens.enums.Identity;

public class AffineX extends Affine {

    public AffineX(Affine affine) {
        super(affine);
    }

    public AffineX(Identity identity) {
        super(1, 0, 0, 0, 1, 0);
    }
}
