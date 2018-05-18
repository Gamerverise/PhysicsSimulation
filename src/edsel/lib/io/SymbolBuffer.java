package edsel.lib.io;

import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.Reduction;
import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.cfg_parser.parsing_restriction.ParsingRestriction;
import edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction;
import edsel.lib.cfg_parser.parsing_restriction.RestrictionMode;
import edsel.lib.cfg_parser.parsing_restriction.TerminalRestriction;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.tokens.enums.CopyType;

import java.util.Stack;

import static edsel.lib.cfg_parser.parsing_restriction.RestrictionMode.PRODUCTION_RESTRICTION;
import static edsel.lib.cfg_parser.parsing_restriction.RestrictionMode.TERMINAL_RESTRICTION;
import static lib.tokens.enums.CopyType.COPY_DEEP;
import static lib.tokens.enums.CopyType.COPY_SHALLOW;

public abstract
class SymbolBuffer
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE,
                SYMBOL_BUFFER_TYPE
                        extends SymbolBuffer
                                <ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        SYMBOL_BUFFER_TYPE>>
        extends
        TokenBuffer<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
{
    public Stack<ParsingRestriction> restriction_stack = new Stack<>();

    public Stack<SymbolBuffer
            <ENUM_PRODUCTION_ID,
                    ENUM_TERMINAL_ID,
                    TOKEN_VALUE_TYPE,
                    SYMBOL_BUFFER_TYPE>> save_stack = new Stack<>();

    public SymbolBuffer(String filename)
            throws InputNotAccepted
    {
        super(filename);
        init(separator_chars, cursor_pos, buf);
    }

    public SymbolBuffer(String filename, byte[] separator_chars)
            throws InputNotAccepted
    {
        super(filename);
        init(separator_chars, cursor_pos, buf);
    }

    public SymbolBuffer(SymbolBuffer tok_buf, CopyType copy_type)
            throws InputNotAccepted
    {
        super(tok_buf, COPY_SHALLOW);

        if (copy_type == COPY_DEEP) {
            separator_chars = new byte[tok_buf.separator_chars.length];
            System.arraycopy(tok_buf.separator_chars, 0, separator_chars, 0, tok_buf.separator_chars.length);
        } else
            separator_chars = tok_buf.separator_chars;

        init(separator_chars, tok_buf.cursor_pos, tok_buf.buf);
    }

    public void init(byte[] separator_chars, int cursor_pos, byte[] buf)
            throws InputNotAccepted
    {
        this.separator_chars = separator_chars;
        this.cursor_pos = cursor_pos;
        this.buf = buf;

        update_restriction(null);
    }

    // =========================================================================================

    public
    SymbolBuffer<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
    self() {
        return this;
    }

    public SymbolBuffer<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
    new_instance(Object... args)
    {
        return Instantiator.new_instance(CharBuffer.class, args);
    }

    // =========================================================================================

    public void save() {
        save_stack.push(new_copy(CopyType.COPY_DEEP));
    }

    public void restore()
            throws InputNotAccepted
    {
        SymbolBuffer tmp = save_stack.pop();
        init(tmp.separator_chars, tmp.cursor_pos, tmp.buf);
    }

    public void update_restriction(ParseNode last_parse_node)
            throws InputNotAccepted
    {
        eat_separators();

        char next_char;

        if (cursor_pos + 1 < buf.length)
            next_char = (char) buf[cursor_pos];
        else
            return;

        ParsingRestriction cur_restriction = restriction_stack.peek();

        if (cur_restriction != null) {

            if (next_char == ')') {

                if (last_parse_node instanceof Reduction) {
                    if (!(cur_restriction instanceof ProductionRestriction))
                        return;

                    Reduction<ENUM_PRODUCTION_ID> last_reduction;
                    ProductionRestriction<ENUM_PRODUCTION_ID> cur_production_restriction;

                    last_reduction = (Reduction<ENUM_PRODUCTION_ID>) last_parse_node;
                    cur_production_restriction = (ProductionRestriction<ENUM_PRODUCTION_ID>) cur_restriction;

                    if (last_reduction.production_id != cur_production_restriction.production.id)
                        return;
                } else {
                    if (!(cur_restriction instanceof TerminalRestriction))
                        return;

                    Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> last_token;
                    TerminalRestriction<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_terminal_restriction;

                    last_token = (Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) last_parse_node;
                    cur_terminal_restriction
                            = (TerminalRestriction<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_restriction;

                    if (last_token.id != cur_terminal_restriction.terminal.id)
                        return;
                }
                restriction_stack.pop();
                cursor_pos++;
                return;
            }
        }

        if (next_char == '(') {

            if (cursor_pos + 1 < buf.length) {
                char mode_char = (char) buf[cursor_pos + 1];

                RestrictionMode mode = RestrictionMode.get_mode(mode_char);

                if (mode != null) {
                    cursor_pos += 2;
                    push_new_restriction(mode);
                }
            }
        }
    }

    public void push_new_restriction(RestrictionMode mode)
            throws InputNotAccepted
    {
        int restriction_name_start = cursor_pos;

        while (cursor_pos < buf.length && buf[cursor_pos] != ':')
            cursor_pos++;

        if (buf[cursor_pos] != ':')
            throw new InputNotAccepted();

        CharBufferString restriction_name
                = new CharBufferString(restriction_name_start, cursor_pos);

        cursor_pos++;

        if (mode == TERMINAL_RESTRICTION) {
            CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> next_terminal_restriction
                    = get_terminal(restriction_name);

            if (next_terminal_restriction != null) {
                restriction_stack.push(new TerminalRestriction<>(next_terminal_restriction, mode));
            }
        } else if (mode == PRODUCTION_RESTRICTION) {
            CFG_Production<ENUM_PRODUCTION_ID> next_production_restriction
                    = get_production(restriction_name);

            if (next_production_restriction != null) {
                restriction_stack.push(new ProductionRestriction<>(next_production_restriction, mode));
            }
        }
        throw new InputNotAccepted();
    }

    public abstract Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> token_advance();

    public void eat_separators() {
        while (cursor_pos < buf.length)
            if (is_separator(buf[cursor_pos]))
                cursor_pos++;
            else
                break;
    }

    public boolean is_separator(byte chr) {
        for(byte cur : separator_chars)
            if (chr == cur)
                return true;

        return false;
    }
}
