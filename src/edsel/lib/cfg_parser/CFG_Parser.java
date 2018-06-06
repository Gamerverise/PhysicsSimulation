package edsel.lib.cfg_parser;

import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.Reduction;
import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.cfg_parser.parsing_restriction.*;
import edsel.lib.cfg_parser.parsing_restriction.old.TerminalRestriction;
import edsel.lib.io.CharBuffer;
import edsel.lib.io.CharBuffer.CharBufferString;
import edsel.lib.io.TokenBuffer;
import lib.data_structures.list.LinkedListLegacy;
import lib.data_structures.list.link.LinkLegacy;
import lib.java_lang_extensions.mutable.MutableInt;

import java.util.Objects;
import java.util.Stack;

import static edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction.RestrictionMode.*;
import static edsel.lib.cfg_parser.parsing_restriction.RestrictionOperator.*;
import static java.lang.Character.isDigit;

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
    public CFG_Production<ENUM_PRODUCTION_ID>                                       start_production;
    public CFG_Production<ENUM_PRODUCTION_ID>[]                                     productions;
    public CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>[]   terminals;

    public SYMBOL_BUFFER_TYPE input;

    @SafeVarargs
    public CFG_Parser(
            CFG_Production<ENUM_PRODUCTION_ID>                                          start_production,
            CFG_Production<ENUM_PRODUCTION_ID>[]                                        productions,
            CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>...     terminals)
    {
        this.start_production = start_production;
        this.productions = productions;
        this.terminals = terminals;
    }

    public abstract Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(String filename, MutableInt num_branches_explored)
            throws AmbiguousParserInput, InputNotAccepted;

    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(SYMBOL_BUFFER_TYPE input, MutableInt num_branches_explored)
            throws AmbiguousParserInput, InputNotAccepted
    {
        this.input = input;
        return parse_recursive(start_production, num_branches_explored);
    }

    public abstract Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            MutableInt num_branches_explored
    )
            throws AmbiguousParserInput, InputNotAccepted;

    public abstract Reduction<ENUM_PRODUCTION_ID>
    parse_branch_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            int branch_num,
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

    public CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
    get_terminal(CharBufferString terminal_name)
    {
        String terminal_name_str = terminal_name.get_string();

        for(CFG_Terminal<ENUM_TERMINAL_ID,  TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE> terminal : terminals)
            if (Objects.equals(terminal_name_str, terminal.name))
                return terminal;

        return null;
    }

    public abstract
    class SymbolBuffer
            <SYMBOL_BUFFER_TYPE extends SymbolBuffer<SYMBOL_BUFFER_TYPE>>
            extends
            TokenBuffer<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
    {
        // =========================================================================================

        public LinkedListLegacy<SymbolBufferSymbol> symbol_buffer = new LinkedListLegacy<>();
        public Stack<SymbolBufferState> save_stack = new Stack<>();

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
            save_stack.push(new SymbolBufferState());
        }

        // =========================================================================================

        public void save() {
            save_stack.push(new SymbolBufferState(save_stack.peek()));
        }

        public void restore()
        {
            save_stack.pop();
        }

        public LinkLegacy<SymbolBufferSymbol> get_symbol_cursor() {
            return save_stack.peek().symbol_cursor;
        }

        public void set_symbol_cursor(LinkLegacy<SymbolBufferSymbol> symbol_cursor) {
            save_stack.peek().symbol_cursor = symbol_cursor;
        }

        public int get_nesting_level() {
            return save_stack.peek().restriction_nesting_level;
        }

        public int inc_nesting_level() {
            return save_stack.peek().restriction_nesting_level++;
        }

        public int dec_nesting_level() {
            return save_stack.peek().restriction_nesting_level--;
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

                symbol_buffer.append(next_sym);
                set_symbol_cursor(symbol_buffer.tail);

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
                if (get_nesting_level() > 0)
                    throw new InputNotAccepted();
                else
                    return null;
            }

            Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> next_token = next_token();
            char next_char = (char) buf[cursor_pos];

            if (next_char == END_RESTRICTION.chr) {
                if (get_nesting_level() > 0) {

                    dec_nesting_level();
                    cursor_pos++;
                }
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
                            if (buf[cursor_pos] == END_RESTRICTION.chr)
                                cursor_pos++;

                            symbol_buffer.ap

                            return next_token;
                        }

                        throw new InputNotAccepted();

                    } else if (op_char == PRODUCTION_PREFIX_RESTRICTION.chr
                            | op_char == PRODUCTION_EXACT_RESTRICTION.chr
                            | op_char == BRANCH_PREFIX_RESTRICTION.chr
                            | op_char == BRANCH_EXACT_RESTRICTION.chr)
                    {
                        inc_nesting_level();

                        cursor_pos += 2;

                        CFG_Production<ENUM_PRODUCTION_ID>
                                production
                                =
                                (CFG_Production<ENUM_PRODUCTION_ID>)
                                        get_restriction(
                                                ProductionRestriction.class,
                                                RESTRICTION_CLAUSE_DELIMITER.chr);

                        if (op_char == PRODUCTION_PREFIX_RESTRICTION.chr)
                            return new ProductionRestriction<>(production, PREFIX_MODE);

                        else if (op_char == PRODUCTION_EXACT_RESTRICTION.chr)
                            return new ProductionRestriction<>(production, EXACT_MODE);

                        else if (op_char == BRANCH_PREFIX_RESTRICTION.chr
                                | op_char == BRANCH_EXACT_RESTRICTION.chr)
                        {
                            int branch_num = lex_non_negative_int(RESTRICTION_CLAUSE_DELIMITER.chr);

                            if (op_char == BRANCH_PREFIX_RESTRICTION.chr)
                                return new BranchRestriction<>(production, branch_num, PREFIX_MODE);

                            else if (op_char == BRANCH_EXACT_RESTRICTION.chr)
                                return new BranchRestriction<>(production, branch_num, EXACT_MODE);
                        }
                    }
                }
            } else if (next_char == GATE_RESTRICTION.chr) {
                return new GateRestriction();
            }

            return next_token();
        }

        public CFG_Symbol
        get_restriction(Class<? extends ParsingRestriction> restriction_type, char delimeter)
                throws InputNotAccepted
        {
            int restriction_name_start = cursor_pos;

            while (cursor_pos < buf.length && buf[cursor_pos] != delimeter)
                cursor_pos++;

            if (buf[cursor_pos] != delimeter)
                throw new InputNotAccepted();

            CharBuffer<SYMBOL_BUFFER_TYPE>.CharBufferString
                    restriction_name = new CharBufferString(restriction_name_start, cursor_pos);

            cursor_pos++;

            if (restriction_type == ProductionRestriction.class)
                return get_production(restriction_name);
            else if (restriction_type == TerminalRestriction.class)
                return get_terminal(restriction_name);

            throw new InputNotAccepted();
        }

        public int lex_non_negative_int(char delimeter)
                throws InputNotAccepted
        {
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
            for(byte cur : separator_chars)
                if (chr == cur)
                    return true;

            return false;
        }

//        public
//        class ParsingState
//        {
//            public Stack<PrefixInfo> prefix_info_stack = new Stack<>();
//
//            public void save() {
//                input.save();
//
//                PrefixInfo top = prefix_info_stack.peek();
//                prefix_info_stack.push(new PrefixInfo(top));
//            }
//
//            public void restore() {
//                input.restore();
//                prefix_info_stack.pop();
//            }
//
//            public int get_in_progress() {
//                return prefix_info_stack.peek().num_prefixes_in_progress;
//            }
//
//            public void inc_prefixes_in_progress() {
//                prefix_info_stack.peek().num_prefixes_in_progress++;
//            }
//
//            public void inc_prefixes_to_retire() {
//                prefix_info_stack.peek().num_prefixes_to_retire++;
//            }
//
//            public boolean retire_prefix() {
//                PrefixInfo top = prefix_info_stack.peek();
//
//                if (top.num_prefixes_in_progress > 0) {
//                    top.num_prefixes_in_progress--;
//                    return true;
//                }
//
//                return false;
//            }
//        }
    }

    public static
    class SymbolBufferState
    {
        LinkLegacy<SymbolBufferSymbol> symbol_cursor = null;
        int restriction_nesting_level = 0;

        public SymbolBufferState() {}

        public SymbolBufferState(SymbolBufferState state) {
            symbol_cursor = state.symbol_cursor;
            restriction_nesting_level = state.restriction_nesting_level;
        }
    }
}
