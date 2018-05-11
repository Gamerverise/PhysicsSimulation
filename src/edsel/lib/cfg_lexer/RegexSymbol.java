package edsel.lib.cfg_lexer;

import edsel.lib.cfg_model.RCFG_Symbol;
import edsel.lib.cfg_parser.regex_cfg.RegexProduction;
import edsel.lib.cfg_parser.regex_cfg.RegexProductionID;
import edsel.lib.cfg_parser.regex_cfg.RegexTerminal;
import edsel.lib.cfg_parser.regex_cfg.RegexTerminalID;

public interface
RegexSymbol<REDUCTION_TYPE>
        extends RCFG_Symbol
        <RegexTerminal<REDUCTION_TYPE>,
                RegexProduction<REDUCTION_TYPE>,
                RegexSymbol<REDUCTION_TYPE>,
                RegexTerminalID,
                Character,
                RegexProductionID,
                REDUCTION_TYPE>
{}
