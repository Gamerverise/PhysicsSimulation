package edsel.compiler.lex.automaton;

public class AutomatonNode<ID_TYPE> {

    public ID_TYPE id;
    public boolean is_finish;

    public AutomatonNode(ID_TYPE id, boolean is_finish) {
        this.id = id;
        this.is_finish = is_finish;
    }
}
