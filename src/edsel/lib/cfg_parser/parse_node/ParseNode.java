package edsel.lib.cfg_parser.parse_node;

import edsel.lib.io.SymbolBuffer;

public abstract class ParseNode
    extends InstantiatorBase<ParseNode>
{
    public
    SymbolBuffer.SymbolBufferString src_string;

    public ParseNode(SymbolBuffer.SymbolBufferString src_string) {
        this.src_string = src_string;
    }

    public abstract String print(int tree_level);
}
