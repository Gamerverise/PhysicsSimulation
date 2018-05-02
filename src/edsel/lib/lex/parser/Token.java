package edsel.lib.lex.parser;

public class Token<T> {
    public T tok_type;
    public String token;

    public Token(T tok_type, String token) {
        this.tok_type = tok_type;
        this.token = token;
    }
}
