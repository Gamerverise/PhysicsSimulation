package edsel.lib.cfg_parser;

import edsel.lib.cfg_model.*;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.Reduction;
import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.cfg_parser.parsing_restriction.ParsingRestriction;
import edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction;
import edsel.lib.cfg_parser.parsing_restriction.RestrictionMode;
import edsel.lib.cfg_parser.parsing_restriction.TerminalRestriction;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.java_lang_extensions.parametrized_types.InstantiatorBase;
import lib.tokens.enums.CopyType;
import edsel.lib.cfg_parser.RCFG_Parser.SymbolBuffer.SymbolBufferString;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

import static edsel.lib.cfg_parser.parsing_restriction.RestrictionMode.*;

public abstract class RCFG_Parser
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
{
    public RCFG_Production<ENUM_PRODUCTION_ID>[]                    productions;
    public RCFG_Production<ENUM_PRODUCTION_ID>                      start_production;
    public RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>[]      terminals;

    @SafeVarargs
    public RCFG_Parser(
            RCFG_Production<ENUM_PRODUCTION_ID>         start_production,
            RCFG_Production<ENUM_PRODUCTION_ID>...      productions)
    {
        this.start_production = start_production;
        this.productions = productions;
    }

    public abstract Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(SymbolBuffer input)
            throws AmbiguousParserInput;
 
    public abstract Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            RCFG_Production<ENUM_PRODUCTION_ID> production,
            SymbolBuffer input,
            int num_branches_explored
    )
            throws AmbiguousParserInput;
    
    public RCFG_Production<ENUM_PRODUCTION_ID>
    get_production(SymbolBufferString production_name)
    {
        for(RCFG_Production<ENUM_PRODUCTION_ID> production : productions)
            if (production_name.get_string() == production.name)
                return production;

        return null;
    }

    public RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
    get_terminal(SymbolBufferString terminal_name)
    {
        for(RCFG_Terminal<ENUM_TERMINAL_ID,  TOKEN_VALUE_TYPE> terminal : terminals)
            if (terminal_name.get_string() == terminal.name)
                return terminal;

        return null;
    }

    // =========================================================================================

    public abstract class
    SymbolBuffer extends InstantiatorBase<SymbolBuffer>
    {
        public class SymbolBufferString
                extends
                InstantiatorBase<SymbolBufferString>
        {
            public int src_start;
            public int src_end;

            public SymbolBufferString(int src_start, int src_end) {
                this.src_start = src_start;
                this.src_end = src_end;
            }

            public SymbolBufferString(SymbolBufferString string, CopyType copy_type) {
                this.src_start = string.src_start;
                this.src_end = string.src_end;
            }

            // =========================================================================================

            public SymbolBufferString
            self()
            {
                return this;
            }

            public SymbolBufferString new_instance(Object... args) {
                return Instantiator.new_instance(SymbolBufferString.class, args);
            }

            // =========================================================================================

            public String get_string()
            {
                return new String(buf, src_start, src_end);
            }
        }

        public byte[] separator_chars = {' ', '\n', '\t'};
        public int cursor_pos = 0;
        public Stack<ParsingRestriction> restriction_stack = new Stack<>();
        public byte[] buf;

        public SymbolBuffer(String filename, byte[] separator_chars, int cursor_pos)
                throws InputNotAccepted
        {
            try {
                Path path = FileSystems.getDefault().getPath(filename);
                long input_size = Files.size(path);

                if (input_size > Integer.MAX_VALUE)
                    throw new RuntimeException();

                buf = Files.readAllBytes(path);
            } catch (IOException e) {
                throw new RuntimeException();
            }

            init(separator_chars, cursor_pos, buf);
        }

        public SymbolBuffer(SymbolBuffer tok_buf, CopyType copy_type)
                throws InputNotAccepted
        {
            byte[]  tmp_sep_chars;

            if (copy_type == CopyType.COPY_DEEP) {
                tmp_sep_chars = new byte[tok_buf.separator_chars.length];
                System.arraycopy(tok_buf.separator_chars, 0, tmp_sep_chars, 0, tok_buf.separator_chars.length);
            } else
                tmp_sep_chars = tok_buf.separator_chars;

            init(tmp_sep_chars, tok_buf.cursor_pos, tok_buf.buf);
        }

        public void init(byte[] separator_chars, int cursor_pos, byte[] buf)
                throws InputNotAccepted
        {
            this.separator_chars = separator_chars;
            this.cursor_pos = cursor_pos;
            this.buf = buf;

            update_restriction(null);
        }

        public Stack<SymbolBuffer> save_stack = new Stack<>();

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

            SymbolBufferString restriction_name
                    = new SymbolBufferString(restriction_name_start, cursor_pos);

            cursor_pos++;

            if (mode == TERMINAL_RESTRICTION) {
                RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> next_terminal_restriction
                        = get_terminal(restriction_name);

                if (next_terminal_restriction != null) {
                    restriction_stack.push(new TerminalRestriction<>(next_terminal_restriction, mode));
                }
            } else if (mode == PRODUCTION_RESTRICTION) {
                RCFG_Production<ENUM_PRODUCTION_ID> next_production_restriction
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

        public SymbolBufferString get_buffer_string(int start, int end)
        {
            return new SymbolBufferString(start, end);
        }

        public String get_string(int start, int end) {
            return new String(buf, start, end);
        }
    }
}
