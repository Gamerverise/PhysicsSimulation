package edsel.compiler.lex;

import java.util.LinkedList;

public class NFA_State {
    int id;
    LinkedList<NFA_State> transitions = new LinkedList<>();

    public NFA_State(int id, NFA_State... transitions) {
        this.id = id;
    }
}
