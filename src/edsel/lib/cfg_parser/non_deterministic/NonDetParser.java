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
import edsel.lib.cfg_parser.parsing_restriction.TerminalRestriction;
import edsel.lib.io.CharBuffer.CharBufferString;
import lib.java_lang_extensions.mutable.MutableInt;

import static edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction.RestrictionMode.*;

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

    @SuppressWarnings("unchecked")
    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            SymbolBuffer<SYMBOL_BUFFER_TYPE> input,
            MutableInt num_branches_explored
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
            MutableInt num_branches_explored
    )
            throws AmbiguousParserInput, InputNotAccepted
    {
        num_branches_explored.value++;

        CFG_Symbol[] cur_branch = production.rhs[branch_num];

        ParseNode[] sub_reductions = new ParseNode[cur_branch.length];

        for (int j = 0; j < cur_branch.length; j++) {

            SymbolBufferSymbol cur_symbol = input.next_symbol();

            if (cur_symbol == null)
                return null;
            else {

                CFG_Symbol cur_expected_symbol = cur_branch[j];

                if (cur_expected_symbol instanceof CFG_Production) {

                    if (cur_symbol instanceof TerminalRestriction)
                        return null;

                    CFG_Production<ENUM_PRODUCTION_ID> cur_expected_production
                            = (CFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol;

                    if (cur_symbol instanceof ProductionRestriction) {

                        ProductionRestriction<ENUM_PRODUCTION_ID> production_restriction
                                = (ProductionRestriction<ENUM_PRODUCTION_ID>) cur_symbol;

                        if (cur_expected_production.id == production_restriction.production.id) {

                            if (cur_symbol instanceof BranchRestriction) {

                                BranchRestriction<ENUM_PRODUCTION_ID> branch_restriction
                                        = (BranchRestriction<ENUM_PRODUCTION_ID>) cur_symbol;

                                sub_reductions[j]
                                        =
                                        parse_branch_recursive(
                                                branch_restriction.production,
                                                branch_restriction.branch_num,
                                                input,
                                                num_branches_explored);
                            } else
                                sub_reductions[j]
                                        =
                                        parse_recursive(
                                                production_restriction.production,
                                                input,
                                                num_branches_explored);

                            if (sub_reductions[j] == null)
                                return null;
                        } else
                            return null;

                        if (production_restriction.mode == EXACT_MODE) {

                            cur_symbol = input.next_symbol();

                            if (!(cur_symbol instanceof EndRestriction))
                                return null;
                        }

                        else do what for prefix mode?
                    } else
                        parse_recursive(cur_expected_production, input, num_branches_explored);

                } else if (cur_expected_symbol instanceof CFG_Terminal) {

                    if (cur_symbol instanceof Token) {

                        Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> token
                                = (Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_symbol;

                        CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_expected_terminal
                                = (CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_expected_symbol;

                        if (token.id == cur_expected_terminal.id) {

                            cur_expected_terminal.reduce(token);
                            sub_reductions[j] = token;
                        } else
                            return null;
                    } else
                        return null;
                } else
                    assert false;
            }
        }

        int src_text_start = sub_reductions[0].src_string.src_start;
        int src_text_end = sub_reductions[sub_reductions.length - 1].src_string.src_end;
        CharBufferString string = input.get_string(CharBufferString.class, src_text_start, src_text_end);

        return production.reduce(branch_num, sub_reductions, num_branches_explored.value, string);
    }
}
