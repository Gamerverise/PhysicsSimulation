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
import edsel.lib.cfg_parser.parsing_restriction.*;
import edsel.lib.io.CharBuffer.CharBufferString;
import lib.debug.MethodNameHack;

import static lib.debug.AssertMessages.NOT_YET_IMPLEMENTED;
import static lib.debug.Debug.assert_msg;

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

        branch_loop:
        for (int i = 0; i < production.rhs.length; i++) {

            CFG_Symbol[] cur_branch = production.rhs[i];

            ParseNode[] sub_reductions = new ParseNode[cur_branch.length];


            /*

                                if (last_parse_node instanceof Reduction) {
                        if (!(cur_restriction instanceof ProductionRestriction))
                            return;

                        Reduction<ENUM_PRODUCTION_ID> last_reduction;
                        ProductionRestriction<ENUM_PRODUCTION_ID> cur_production_restriction;

                        last_reduction = (Reduction<ENUM_PRODUCTION_ID>) last_parse_node;
                        cur_production_restriction = (ProductionRestriction<ENUM_PRODUCTION_ID>) cur_restriction;

                        if (last_reduction.production_id != cur_production_restriction.production.id)
                            return;
                    } else {
                        if (!(cur_restriction instanceof TerminalRestriction))
                            return;

                        Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> last_token;
                        TerminalRestriction<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_terminal_restriction;

                        last_token = (Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) last_parse_node;
                        cur_terminal_restriction
                                = (TerminalRestriction<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_restriction;

                        if (last_token.id != cur_terminal_restriction.terminal.id)
                            return;
                    }

             */

            for (int j = 0; j < cur_branch.length; j++) {

                input.save();

                CFG_Symbol cur_expected_symbol = cur_branch[j];

                SymbolBufferSymbol cur_symbol = input.next_symbol();

                if (cur_expected_symbol == null)
                    continue;

                else if (cur_expected_symbol instanceof CFG_Production) {

                    if (cur_symbol instanceof ProductionRestriction) {

                        CFG_Production<ENUM_PRODUCTION_ID> cur_expected_production
                                = (CFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol;

                        ProductionRestriction<ENUM_PRODUCTION_ID> production_restriction
                                = (ProductionRestriction<ENUM_PRODUCTION_ID>) cur_symbol;

                        if (cur_expected_production.id == production_restriction.production.id) {

                            if (cur_symbol instanceof BranchRestriction) {

                                assert false : assert_msg(
                                        this.getClass(),
                                        new MethodNameHack(){}.method_name(),
                                        NOT_YET_IMPLEMENTED);

                                BranchRestriction<ENUM_PRODUCTION_ID> branch_restriction
                                        = (BranchRestriction<ENUM_PRODUCTION_ID>) cur_symbol;

                                if (j == branch_restriction.branch_num) {

                                }
                            } else {
                                Reduction<ENUM_PRODUCTION_ID> cur_sub_reduction;

                                CFG_Production<ENUM_PRODUCTION_ID>
                                        rhs_production = (CFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol;

                                cur_sub_reduction = parse_recursive(rhs_production, input, num_branches_explored + 1);

                                Reduction<ENUM_PRODUCTION_ID> cur_sub_reduction;
                                CFG_Production<ENUM_PRODUCTION_ID>
                                        rhs_production = (CFG_Production<ENUM_PRODUCTION_ID>) cur_expected_symbol;

                                cur_sub_reduction = parse_recursive(rhs_production, input, num_branches_explored + 1);

                                if (cur_sub_reduction == null) {
                                    input.restore();
                                    continue branch_loop;
                                } else
                                    sub_reductions[j] = cur_sub_reduction;
                            }
                        }
                    }
                } if (cur_expected_symbol instanceof CFG_Terminal) {

                    if (cur_symbol instanceof Token) {

                        CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> cur_expected_terminal
                                = (CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_expected_symbol;

                        Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> token
                                = (Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_symbol;

                        if (token.id == cur_expected_terminal.id) {

                            cur_expected_terminal.reduce(token);
                            sub_reductions[j] = token;
                            input.symbol_advance(cur_token);
                        }
                    }
                } else if (cur_symbol instanceof EndRestriction) {


                } else if (cur_symbol instanceof Token) {

                    if (cur_token.id == cur_expected_terminal.id) {
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
            CharBufferString string = input.get_string(CharBufferString.class, src_text_start, src_text_end);

            reduction = production.reduce(i, sub_reductions, num_branches_explored, string);
        }

        return reduction;
    }
}
