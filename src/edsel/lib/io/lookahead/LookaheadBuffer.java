package edsel.lib.io.lookahead;

public abstract class LookaheadBuffer<T> {

    public T eof;

    public LookaheadBuffer(T eof) {
        this.eof = eof;
    }

    public abstract boolean not_empty();

    public abstract T next();

    public abstract T peek();

    public abstract T peek(int n);
}
