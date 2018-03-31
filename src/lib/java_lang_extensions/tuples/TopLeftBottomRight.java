package tuples;

public class TopLeftBottomRight<T extends Number> {

    public T top;
    public T left;
    public T bottom;
    public T right;

    public TopLeftBottomRight(T top, T left, T bottom, T right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }
}
