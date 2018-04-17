package lib.javafx_api_extensions;

import lib.transforms.TransformMulti;

public class AffineXClash implements TransformMulti {

    public AffineX affine;

    public AffineXClash(AffineX affine) {
        this.affine = affine;
    }

    public void translate(double dx, double dy) {
        affine.appendTranslation(dx, dy);
    }

    public void scale(double scale_x, double scale_y) {
        affine.appendScale(scale_x, scale_y);
    }

    public void rotate(double degrees) {
        affine.appendRotation(degrees);
    }
}
