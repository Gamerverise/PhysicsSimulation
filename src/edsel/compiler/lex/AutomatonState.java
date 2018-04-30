package edsel.compiler.lex;

public class AutomatonState<T> {

    public int id;
    public T tok_type;
    public boolean is_finish;
    public AutomatonState<T>[] transitions;

    public AutomatonState(int id, T tok_type, boolean is_finish) {
        this.id = id;
        this.tok_type = tok_type;
        this.is_finish = is_finish;
    }
}

