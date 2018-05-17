package edsel.lib.cfg_parser.parse_node;

import edsel.lib.cfg_parser.RCFG_Parser;
import lib.java_lang_extensions.parametrized_types.InstantiatorBase;

public abstract class ParseNode
    extends InstantiatorBase<ParseNode>
{
    public
    RCFG_Parser.SymbolBuffer.SymbolBufferString src_string;

    public ParseNode(RCFG_Parser.SymbolBuffer.SymbolBufferString src_string) {
        this.src_string = src_string;
    }

    public abstract String print(int tree_level);
}
