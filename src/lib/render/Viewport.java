package lib.render;

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

    public void scale_basis(double scale) {
        basis_width_model *= scale;
        basis_height_model += scale;
    }

    public void translate_basis(int delta_x_count, int delta_y_count) {
        this.delta_x_count += delta_x_count;
        this.delta_y_count += delta_y_count;
    }

    public void scale_basis(int scale_count) {
        this.scale_count += scale_count;
    }

    void scale_to_width(double basis_width_model) {
        double aspect_ratio = this.basis_width_model / this.basis_height_model;
        double height = basis_width_model / aspect_ratio;
        set_basis_dimensions(basis_width_model, basis_height_model);
    }

    void scale_to_height(double basis_height_model) {
        double aspect_ratio = this.basis_height_model / this.basis_height_model;
        double width = basis_height_model * aspect_ratio;
        set_basis_dimensions(width, basis_height_model);
    }

    public void scale_relative(double percent) {
        gcx.px_per_unit_model = px_per_unit_model;
    }

    public void scale_relative(int scale_count) {
        this.scale_count += scale_count;
    }
}
