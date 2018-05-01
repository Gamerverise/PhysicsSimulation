package edsel.compiler.lex.automata_state;

import java.util.LinkedList;

public class NFA_State<ID_TYPE> {
    public ID_TYPE id;

    LinkedList<DFA_State<ID_TYPE>> sub_states = new LinkedList<>();

    public NFA_State(ID_TYPE id, NFA_State... transitions) {
        this.id = id;
    }
}
