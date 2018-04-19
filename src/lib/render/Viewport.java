package lib.render;

import lib.java_lang_extensions.tuples.Geometry_XY_WH;
import lib.java_lang_extensions.tuples.WidthHeight;
import lib.java_lang_extensions.tuples.XY;

public class Viewport {

    // _model suffix means units in model space

    public double basis_center_x_model;
    public double basis_center_y_model;
    public double basis_width_model;
    public double basis_height_model;

    public double translation_unit;
    public double scale_unit;

    public int delta_x_count;
    public int delta_y_count;
    public int scale_count;

    public Viewport() {
        // Default viewport size is zero-by-zero
        // Default viewport translation is the identity operation

        set_basis_geometry(0, 0, 0, 0);

        delta_x_count = 0;
        delta_y_count = 0;
        scale_count = 0;
    }

    public Viewport(double basis_center_x_model, double basis_center_y_model,
                    double basis_width_model, double basis_height_model,
                    double translation_unit, double scale_unit,
                    int delta_x_count, int delta_y_count, int scale_count)
    {
        init(   basis_center_x_model, basis_center_y_model,
                basis_width_model, basis_height_model,
                translation_unit, scale_unit,
                delta_x_count, delta_y_count, scale_count);
    }

    public Viewport new_copy() {
        return new Viewport(
                basis_center_x_model, basis_center_y_model,
                basis_width_model, basis_height_model,
                translation_unit, scale_unit,
                delta_x_count, delta_y_count, scale_count);
    }

    public void init(double basis_center_x_model, double basis_center_y_model,
                     double basis_width_model, double basis_height_model,
                     double translation_unit, double scale_unit,
                     int delta_x_count, int delta_y_count, int scale_count)
    {
        this.basis_center_x_model = basis_center_x_model;
        this.basis_center_y_model = basis_center_y_model;
        this.basis_width_model = basis_width_model;
        this.basis_height_model = basis_height_model;
        this.translation_unit = translation_unit;
        this.scale_count = scale_count;
        this.delta_x_count = delta_x_count;
        this.delta_y_count = delta_y_count;
        this.scale_count = scale_count;
    }

    public void init(Viewport v) {
        init(   v.basis_center_x_model, v.basis_center_y_model,
                v.basis_width_model, v.basis_height_model,
                v.translation_unit, v.scale_unit,
                v.delta_x_count, v.delta_y_count, v.scale_count);
    }

    public void set_basis_center(double basis_center_x_model, double basis_center_y_model) {
        this.basis_center_x_model = basis_center_x_model;
        this.basis_center_y_model = basis_center_y_model;
    }
    
    public void set_basis_dimensions(double basis_width_model, double basis_height_model) {
        this.basis_width_model = basis_width_model;
        this.basis_height_model = basis_height_model;
    }

    public void set_basis_geometry(double basis_center_x_model,
                             double basis_center_y_model,
                             double basis_width_model,
                             double basis_height_model)
    {
        this.basis_center_x_model = basis_center_x_model;
        this.basis_center_y_model = basis_center_y_model;
        this.basis_width_model = basis_width_model;
        this.basis_height_model = basis_height_model;
    }

    public void translate_basis(double delta_x, double delta_y) {
        basis_center_x_model += delta_x;
        basis_center_y_model += delta_y;
    }

    public void scale_basis(double scale_model) {
        basis_width_model *= scale_model;
        basis_height_model *= scale_model;
    }

    public void translate_basis(int delta_x_count, int delta_y_count) {
        this.delta_x_count += delta_x_count;
        this.delta_y_count += delta_y_count;
    }

    public void scale_basis(int scale_count) {
        this.scale_count += scale_count;
    }

    public XY<Double> get_center_model() {
        return new XY<>(basis_center_x_model + delta_x_count*translation_unit,
                basis_center_y_model + delta_y_count*translation_unit);
    }

    public double get_width_model() {
        return basis_width_model*(1 + scale_count * scale_unit);
    }

    public double get_height_model() {
        return basis_height_model*(1 + scale_count * scale_unit);
    }

    public WidthHeight<Double> get_dimensions_model() {
        return new WidthHeight<>(basis_width_model*(1 + scale_count * scale_unit),
                basis_height_model*(1 + scale_count * scale_unit));
    }

    public Geometry_XY_WH<Double> get_geometry_model() {
        return new Geometry_XY_WH<>(
                basis_center_x_model + delta_x_count*translation_unit,
                basis_center_y_model + delta_y_count*translation_unit,
                basis_width_model*(1 + scale_count * scale_unit),
                basis_height_model*(1 + scale_count * scale_unit));
    }

    public double get_aspect_ratio() {
        return get_width_model() / get_height_model();
    }

    void scale_to_width(double basis_width_model) {
        double basis_height_model = this.basis_height_model / this.basis_width_model * basis_width_model;
        set_basis_dimensions(basis_width_model, basis_height_model);
    }

    void scale_to_height(double basis_height_model) {
        double basis_width_model = this.basis_width_model / this.basis_height_model * basis_height_model;
        set_basis_dimensions(basis_width_model, basis_height_model);
    }
}
