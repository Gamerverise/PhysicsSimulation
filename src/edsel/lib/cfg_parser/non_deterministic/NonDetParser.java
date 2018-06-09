package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.CFG_Production;
import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.CFG_Terminal;
import edsel.lib.cfg_parser.CFG_Parser;
import edsel.lib.cfg_parser.SymbolBufferSymbol;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.Reduction;
import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.cfg_parser.parsing_restriction.BranchRestriction;
import edsel.lib.cfg_parser.parsing_restriction.EndRestriction;
import edsel.lib.cfg_parser.parsing_restriction.GateRestriction;
import edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction;
import edsel.lib.io.CharBuffer.CharBufferString;

import static edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction.RestrictionMode.EXACT_MODE;
import static edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction.RestrictionMode.PREFIX_MODE;
import static lib.text_io.FormattedText.indent;
import static lib.text_io.FormattedText.spaces;

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
                                        SYMBOL_BUFFER_TYPE,
                                        NonDetParsingState
                                                <ENUM_PRODUCTION_ID,
                                                        ENUM_TERMINAL_ID,
                                                        TOKEN_VALUE_TYPE,
                                                        SYMBOL_BUFFER_TYPE>>.SymbolBuffer,
                PARSING_STATE_TYPE extends
                        NonDetParsingState
                                <ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        SYMBOL_BUFFER_TYPE>>
        extends
        CFG_Parser
                <ENUM_PRODUCTION_ID,
                        ENUM_TERMINAL_ID,
                        TOKEN_VALUE_TYPE,
                        SYMBOL_BUFFER_TYPE,
                        NonDetParsingState
                                <ENUM_PRODUCTION_ID,
                                        ENUM_TERMINAL_ID,
                                        TOKEN_VALUE_TYPE,
                                        SYMBOL_BUFFER_TYPE>>
{
    @SafeVarargs
    public NonDetParser(
            CFG_Production<ENUM_PRODUCTION_ID>                                          start_production,
            CFG_Production<ENUM_PRODUCTION_ID>[]                                        productions,
            CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>...     terminals)
    {
        super(start_production, productions, terminals);
    }

    @SuppressWarnings("unchecked")
    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            PARSING_STATE_TYPE state
    )
            throws AmbiguousParserInput, InputNotAccepted
    {
//        System.out.println(indent(2 * state.get_cur_depth(), production.sprint_id()));

        state.set_last_symbol_explored(production);
        state.inc_cur_depth();

        Reduction<ENUM_PRODUCTION_ID> reduction = null;

        NonDetParsingState<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
                result_state = null;

        for (int i = 0; i < production.rhs.length; i++) {

            NonDetParsingState<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
                    branch_state = state.branch();

            Reduction<ENUM_PRODUCTION_ID> tmp_reduction = parse_branch_recursive(production, i, branch_state);

            state.num_branches_explored += branch_state.num_branches_explored;

            if (tmp_reduction != null) {
                if (reduction != null)
                    throw new AmbiguousParserInput();
                else {
                    System.out.println(
                            new StringBuilder(indent(state.get_cur_depth(), "Reduced "))
                                    .append(tmp_reduction.sprint(0)));

                    reduction = tmp_reduction;
                    result_state = branch_state;
                }
            }
        }

        state.dec_cur_depth();

        if (reduction == null)
            return null;

        state.accept_branch(result_state);

        return reduction;
    }

    public Reduction<ENUM_PRODUCTION_ID>
    parse_branch_restriction_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            int branch_num,
            PARSING_STATE_TYPE state
    )
            throws AmbiguousParserInput, InputNotAccepted
    {
        state.set_last_symbol_explored(production);
        state.inc_cur_depth();

        return parse_branch_recursive(production, branch_num, state);
    }

    @SuppressWarnings("unchecked")
    public Reduction<ENUM_PRODUCTION_ID>
    parse_branch_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            int branch_num,
            PARSING_STATE_TYPE state
    )
            throws AmbiguousParserInput, InputNotAccepted
    {
//        System.out.println(indent(2*(state.get_cur_depth() - 1) + 1, production.sprint_branch(branch_num)));
        System.out.println(
                indent(
                        state.get_cur_depth() - 1,
                        new StringBuilder(production.sprint_id())
                                .append(", ")
                                .append(production.sprint_branch(branch_num))
                                .toString()));

        state.num_branches_explored++;

        CFG_Symbol[] cur_branch = production.rhs[branch_num];

        ParseNode[] sub_reductions = new ParseNode[cur_branch.length];

        for (int j = 0; j < cur_branch.length; j++) {

            SymbolBufferSymbol cur_symbol = state.input.next_symbol();

            if (cur_symbol == null)
                return null;

            else if (cur_symbol instanceof GateRestriction && j != 0)
                return null;

            else if (cur_symbol instanceof EndRestriction) {

                if (state.get_prefixes_in_progress() <= 0)
                    return null;

                state.retire_prefix();
                j--;
            } else {

                CFG_Symbol cur_expected_symbol = cur_branch[j];

                if (cur_expected_symbol instanceof CFG_Production) {

                    CFG_Production<ENUM_PRODUCTION_ID> cur_expected_production
                            = (CFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol;

                    if (state.chk_consecutive_expected_production(cur_expected_production))
                        return null;

                    state.set_last_symbol_explored(cur_expected_production);

                    if (cur_symbol instanceof ProductionRestriction) {

                        ProductionRestriction<ENUM_PRODUCTION_ID> production_restriction
                                = (ProductionRestriction<ENUM_PRODUCTION_ID>) cur_symbol;

                        if (cur_expected_production.id == production_restriction.production.id) {

                            if (production_restriction.mode == PREFIX_MODE)
                                state.inc_prefixes_in_progress();

                            if (cur_symbol instanceof BranchRestriction) {

                                BranchRestriction<ENUM_PRODUCTION_ID> branch_restriction
                                        = (BranchRestriction<ENUM_PRODUCTION_ID>) cur_symbol;

                                sub_reductions[j]
                                        =
                                        parse_branch_restriction_recursive(
                                                branch_restriction.production,
                                                branch_restriction.branch_num,
                                                state);
                            } else
                                sub_reductions[j]
                                        =
                                        parse_recursive(production_restriction.production, state);

                            if (sub_reductions[j] == null)
                                return null;
                        } else
                            return null;

                        if (production_restriction.mode == PREFIX_MODE) {
                            state.inc_prefixes_to_retire();

                        } if (production_restriction.mode == EXACT_MODE) {

                            cur_symbol = state.input.next_symbol();

                            if (!(cur_symbol instanceof EndRestriction))
                                return null;
                        }
                    } else {
                        sub_reductions[j] = parse_recursive(cur_expected_production, state);

                        if (sub_reductions[j] == null)
                            return null;
                    }
                } else if (cur_expected_symbol instanceof CFG_Terminal) {

                    System.out.println(
                            indent(
                                    state.get_cur_depth(),
                                    cur_expected_symbol.sprint_id()));

                    if (cur_symbol instanceof Token) {

                        Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> token
                                = (Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_symbol;

                        CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
                                cur_expected_terminal
                                =
                                (CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>)
                                        cur_expected_symbol;

                        if (token.id == cur_expected_terminal.id) {

                            System.out.println(
                                    new StringBuilder(indent(state.get_cur_depth() + 1, "Accepted token: "))
                                            .append(token.sprint(0)));

                            state.set_last_symbol_explored(cur_expected_terminal);
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

        CharBufferString
                string = state.input.get_string(CharBufferString.class, src_text_start, src_text_end);

        return production.reduce(branch_num, sub_reductions, state.num_branches_explored, string);
    }

}
