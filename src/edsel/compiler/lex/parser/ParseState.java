package edsel.compiler.lex.parser;

import edsel.compiler.lex.automaton.DFA_State;
import edsel.compiler.lex.text_io.SeekableCharBuffer;

import java.util.Stack;

public class ParseState<ID_TYPE> {

    Stack<DFA_State<ID_TYPE>> state_stack = new Stack<>();
    SeekableCharBuffer input;

    ParseState(SeekableCharBuffer input) {
        this.input = input;
    }
}
