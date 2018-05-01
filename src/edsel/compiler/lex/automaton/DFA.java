package edsel.compiler.lex.automaton;

import edsel.compiler.lex.automata_state.DFA_State;

public class DFA<ID_TYPE> {
    public DFA_State<ID_TYPE> start;
    public DFA_State<ID_TYPE>[][] transition_matrix;
}
