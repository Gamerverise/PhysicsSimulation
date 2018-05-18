package edsel.lib.cfg_parser.non_deterministic;

import edsel.lib.cfg_model.*;
import edsel.lib.cfg_parser.CFG_Parser;
import edsel.lib.cfg_parser.exception.AmbiguousParserInput;
import edsel.lib.cfg_parser.exception.InputNotAccepted;
import edsel.lib.cfg_parser.parse_node.ParseNode;
import edsel.lib.cfg_parser.parse_node.Reduction;
import edsel.lib.cfg_parser.parse_node.Token;
import edsel.lib.cfg_parser.parsing_restriction.ParsingRestriction;
import edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction;
import edsel.lib.cfg_parser.parsing_restriction.TerminalRestriction;
import edsel.lib.io.SymbolBuffer;
import lib.java_lang_extensions.parametrized_types.Instantiator;
import lib.java_lang_extensions.parametrized_types.InstantiatorBase;
import lib.tokens.enums.CopyType;

public class NonDetParser
        <ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TOKEN_VALUE_TYPE>
    extends
        CFG_Parser<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>
{
    @SafeVarargs
    public NonDetParser(
            CFG_Production<ENUM_PRODUCTION_ID>      start_production,
            CFG_Production<ENUM_PRODUCTION_ID>...   productions)
    {
        super(start_production, productions);
    }

    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            SymbolBuffer<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> input
    )
            throws AmbiguousParserInput, InputNotAccepted
    {
        return parse_recursive(start_production, input, 0);
    }

    @SuppressWarnings("unchecked")
    public Reduction<ENUM_PRODUCTION_ID>
    parse_recursive(
            CFG_Production<ENUM_PRODUCTION_ID> production,
            SymbolBuffer<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> input,
            int num_branches_explored
    )
            throws AmbiguousParserInput, InputNotAccepted
    {
        Reduction<ENUM_PRODUCTION_ID> reduction = null;

        branch_loop:
        for (int i = 0; i < production.rhs.length; i++) {

            CFG_Symbol[] cur_branch = production.rhs[i];

            ParseNode[] sub_reductions = new ParseNode[cur_branch.length];

            input.save();

            for (int j = 0; j < cur_branch.length; j++) {

                CFG_Symbol cur_expected_symbol = cur_branch[j];
                ParsingRestriction restriction = input.restriction_stack.peek();

                if (restriction != null) {

                    if (restriction instanceof ProductionRestriction) {

                        ProductionRestriction production_restriction = (ProductionRestriction) restriction;

                        if (cur_expected_symbol instanceof CFG_Production) {
                            CFG_Production<ENUM_PRODUCTION_ID> cur_expected_production
                                    = (CFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol;

                            if (cur_expected_production.id != production_restriction.production.id) {
                                input.restore();
                                continue branch_loop;
                            }
                        } else {
                            input.restore();
                            continue branch_loop;
                        }
                    } else {
                        TerminalRestriction terminal_restriction = (TerminalRestriction) restriction;

                        if (cur_expected_symbol instanceof CFG_Terminal) {
                            CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_expected_terminal
                                    = (CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_expected_symbol;

                            if (cur_expected_terminal.id != terminal_restriction.terminal.id) {
                                input.restore();
                                continue branch_loop;
                            }
                        } else {
                            input.restore();
                            continue branch_loop;
                        }
                    }
                }

                if (cur_expected_symbol instanceof CFG_Production) {

                    Reduction<ENUM_PRODUCTION_ID> cur_sub_reduction;
                    CFG_Production<ENUM_PRODUCTION_ID>
                            rhs_production = (CFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol;

                    cur_sub_reduction = parse_recursive(rhs_production, input, num_branches_explored + 1);

                    if (cur_sub_reduction == null) {
                        input.restore();
                        continue branch_loop;
                    } else
                        sub_reductions[j] = cur_sub_reduction;
                } else {
                    Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_token = input.token_advance();

                    if (cur_token == null) {
                        input.restore();
                        continue branch_loop;
                    }

                    CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_expected_terminal
                            = (CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_expected_symbol;

                    if (cur_token.id == cur_expected_terminal.id) {
                        cur_expected_terminal.reduce(cur_token);
                        sub_reductions[j] = cur_token;
                        input.update_restriction(cur_token);
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
            SymbolBuffer<ENUM_PRODUCTION_ID, ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>.SymbolBufferString
                    string = input.get_buffer_string(src_text_start, src_text_end);

            reduction = production.reduce(i, sub_reductions, num_branches_explored, string);
        }

        return reduction;
    }
}
