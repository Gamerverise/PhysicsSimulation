package edsel.compiler.lex;

public class AutomatonToken<T> {
    public T tok_type;
    public String token;

    public AutomatonToken(T tok_type, String token) {
        this.tok_type = tok_type;
        this.token = token;
    }
}
