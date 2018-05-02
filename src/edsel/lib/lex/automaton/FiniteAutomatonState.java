package edsel.lib.lex.automaton;

import java.util.LinkedList;

public class FiniteAutomatonState<ID_TYPE, REDUCTION> {

    public ID_TYPE id;
    public boolean is_finish;
    public LinkedList<FiniteAutomatonTransition<ID_TYPE, REDUCTION>> transitions = new LinkedList<>();
    public REDUCTION reduction;

    public FiniteAutomatonState(ID_TYPE id, boolean is_finish) {
        this.id = id;
        this.is_finish = is_finish;
    }
}
