package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_parser.CFG_Parser;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.Reduction;
import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.cfg_parser.parsing_restriction.BranchRestriction;
import edsel.lib.cfg_parser.parsing_restriction.EndRestriction;
import edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction;
import edsel.lib.io.CharBuffer.CharBufferString;

public abstract
class NonDetParser
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE,
                SYMBOL_BUFFER_TYPE extends
                        CFG_Parser
                                <ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        SYMBOL_BUFFER_TYPE>.SymbolBuffer<SYMBOL_BUFFER_TYPE>>
    extends
        CFG_Parser<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
{
    @SafeVarargs
    public NonDetParser(
            CFG_Production<ENUM_PRODUCTION_ID>                      start_production,
            CFG_Production<ENUM_PRODUCTION_ID>[]                    productions,
            CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>...     terminals)
    {
        super(start_production, productions, terminals);
    }

    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            SymbolBuffer<SYMBOL_BUFFER_TYPE> input
    )
            throws AmbiguousParserInput, InputNotAccepted
    {
        return parse_recursive(start_production, input, 0);
    }

    @SuppressWarnings("unchecked")
    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            SymbolBuffer<SYMBOL_BUFFER_TYPE> input,
            int num_branches_explored
    )
            throws AmbiguousParserInput, InputNotAccepted
    {
        Reduction<ENUM_PRODUCTION_ID> reduction = null;

        for (int i = 0; i < production.rhs.length; i++) {

            input.save();

            Reduction<ENUM_PRODUCTION_ID> tmp_reduction
                    = parse_branch_recursive(production, i, input, num_branches_explored);

            if (tmp_reduction == null) {
                input.restore();
                continue;
            } else if (reduction == null)
                    reduction = tmp_reduction;
            else
                throw new AmbiguousParserInput();
        }
        return reduction;
    }

    @SuppressWarnings("unchecked")
    public Reduction<ENUM_PRODUCTION_ID>
    parse_branch_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            int branch_num,
            SymbolBuffer<SYMBOL_BUFFER_TYPE> input,
            int num_branches_explored
    )
            throws AmbiguousParserInput, InputNotAccepted
    {
        Reduction<ENUM_PRODUCTION_ID> reduction = null;

        CFG_Symbol[] cur_branch = production.rhs[branch_num];

        ParseNode[] sub_reductions = new ParseNode[cur_branch.length];

        for (int j = 0; j < cur_branch.length; j++) {

            SymbolBufferSymbol cur_symbol = input.next_symbol();

            if (cur_symbol != null) {

                CFG_Symbol cur_expected_symbol = cur_branch[j];

                if (cur_expected_symbol instanceof CFG_Production) {

                    if (cur_symbol instanceof ProductionRestriction) {

                        ProductionRestriction<ENUM_PRODUCTION_ID> production_restriction
                                = (ProductionRestriction<ENUM_PRODUCTION_ID>) cur_symbol;

                        CFG_Production<ENUM_PRODUCTION_ID> cur_expected_production
                                = (CFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol;

                        if (cur_expected_production.id == production_restriction.production.id) {

                            Reduction<ENUM_PRODUCTION_ID> cur_sub_reduction;

                            if (cur_symbol instanceof BranchRestriction) {

                                BranchRestriction<ENUM_PRODUCTION_ID> branch_restriction
                                        = (BranchRestriction<ENUM_PRODUCTION_ID>) cur_symbol;

                                sub_reductions[j]
                                        =
                                        parse_branch_recursive(
                                                branch_restriction.production,
                                                branch_restriction.branch_num,
                                                input,
                                                num_branches_explored + 1);
                            } else
                                sub_reductions[j]
                                        =
                                        parse_recursive(
                                                production_restriction.production,
                                                input,
                                                num_branches_explored + 1);

                            if (sub_reductions[j] == null)
                                return null;
                        }
                    }
                } else if (cur_symbol instanceof EndRestriction) {

                } else if (cur_expected_symbol instanceof CFG_Terminal) {

                    if (cur_symbol instanceof Token) {

                        Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> token
                                = (Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_symbol;

                        CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_expected_terminal
                                = (CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_expected_symbol;

                        if (token.id == cur_expected_terminal.id) {

                            cur_expected_terminal.reduce(token);
                            sub_reductions[j] = token;
                        }
                    }
                }
            }
            return null;
        }

        int src_text_start = sub_reductions[0].src_string.src_start;
        int src_text_end = sub_reductions[sub_reductions.length - 1].src_string.src_end;
        CharBufferString string = input.get_string(CharBufferString.class, src_text_start, src_text_end);

        return production.reduce(branch_num, sub_reductions, num_branches_explored, string);
    }
}
