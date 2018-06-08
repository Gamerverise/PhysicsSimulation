package edsel.lib.cfg_parser;

import java.util.Objects;
import java.util.Stack;

import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.Reduction;
import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.cfg_parser.parsing_restriction.*;
import edsel.lib.cfg_parser.parsing_restriction.old.TerminalRestriction;
import edsel.lib.io.CharBuffer.CharBufferString;
import edsel.lib.io.TokenBuffer;
import lib.data_structures.list.LinkedListLegacy;
import lib.data_structures.list.link.LinkLegacy;

import static java.lang.Character.isDigit;

import static edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction.RestrictionMode.EXACT_MODE;
import static edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction.RestrictionMode.PREFIX_MODE;
import static edsel.lib.cfg_parser.parsing_restriction.RestrictionOperator.*;

public abstract class CFG_Parser
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE,
                SYMBOL_BUFFER_TYPE extends
                        CFG_Parser
                                <ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        SYMBOL_BUFFER_TYPE,
                                        PARSING_STATE_TYPE>
                                .SymbolBuffer,
                PARSING_STATE_TYPE extends
                        ParsingState
                                <ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        SYMBOL_BUFFER_TYPE,
                                        PARSING_STATE_TYPE>>
{
    public CFG_Production<ENUM_PRODUCTION_ID> start_production;
    public CFG_Production<ENUM_PRODUCTION_ID>[] productions;
    public CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>[] terminals;

    @SafeVarargs
    public CFG_Parser(
            CFG_Production<ENUM_PRODUCTION_ID> start_production,
            CFG_Production<ENUM_PRODUCTION_ID>[] productions,
            CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>... terminals) {
        this.start_production = start_production;
        this.productions = productions;
        this.terminals = terminals;
    }

    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(String filename)
            throws AmbiguousParserInput, InputNotAccepted
    {
        SYMBOL_BUFFER_TYPE input = get_new_input(filename);
        PARSING_STATE_TYPE state = get_new_parsing_state(input);
        return parse_recursive(start_production, state);
    }

    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(PARSING_STATE_TYPE state)
            throws AmbiguousParserInput, InputNotAccepted
    {
        return parse_recursive(start_production, state);
    }

    public abstract Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            PARSING_STATE_TYPE state
    )
            throws AmbiguousParserInput, InputNotAccepted;

    public abstract Reduction<ENUM_PRODUCTION_ID>
    parse_branch_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            int branch_num,
            PARSING_STATE_TYPE state
    )
       throws AmbiguousParserInput, InputNotAccepted;

    // =========================================================================================

    public abstract SYMBOL_BUFFER_TYPE get_new_input(String filename);
    public abstract PARSING_STATE_TYPE get_new_parsing_state(SYMBOL_BUFFER_TYPE input);

    // =========================================================================================

    public CFG_Production<ENUM_PRODUCTION_ID>
    get_production(CharBufferString production_name) {
        for (CFG_Production<ENUM_PRODUCTION_ID> production : productions)
            if (Objects.equals(production_name.get_string(), production.name))
                return production;

        return null;
    }

    public CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
    get_terminal(CharBufferString terminal_name) {
        String terminal_name_str = terminal_name.get_string();

        for (CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE> terminal : terminals)
            if (Objects.equals(terminal_name_str, terminal.name))
                return terminal;

        return null;
    }

    public abstract class SymbolBuffer
            extends
            TokenBuffer<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
    {
        // =========================================================================================

        public LinkedListLegacy<SymbolBufferSymbol> symbol_buffer = new LinkedListLegacy<>();
        public Stack<SymbolBufferState> save_stack = new Stack<>();

        // =========================================================================================

        public SymbolBuffer(String filename)
        {
            this(filename, DEFAULT_SEPARATOR_CHARS);
        }

        public SymbolBuffer(String filename, byte[] separator_chars)
        {
            super(filename, separator_chars);
            save_stack.push(new SymbolBufferState());
        }

        // =========================================================================================

        public void save() {
            save_stack.push(new SymbolBufferState(save_stack.peek()));
        }

        public void restore() {
            save_stack.pop();
        }

        public LinkLegacy<SymbolBufferSymbol> get_symbol_cursor() {
            return save_stack.peek().symbol_cursor;
        }

        public void set_symbol_cursor(LinkLegacy<SymbolBufferSymbol> link) {
            save_stack.peek().symbol_cursor = link;
        }

        public void extend_symbol_buffer(SymbolBufferSymbol symbol) {
            symbol_buffer.append(symbol);
            save_stack.peek().symbol_cursor = symbol_buffer.tail;
        }

        public SymbolBufferSymbol next_symbol()
                throws InputNotAccepted
        {
            SymbolBufferSymbol next_sym;
            LinkLegacy<SymbolBufferSymbol> symbol_cursor = get_symbol_cursor();

            if (symbol_cursor == null) {
                next_sym = lex_next_symbol();

                if (next_sym == null)
                    return null;

                extend_symbol_buffer(next_sym);

                return next_sym;
            } else {
                SymbolBufferSymbol cur = symbol_cursor.elem;
                set_symbol_cursor(symbol_cursor.next);
                return cur;
            }
        }

        public SymbolBufferSymbol lex_next_symbol()
                throws InputNotAccepted
        {
            eat_separators();

            if (cursor_pos >= buf.length)
                return null;

            SymbolBufferSymbol next_symbol = null;
            char next_char = (char) buf[cursor_pos];

            if (next_char == END_RESTRICTION.chr) {

                next_symbol = new EndRestriction();
                cursor_pos++;

            } else if (next_char == BEGIN_RESTRICTION.chr) {

                if (cursor_pos + 1 < buf.length) {

                    char op_char = (char) buf[cursor_pos + 1];

                    if (op_char == TERMINAL_RESTRICTION.chr) {

                        cursor_pos += 2;

                        CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
                                terminal
                                =
                                (CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>)
                                        get_restriction(TerminalRestriction.class, ':');

                        if (terminal == null)
                            throw new InputNotAccepted();

                        Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> next_token = next_token();

                        if (next_token.id == terminal.id) {
                            if (buf[cursor_pos] != END_RESTRICTION.chr)
                                throw new InputNotAccepted();

                            next_symbol = next_token;
                            cursor_pos++;

                        } else
                            throw new InputNotAccepted();

                    } else if (op_char == PRODUCTION_PREFIX_RESTRICTION.chr
                            | op_char == PRODUCTION_EXACT_RESTRICTION.chr
                            | op_char == BRANCH_PREFIX_RESTRICTION.chr
                            | op_char == BRANCH_EXACT_RESTRICTION.chr)
                    {
                        cursor_pos += 2;

                        CFG_Production<ENUM_PRODUCTION_ID>
                                production
                                =
                                (CFG_Production<ENUM_PRODUCTION_ID>)
                                        get_restriction(
                                                ProductionRestriction.class,
                                                RESTRICTION_CLAUSE_DELIMITER.chr);

                        if (op_char == PRODUCTION_PREFIX_RESTRICTION.chr)
                            next_symbol = new ProductionRestriction<>(production, PREFIX_MODE);

                        else if (op_char == PRODUCTION_EXACT_RESTRICTION.chr)
                            next_symbol = new ProductionRestriction<>(production, EXACT_MODE);

                        else if (op_char == BRANCH_PREFIX_RESTRICTION.chr) {
                            int branch_num = lex_non_negative_int(RESTRICTION_CLAUSE_DELIMITER.chr);
                            next_symbol = new BranchRestriction<>(production, branch_num, PREFIX_MODE);

                        } else if (op_char == BRANCH_EXACT_RESTRICTION.chr) {
                            int branch_num = lex_non_negative_int(RESTRICTION_CLAUSE_DELIMITER.chr);
                            next_symbol = new BranchRestriction<>(production, branch_num, EXACT_MODE);
                        }
                    }
                }
            } else if (next_char == GATE_RESTRICTION.chr) {
                next_symbol = new GateRestriction();
                cursor_pos++;
            } else
                next_symbol = next_token();

            extend_symbol_buffer(next_symbol);

            return next_symbol;
        }

        public CFG_Symbol
        get_restriction(Class<? extends ParsingRestriction> restriction_type, char delimeter)
                throws InputNotAccepted {
            int restriction_name_start = cursor_pos;

            while (cursor_pos < buf.length && buf[cursor_pos] != delimeter)
                cursor_pos++;

            if (buf[cursor_pos] != delimeter)
                throw new InputNotAccepted();

            CharBufferString
                    restriction_name = new CharBufferString(restriction_name_start, cursor_pos);

            cursor_pos++;

            if (restriction_type == ProductionRestriction.class)
                return get_production(restriction_name);
            else if (restriction_type == TerminalRestriction.class)
                return get_terminal(restriction_name);

            throw new InputNotAccepted();
        }

        public int lex_non_negative_int(char delimeter)
                throws InputNotAccepted {
            int int_start = cursor_pos;

            while (cursor_pos < buf.length && isDigit(buf[cursor_pos]))
                cursor_pos++;

            if (buf[cursor_pos] != delimeter)
                throw new InputNotAccepted();

            int branch_num
                    = Integer.parseInt(
                    new String(buf, int_start, cursor_pos - int_start));

            cursor_pos++;

            return branch_num;
        }

        public void eat_separators() {
            while (cursor_pos < buf.length)
                if (is_separator(buf[cursor_pos]))
                    cursor_pos++;
                else
                    break;
        }

        public boolean is_separator(byte chr) {
            for (byte cur : separator_chars)
                if (chr == cur)
                    return true;

            return false;
        }
    }
}
