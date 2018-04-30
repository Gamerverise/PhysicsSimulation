package edsel.compiler.lex;

public class DFA<T> {
    public AutomatonState<T> start;
    public AutomatonState<T>[][] transition_matrix;
}
