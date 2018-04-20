package lib.javafx_api_extensions;

import javafx.scene.transform.Affine;
import lib.java_lang_extensions.tuples.XY;
import lib.transforms.TransformMulti;

import static lib.java_lang_extensions.tuples.XY.XY;

public class AffineX extends Affine {

    public TransformMulti transform = new TransformMulti() {
        @Override
        public void translate(double dx, double dy) {

            // FIXME: HIGH_PRIORITY: prepend or append?

//            prependTranslation(dx, dy);
            appendTranslation(dx, dy);
        }

        @Override
        public void scale(double scale_x, double scale_y) {

            appendScale(scale_x, scale_y);
        }

        @Override
        public void rotate(double degrees) {
            appendRotation(degrees);
        }

        @Override
        public XY<Double> map_point(double x, double y) {
            return XY(transform(x, y));
        }
    };

    public AffineX(Affine affine) {
        super(affine);
    }

    public AffineX() {
        super(1, 0, 0, 0, 1, 0);
    }
}
