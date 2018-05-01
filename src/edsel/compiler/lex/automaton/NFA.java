package edsel.compiler.lex.automaton;

public class NFA<ID_TYPE> {
    public NFA_Node<ID_TYPE> start;
    public NFA_Node<ID_TYPE>[][] transition_matrix;
}
