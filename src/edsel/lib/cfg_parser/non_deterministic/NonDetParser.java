package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.*;
import edsel.lib.cfg_parser.RCFG_Parser;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.Reduction;
import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.cfg_parser.parsing_restriction.ParsingRestriction;
import edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction;
import edsel.lib.cfg_parser.parsing_restriction.TerminalRestriction;

public class NonDetParser
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
    extends
        RCFG_Parser<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
{
    @SafeVarargs
    public NonDetParser(
            RCFG_Production<ENUM_PRODUCTION_ID>         start_production,
            RCFG_Production<ENUM_PRODUCTION_ID>...      productions)
    {
        super(start_production, productions);
    }

    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(SymbolBuffer input)
            throws AmbiguousParserInput
    {
        return parse_recursive(start_production, input, 0);
    }

    @SuppressWarnings("unchecked")
    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            RCFG_Production<ENUM_PRODUCTION_ID> production,
            SymbolBuffer input,
            int num_branches_explored
    )
            throws AmbiguousParserInput
    {
        Reduction<ENUM_PRODUCTION_ID> reduction = null;

        branch_loop:
        for (int i = 0; i < production.rhs.length; i++) {

            RCFG_Symbol[] cur_branch = production.rhs[i];

            ParseNode[] sub_reductions = new ParseNode[cur_branch.length];

            input.save();

            for (int j = 0; j < cur_branch.length; j++) {

                RCFG_Symbol cur_expected_symbol = cur_branch[j];
                ParsingRestriction restriction = input.restriction_stack.peek();

                if (restriction != null) {

                    if (restriction instanceof ProductionRestriction) {

                        ProductionRestriction production_restriction = (ProductionRestriction) restriction;

                        if (cur_expected_symbol instanceof RCFG_Production) {
                            RCFG_Production<ENUM_PRODUCTION_ID> cur_expected_production
                                    = (RCFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol;

                            if (cur_expected_production.id != production_restriction.production.id)
                                continue branch_loop;
                        } else
                            continue branch_loop;
                    } else {
                        TerminalRestriction terminal_restriction = (TerminalRestriction) restriction;

                        if (cur_expected_symbol instanceof RCFG_Terminal) {
                            RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_expected_terminal
                                    = (RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_expected_symbol;

                            if (cur_expected_terminal.id != terminal_restriction.terminal.id)
                                continue branch_loop;
                        } else
                            continue branch_loop;
                    }
                }

                if (next_input == null) {
                    input.restore();
                    continue branch_loop;
                }

                if (cur_expected_symbol instanceof RCFG_Production) {

                    Reduction<ENUM_PRODUCTION_ID> cur_sub_reduction;

                    if (next_input instanceof Token) {
                        input.restore();
                        continue branch_loop;
                    }

                    if (next_input instanceof Reduction) {

                        ENUM_PRODUCTION_ID cur_expected_production_id
                                = ((RCFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol).id;

                        ENUM_PRODUCTION_ID cur_production_id
                                = ((Reduction<ENUM_PRODUCTION_ID>) next_input).production_id;

                        cur_sub_reduction = (Reduction<ENUM_PRODUCTION_ID>) next_input;

                        if (cur_sub_reduction.production_id == cur_expected_symbol.id) {
                            sub_reductions[j] = cur_sub_reduction;
                            input.update_restriction();
                        } else {
                            input.restore();
                            continue branch_loop;
                        }
                    } else {
                        RCFG_Production<ENUM_PRODUCTION_ID>
                                rhs_production = (RCFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol;

                        cur_sub_reduction
                                = parse_recursive(rcfg, rhs_production, input, num_branches_explored + 1);

                        if (cur_sub_reduction != null) {
                            sub_reductions[j] = cur_sub_reduction;
                            input.update_restriction();
                        } else {
                            input.restore();
                            continue branch_loop;
                        }
                    }
                } else if (cur_expected_symbol instanceof RCFG_Terminal) {

                    if (next_input instanceof Reduction) {
                        input.restore();
                        continue branch_loop;
                    }

                    RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_expected_terminal
                            = (RCFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_expected_symbol;

                    Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_terminal
                            = (Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) next_input;

                    if (cur_terminal.id == cur_expected_terminal.id) {
                        cur_expected_terminal.reduce(cur_terminal);
                        sub_reductions[j] = next_input;
                        input.update_restriction();
                    } else {
                        input.restore();
                        continue branch_loop;
                    }
                }
            }

            if (reduction != null) {
                input.restore();
                throw new AmbiguousParserInput();
            }

            int src_text_start = sub_reductions[0].src_string.src_start;
            int src_text_end = sub_reductions[sub_reductions.length - 1].src_string.src_end;
            ParseNodeBufferString string = input.get_buffer_string(src_text_start, src_text_end);

            reduction = production.reduce(i, sub_reductions, num_branches_explored, string);
        }

        return reduction;
    }
}
