package edsel.lib.cfg_parser;

import edsel.cfgs.regex_cfg.io.RegexToken;
import edsel.lib.cfg_model.RCFG_Production;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.ReductionParseNode;
import edsel.lib.cfg_parser.parsing_restriction.ParsingRestriction;
import edsel.lib.cfg_parser.parsing_restriction.RestrictionMode;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.java_lang_extensions.parametrized_types.InstantiatorBase;
import lib.tokens.enums.CopyType;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

import static edsel.cfgs.regex_cfg.RegexTerminalID.UNRESTRICT_ID;

public abstract class RCFG_Parser
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
{
    public RCFG_Production<ENUM_PRODUCTION_ID>          start_production;
    public RCFG_Production<ENUM_PRODUCTION_ID>[]        productions;

    @SafeVarargs
    public RCFG_Parser(
            RCFG_Production<ENUM_PRODUCTION_ID>         start_production,
            RCFG_Production<ENUM_PRODUCTION_ID>...      productions)
    {
        this.start_production = start_production;
        this.productions = productions;
    }

    public abstract ReductionParseNode<ENUM_PRODUCTION_ID>
    parse_recursive(ParseNodeBuffer input)
            throws AmbiguousParserInput;
 
    public abstract ReductionParseNode<ENUM_PRODUCTION_ID>
    parse_recursive(
            RCFG_Production<ENUM_PRODUCTION_ID> production,
            ParseNodeBuffer input,
            int num_branches_explored
    )
            throws AmbiguousParserInput;


    public RCFG_Production<ENUM_PRODUCTION_ID>
    get_production(ParseNodeBuffer.ParseNodeBufferString production_name)
    {
        for(RCFG_Production<ENUM_PRODUCTION_ID> production : productions)
            if (production_name.get_string() == production.name)
                return production;

        return null;
    }

    // =========================================================================================

    public abstract class
    ParseNodeBuffer<ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
            extends InstantiatorBase<ParseNodeBuffer<ENUM_PRODUCTION_ID>>
    {
        public class ParseNodeBufferString
                extends
                InstantiatorBase<ParseNodeBufferString>
        {
            public int src_start;
            public int src_end;

            public ParseNodeBufferString(int src_start, int src_end) {
                this.src_start = src_start;
                this.src_end = src_end;
            }

            public ParseNodeBufferString(ParseNodeBuffer.ParseNodeBufferString string, CopyType copy_type) {
                this.src_start = string.src_start;
                this.src_end = string.src_end;
            }

            // =========================================================================================

            public ParseNodeBuffer.ParseNodeBufferString
            self()
            {
                return this;
            }

            public ParseNodeBuffer.ParseNodeBufferString new_instance(Object... args) {
                return Instantiator.new_instance(ParseNodeBuffer.ParseNodeBufferString.class, args);
            }

            // =========================================================================================

            public String get_string()
            {
                return new String(buf, src_start, src_end);
            }
        }

        public byte[] separator_chars = {' ', '\n', '\t'};
        public int cursor_pos = 0;
        public int restricted_mode_nesting = 0;
        public ParseNode next_parse_node = null;
        public byte[] buf;

        public ParseNodeBuffer(
                String filename,
                byte[] separator_chars,
                int cursor_pos,
                int restricted_mode_nesting,
                ParseNode next_parse_node)
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

            init(
                    separator_chars,
                    cursor_pos,
                    restricted_mode_nesting,
                    next_parse_node,
                    buf);
        }

        public ParseNodeBuffer(ParseNodeBuffer tok_buf, CopyType copy_type)
        {
            byte[]  tmp_sep_chars;

            if (copy_type == CopyType.COPY_DEEP) {
                tmp_sep_chars = new byte[tok_buf.separator_chars.length];
                System.arraycopy(tok_buf.separator_chars, 0, tmp_sep_chars, 0, tok_buf.separator_chars.length);
            } else
                tmp_sep_chars = tok_buf.separator_chars;

            init(
                    tmp_sep_chars,
                    tok_buf.cursor_pos,
                    tok_buf.restricted_mode_nesting,
                    tok_buf.next_parse_node,
                    tok_buf.buf);
        }

        public void init(
                byte[] separator_chars,
                int cursor_pos,
                int restricted_mode_nesting,
                ParseNode next_parse_node,
                byte[] buf)
        {
            this.separator_chars = separator_chars;
            this.cursor_pos = cursor_pos;
            this.restricted_mode_nesting = restricted_mode_nesting;
            this.next_parse_node = next_parse_node;
            this.buf = buf;
        }

        public Stack<ParseNodeBuffer> save_stack = new Stack<>();

        public void save() {
            save_stack.push(new_copy(CopyType.COPY_DEEP));
        }

        public void restore() {
            ParseNodeBuffer tmp = save_stack.pop();
            init(
                    tmp.separator_chars,
                    tmp.cursor_pos,
                    tmp.restricted_mode_nesting,
                    tmp.next_parse_node,
                    tmp.buf);
        }

        public ParseNode advance(int num_branches_explored)
                throws AmbiguousParserInput
        {
            eat_separators();

            ParseNode cur_parse_node = next_parse_node;



            char next_char = (char) buf[cursor_pos];


            return parse_recursive(get_production(restriction_name), this, num_branches_explored);

            next_parse_node = specialized_advance();

            return cur_parse_node;
        }

        public ParsingRestriction
        get_restriction() {
            char next_char = (char) buf[cursor_pos];

            if (next_char == '(') {

                if (cursor_pos + 1 < buf.length) {

                    char next_next_char = (char) buf[cursor_pos + 1];

                    RestrictionMode mode = RestrictionMode.get_mode(next_next_char);

                    if (mode == null)
                        return null;

                    cursor_pos += 2;

                    int restriction_name_start = cursor_pos;

                    while (cursor_pos < buf.length && buf[cursor_pos] != ':')
                        cursor_pos++;

                    ParseNodeBuffer.ParseNodeBufferString restriction_name
                            = new ParseNodeBuffer.ParseNodeBufferString(restriction_name_start, cursor_pos);

                    cursor_pos++;
                    restricted_mode_nesting++;

                    return parse_recursive(get_production(restriction_name), this, num_branches_explored);
                }
        }

        public abstract ParseNode specialized_advance();

        public ParseNode peek() {
            return next_parse_node;
        }

        public boolean not_empty() {
            return next_parse_node != null;
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

        public ParseNodeBuffer.ParseNodeBufferString get_buffer_string(int start, int end)
        {
            return new ParseNodeBuffer.ParseNodeBufferString(start, end);
        }

        public String get_string(int start, int end) {
            return new String(buf, start, end);
        }
    }
}
