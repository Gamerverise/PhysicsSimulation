package edsel.lib.lex.automaton;

import java.util.LinkedList;

public class FiniteAutomatonTransition<ID_TYPE, REDUCTION> {

    public int chr;

    public LinkedList<FiniteAutomatonState<ID_TYPE, REDUCTION>> states = new LinkedList<>();

    public FiniteAutomatonTransition(int chr) {
        this.chr = chr;
    }
}
