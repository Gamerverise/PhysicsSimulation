package edsel.compiler.lex.automaton;

import edsel.compiler.lex.automata_state.NFA_State;

public class NFA<ID_TYPE> {
    public NFA_State<ID_TYPE> start;
    public NFA_State<ID_TYPE>[][] transition_matrix;
}
