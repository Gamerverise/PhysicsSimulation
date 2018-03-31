class JavaFX_Bugs {
    // Dummy class just so we can keep these notes in a .java file in the src directory.
}

// FEATURE BUG: Roughly speaking, depending on its configuration, a GridPane may compute its height
// constraints with rounding errors:
//
//      min_height = round(requested_height / num_rows) * num_rows
//
// whereas the correct calculation should be
//
//      min_height = requested_height
//
// Without code to counter this effect, the relative size of this widget may change upon resizing.
// Turning off snap-to-pixel does the trick:
//
//      setSnapToPixel(false);
//
// Simply fixing the size of the GridPane in layoutChildren of the parent would seem to be a more
// general solution, but doing so simply forces errors to manifest from rounding down.


// BUG: JavaFX inconsistent in the way it draws borders
//
// I discovered this bug while I was using RED to debug a different problem.
//
// This statement with the corner radii at 2 should work:
//
//      setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(0, 0, 2, 2, false), new Insets(0, 0, 0, 0))));
//
// But the output looks wrong. The red color is outside of the border at the corners.
//
// With this statement, with the corner raddi at 3, the output looks right:
//
//      setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(0, 0, 3, 3, false), new Insets(0, 0, 0, 0))));
//
// My theory:
//
// The reason is that for a given region, its border takes precendence over its background--because its
// background is drawn first. (The color blending algorithm may also affect the output.) However, for a child
// region, its entirety--including its border--takes precedence over its parent because it is drawn after
// its parent (subject to clipping and positioning by its parent). Ultimately, part of the background of a
// non-rounded child can interfere with the rounded corners of a parent.
