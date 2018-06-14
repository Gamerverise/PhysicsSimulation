package edsel.lib.cfg_parser;

import java.util.Objects;

import edsel.lib.cfg_model.CFG;
import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.Reduction;
import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.cfg_parser.parsing_restriction.*;
import edsel.lib.io.CharBuffer.CharBufferString;
import edsel.lib.io.TokenBuffer;
import lib.data_structures.list.LinkedListLegacy;
import lib.data_structures.list.link.LinkLegacy;
import lib.text_io.Sprintable;

import static java.lang.Character.isDigit;

import static edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction.RestrictionMode.EXACT_MODE;
import static edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction.RestrictionMode.PREFIX_MODE;
import static edsel.lib.cfg_parser.parsing_restriction.RestrictionOperator.*;
import static lib.java_api_extensions.PrintStreamX.outx;

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
    CFG<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cfg;

    public CFG_Parser(CFG<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cfg) {
        this.cfg = cfg;
    }

    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            CFG<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cfg,
            String filename
    )
            throws AmbiguousParserInput, InputNotAccepted
    {
        SYMBOL_BUFFER_TYPE input = get_new_input(filename);

        outx.println(input.sprint());
        outx.println();

        PARSING_STATE_TYPE state = get_new_parsing_state(input);

        return parse_recursive(cfg.start_production, state);
    }

    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(PARSING_STATE_TYPE state)
            throws AmbiguousParserInput, InputNotAccepted
    {
        return parse_recursive(cfg.start_production, state);
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

    public abstract SYMBOL_BUFFER_TYPE get_new_input(String filename)
            throws InputNotAccepted;

    public abstract PARSING_STATE_TYPE get_new_parsing_state(SYMBOL_BUFFER_TYPE input);

    // =========================================================================================

    public abstract class SymbolBuffer
            extends
            TokenBuffer<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
            implements
            Sprintable
    {
        // =========================================================================================

        public LinkLegacy<SymbolBufferSymbol> symbol_cursor = null;
        public LinkedListLegacy<SymbolBufferSymbol> symbol_buffer = new LinkedListLegacy<>();

        public int how_many_terminals = 0;

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

            SymbolBufferSymbol cur_symbol;

            for (
                    cur_symbol = lex_next_symbol();
                    cur_symbol. != cfg.eof_id;
                    cur_symbol = lex_next_symbol())
            {
                symbol_buffer.append(cur_symbol);

                if (cur_symbol instanceof Token)
                    how_many_terminals++;
            }

            symbol_buffer.append(cur_symbol);
            how_many_terminals++;

            symbol_buffer.append((SymbolBufferSymbol) null);
            symbol_buffer.tail.next = symbol_buffer.tail;

            symbol_cursor = symbol_buffer.head;
        }

        // =========================================================================================

        public SymbolBufferSymbol next_symbol()
        {
            symbol_cursor = symbol_cursor.next;
            return symbol_cursor.elem;
        }

        public SymbolBufferSymbol get_cur_symbol()
        {
            return symbol_cursor.elem;
        }

        public void advance_symbol_cursor()
        {
            symbol_cursor = symbol_cursor.next;
        }

        public SymbolBufferSymbol lex_next_symbol()
                throws InputNotAccepted
        {
            eat_separators();

            if (cursor_pos >= buf.length)
                return
                        new Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
                                (cfg.eof_id, null, new CharBufferString(buf.length, buf.length));

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

                        CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
                                terminal
                                =
                                (CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>)
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
                return cfg.get_production(restriction_name);
            else if (restriction_type == TerminalRestriction.class)
                return cfg.get_terminal(restriction_name);

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

        public StringBuilder sprint() {
            StringBuilder result = new StringBuilder();

            LinkLegacy<SymbolBufferSymbol> cur = symbol_buffer.head;

            if (cur.next != cur.next.next) {
                result.append(cur.elem.sprint());

                while (cur.next != cur.next.next) {
                    cur = cur.next;
                    result.append(", ").append(cur.elem.sprint());
                }
            }

            return result;
        }
    }
}
