package edsel.compiler.lex.automaton;

import java.util.LinkedList;

public class FiniteAutomatonTransition<ID_TYPE> {

    public int chr;

    public LinkedList<FiniteAutomatonState<ID_TYPE>> states = new LinkedList<>();

    public FiniteAutomatonTransition(int chr) {
        this.chr = chr;
    }
}
