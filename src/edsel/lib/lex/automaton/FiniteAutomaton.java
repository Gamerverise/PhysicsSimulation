package edsel.lib.lex.automaton;

import java.util.LinkedList;

public class FiniteAutomaton<ID_TYPE, REDUCTION> {
    public LinkedList<FiniteAutomaton<ID_TYPE, REDUCTION>> start_states = new LinkedList<>();
}
