package edsel.lib.cfg_parser.parse_node;

import edsel.lib.io.ParseNodeBuffer;
import lib.java_lang_extensions.parametrized_types.InstantiatorBase;

public abstract class ParseNode
    extends InstantiatorBase<ParseNode>
{
    public
    ParseNodeBuffer.ParseNodeBufferString src_string;

    public ParseNode(ParseNodeBuffer.ParseNodeBufferString src_string) {
        this.src_string = src_string;
    }

    public abstract String print(int tree_level);
}
