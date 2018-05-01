package edsel.compiler.lex.automaton;

public class DFA<ID_TYPE> {
    public AutomatonNode<ID_TYPE> start;
    public AutomatonNode<ID_TYPE>[][] transition_matrix;
}
