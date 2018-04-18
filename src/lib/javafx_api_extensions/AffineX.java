package lib.javafx_api_extensions;

import javafx.scene.transform.Affine;
import lib.transforms.TransformMulti;

public class AffineX extends Affine {

    public TransformMulti transform = new TransformMulti() {
        @Override
        public void translate(double dx, double dy) {
            this.translate(dx, dy);
        }

        @Override
        public void scale(double scale_x, double scale_y) {
            this.scale(scale_x, scale_y);
        }

        @Override
        public void rotate(double degrees) {
            this.rotate(degrees);
        }
    };

    public AffineX(Affine affine) {
        super(affine);
    }

    public AffineX() {
        super(1, 0, 0, 0, 1, 0);
    }
}
