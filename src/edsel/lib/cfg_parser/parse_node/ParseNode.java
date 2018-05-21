package edsel.lib.cfg_parser.parse_node;

import edsel.lib.io.CharBuffer;
import lib.java_lang_extensions.parametrized_types.Copyable;

public abstract class ParseNode
    extends Copyable<ParseNode>
{
    public CharBuffer<? extends CharBuffer>.CharBufferString src_string;

    public ParseNode(CharBuffer<? extends CharBuffer>.CharBufferString src_string) {
        this.src_string = src_string;
    }

    public abstract String print(int tree_level);
}
