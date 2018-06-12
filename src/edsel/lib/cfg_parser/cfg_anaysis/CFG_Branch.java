package edsel.lib.cfg_parser.cfg_anaysis;

import edsel.lib.cfg_model.CFG_Production;

public class CFG_Branch
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
{
    CFG_Production production;
    int branch_num;
}
