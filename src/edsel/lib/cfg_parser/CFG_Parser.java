package edsel.lib.cfg_parser;

import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.Reduction;
import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.cfg_parser.parsing_restriction.*;
import edsel.lib.io.CharBuffer.CharBufferString;
import edsel.lib.io.TokenBuffer;
import jdk.internal.util.xml.impl.Input;
import lib.data_structures.list.LinkedListLegacy;
import lib.data_structures.list.link.LinkLegacy;
import lib.java_lang_extensions.mutable.MutableInt;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.java_lang_extensions.tuples.Tuple;

import java.util.Objects;

import static edsel.lib.cfg_parser.parsing_restriction.RestrictionMode.PRODUCTION_RESTRICTION;
import static edsel.lib.cfg_parser.parsing_restriction.RestrictionMode.TERMINAL_RESTRICTION;

public abstract class CFG_Parser
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE,
                SYMBOL_BUFFER_TYPE extends
                        CFG_Parser
                                <ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        SYMBOL_BUFFER_TYPE>.SymbolBuffer<SYMBOL_BUFFER_TYPE>>
{
    public CFG_Production<ENUM_PRODUCTION_ID>                      start_production;
    public CFG_Production<ENUM_PRODUCTION_ID>[]                    productions;
    public CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>[]      terminals;

    @SafeVarargs
    public CFG_Parser(
            CFG_Production<ENUM_PRODUCTION_ID>                      start_production,
            CFG_Production<ENUM_PRODUCTION_ID>[]                    productions,
            CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>...     terminals)
    {
        this.start_production = start_production;
        this.productions = productions;
        this.terminals = terminals;
    }

    public abstract Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(String filename, MutableInt num_branches_explored)
            throws AmbiguousParserInput, InputNotAccepted;

    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(SymbolBuffer<SYMBOL_BUFFER_TYPE> input, MutableInt num_branches_explored)
            throws AmbiguousParserInput, InputNotAccepted
    {
        return parse_recursive(start_production, input, num_branches_explored);
    }

    public abstract Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            SymbolBuffer<SYMBOL_BUFFER_TYPE> input,
            MutableInt num_branches_explored
    )
            throws AmbiguousParserInput, InputNotAccepted;

    public abstract Reduction<ENUM_PRODUCTION_ID>
    parse_branch_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            int branch_num,
            SymbolBuffer<SYMBOL_BUFFER_TYPE> input,
            MutableInt num_branches_explored
    )
            throws AmbiguousParserInput, InputNotAccepted;

    public CFG_Production<ENUM_PRODUCTION_ID>
    get_production(CharBufferString production_name)
    {
        for(CFG_Production<ENUM_PRODUCTION_ID> production : productions)
            if (Objects.equals(production_name.get_string(), production.name))
                return production;

        return null;
    }

    public CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
    get_terminal(CharBufferString terminal_name)
    {
        String terminal_name_str = terminal_name.get_string();

        for(CFG_Terminal<ENUM_TERMINAL_ID,  TOKEN_VALUE_TYPE> terminal : terminals)
            if (Objects.equals(terminal_name_str, terminal.name))
                return terminal;

        return null;
    }

    public interface SymbolBufferSymbol {}

    public abstract
    class SymbolBuffer
            <SYMBOL_BUFFER_TYPE extends SymbolBuffer<SYMBOL_BUFFER_TYPE>>
            extends
            TokenBuffer<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
    {
        // =========================================================================================

        LinkLegacy<SymbolBufferSymbol> symbol_cursor;

        int restriction_nesting_level = 0;

        public LinkedListLegacy<SymbolBufferSymbol> symbol_buffer = new LinkedListLegacy<>();

        public LinkedListLegacy<LinkLegacy<SymbolBufferSymbol>> save_queue = new LinkedListLegacy<>();

        // =========================================================================================

        public SymbolBuffer(String filename)
                throws InputNotAccepted
        {
            this(filename, DEFAULT_SEPARATOR_CHARS);
        }

        public SymbolBuffer(String filename, byte[] separator_chars)
                throws InputNotAccepted
        {
            super(filename, separator_chars);
        }

        // =========================================================================================

        public void save() {
            save_queue.push(symbol_cursor);
        }

        public void restore()
        {
            symbol_cursor = save_queue.pop();
        }

        public SymbolBufferSymbol next_symbol()
                throws InputNotAccepted
        {
            SymbolBufferSymbol next_sym;

            if (symbol_cursor == null) {
                next_sym = lex_next_symbol();

                if (next_sym == null)
                    return null;

                symbol_buffer.append(next_sym);
                symbol_cursor = symbol_buffer.tail;
                return symbol_cursor.elem;
            } else {
                SymbolBufferSymbol cur = symbol_cursor.elem;
                symbol_cursor = symbol_cursor.next;
                return cur;
            }
        }

        public SymbolBufferSymbol lex_next_symbol()
                throws InputNotAccepted
        {
            eat_separators();

            if (cursor_pos >= buf.length) {
                if (restriction_nesting_level > 0)
                    throw new InputNotAccepted();
                else
                    return null;
            }

            char next_char = (char) buf[cursor_pos];

            if (next_char == ')') {
                if (restriction_nesting_level > 0) {

                    restriction_nesting_level -= 1;
                    cursor_pos++;
                    return new EndRestriction();
                }
            } else if (next_char == '(') {

                if (cursor_pos + 1 < buf.length) {
                    char mode_char = (char) buf[cursor_pos + 1];

                    RestrictionMode mode = RestrictionMode.get_mode(mode_char);

                    if (mode != null) {
                        cursor_pos += 2;

                        int restriction_name_start = cursor_pos;

                        while (cursor_pos < buf.length && buf[cursor_pos] != ':')
                            cursor_pos++;

                        if (buf[cursor_pos] != ':')
                            throw new InputNotAccepted();

                        CharBufferString restriction_name
                                = new CharBufferString(restriction_name_start, cursor_pos);

                        cursor_pos++;

                        if (mode == TERMINAL_RESTRICTION) {
                            CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> terminal = get_terminal(restriction_name);

                            if (terminal == null)
                                throw new InputNotAccepted();

                            return next_token();

                        } else if (mode == PRODUCTION_RESTRICTION) {
                            CFG_Production<ENUM_PRODUCTION_ID> production = get_production(restriction_name);

                            if (production == null)
                                throw new InputNotAccepted();

                            return new ProductionRestriction<>(production, mode);
                        }
                    }
                }
            }
            return next_token();
        }

        public abstract Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> next_token();

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
}
