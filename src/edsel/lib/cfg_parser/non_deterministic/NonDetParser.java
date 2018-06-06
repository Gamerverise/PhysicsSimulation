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
import edsel.lib.cfg_parser.parsing_restriction.old.TerminalRestriction;
import edsel.lib.io.CharBuffer.CharBufferString;
import lib.java_lang_extensions.mutable.MutableInt;

import static edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction.RestrictionMode.EXACT_MODE;
import static edsel.lib.cfg_parser.parsing_restriction.ProductionRestriction.RestrictionMode.PREFIX_MODE;

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
            MutableInt num_branches_explored
    )
            throws AmbiguousParserInput, InputNotAccepted
    {
        Reduction<ENUM_PRODUCTION_ID> reduction = null;

        for (int i = 0; i < production.rhs.length; i++) {

            input.save();

            Reduction<ENUM_PRODUCTION_ID>
                    tmp_reduction
                    =
                    parse_branch_recursive(
                            production,
                            i,
                            num_branches_explored);

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
            else if (cur_symbol instanceof GateRestriction)
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

                            if (production_restriction.mode == PREFIX_MODE)
                                input.inc_nesting_level();

                            if (cur_symbol instanceof BranchRestriction) {

                                BranchRestriction<ENUM_PRODUCTION_ID> branch_restriction
                                        = (BranchRestriction<ENUM_PRODUCTION_ID>) cur_symbol;

                                sub_reductions[j]
                                        =
                                        parse_branch_recursive(
                                                branch_restriction.production,
                                                branch_restriction.branch_num,
                                                num_branches_explored);
                            } else
                                sub_reductions[j]
                                        =
                                        parse_recursive(
                                                production_restriction.production,
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
                    } else
                        parse_recursive(cur_expected_production, num_branches_explored);

                } else if (cur_expected_symbol instanceof CFG_Terminal) {

                    if (cur_symbol instanceof Token) {

                        Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE> token
                                = (Token<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE>) cur_symbol;

                        CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>
                                cur_expected_terminal
                                =
                                (CFG_Terminal<ENUM_TERMINAL_ID, TOKEN_VALUE_TYPE, SYMBOL_BUFFER_TYPE>)
                                        cur_expected_symbol;

                        if (token.id == cur_expected_terminal.id) {

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

//    public static
//    class PrefixInfo
//    {
//        public int num_prefixes_in_progress;
//        public int num_prefixes_to_retire;
//
//        public PrefixInfo(int num_prefix_in_progress, int num_prefixes_to_retire) {
//            this.num_prefixes_in_progress = num_prefix_in_progress;
//            this.num_prefixes_to_retire = num_prefixes_to_retire;
//        }
//
//        public PrefixInfo(PrefixInfo info) {
//            num_prefixes_in_progress = info.num_prefixes_in_progress;
//            num_prefixes_to_retire = info.num_prefixes_to_retire;
//        }
//    }
//
//    public
//    class ParsingState
//    {
//        public Stack<PrefixInfo> prefix_info_stack = new Stack<>();
//
//        public void save() {
//            input.save();
//
//            PrefixInfo top = prefix_info_stack.peek();
//            prefix_info_stack.push(new PrefixInfo(top));
//        }
//
//        public void restore() {
//            input.restore();
//            prefix_info_stack.pop();
//        }
//
//        public int get_in_progress() {
//            return prefix_info_stack.peek().num_prefixes_in_progress;
//        }
//
//        public void inc_prefixes_in_progress() {
//            prefix_info_stack.peek().num_prefixes_in_progress++;
//        }
//
//        public void inc_prefixes_to_retire() {
//            prefix_info_stack.peek().num_prefixes_to_retire++;
//        }
//
//        public boolean retire_prefix() {
//            PrefixInfo top = prefix_info_stack.peek();
//
//            if (top.num_prefixes_in_progress > 0) {
//                top.num_prefixes_in_progress--;
//                return true;
//            }
//
//            return false;
//        }
//    }
}
