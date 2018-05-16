package edsel.lib.cfg_parser.parsing_state;

import edsel.lib.cfg_parser.RCFG_Parser;
import edsel.lib.cfg_model.RCFG_Production;
import edsel.lib.cfg_parser.parsing_restriction.ParsingRestriction;
import edsel.lib.io.old.ParseNodeBuffer;

public class ParsingState
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                PRODUCTION_TYPE extends
                        RCFG_Production<ENUM_PRODUCTION_ID>>
{
    public RCFG_Parser<ENUM_PRODUCTION_ID, PRODUCTION_TYPE> rcfg;
    public ParsingRestriction cur_restriction;
    public ParseNodeBuffer buf;
    int num_branches_explored;
}
