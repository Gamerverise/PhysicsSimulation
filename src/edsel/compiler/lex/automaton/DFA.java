package edsel.compiler.lex.automaton;

public class DFA<ID_TYPE> {
    public DFA_State<ID_TYPE> start;
    public DFA_State<ID_TYPE>[][] transition_matrix;
}
