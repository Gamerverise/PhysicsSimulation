package edsel.compiler.lex.automata_state;

public class DFA_State<ID_TYPE> {

    public ID_TYPE id;
    public boolean is_finish;

    public DFA_State(ID_TYPE id, boolean is_finish) {
        this.id = id;
        this.is_finish = is_finish;
    }
}
