package edsel.lib.cfg_model;

import edsel.lib.io.SeekableBuffer;

import java.util.LinkedList;
import java.util.Stack;

import edsel.lib.cfg_model.LALR_ParseTable.*;

public class LALR_Grammar
        <TERMINAL_TYPE extends CFG_Terminal
                <ENUM_TERMINAL_ID,
                        TERMINAL_VALUE_TYPE,
                        ENUM_PRODUCTION_ID>,
                PRODUCTION_TYPE extends CFG_Production
                        <ENUM_TERMINAL_ID,
                                TERMINAL_VALUE_TYPE,
                                ENUM_PRODUCTION_ID>,
                ENUM_TERMINAL_ID extends Enum<ENUM_TERMINAL_ID>,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID extends Enum<ENUM_PRODUCTION_ID>>
        extends CFG
        <TERMINAL_TYPE,
                PRODUCTION_TYPE,
                ENUM_TERMINAL_ID,
                TERMINAL_VALUE_TYPE,
                ENUM_PRODUCTION_ID>
{
    public static class GrammarNotLALR extends Exception {};

    public LALR_ParseTable
            <ENUM_TERMINAL_ID,
                    TERMINAL_VALUE_TYPE,
                    ENUM_PRODUCTION_ID>
            parse_table;

    public LALR_Grammar(
            LinkedList<TERMINAL_TYPE> terminals,
            TERMINAL_TYPE eof,
            LinkedList<PRODUCTION_TYPE> productions,
            PRODUCTION_TYPE root_production)
            throws GrammarNotLALR
    {
        super(terminals, eof, productions, root_production);
        parse_table = build_parse_table();
    }

    public LALR_ParseTable
            <ENUM_TERMINAL_ID,
                    TERMINAL_VALUE_TYPE,
                    ENUM_PRODUCTION_ID>
    build_parse_table()
            throws GrammarNotLALR
    {
        return null;
    }

    public PRODUCTION_TYPE parse_lalr_naive(
            SeekableBuffer<TERMINAL_TYPE>                       input,
            Stack<
                    CFG_Symbol
                            <ENUM_TERMINAL_ID,
                                    TERMINAL_VALUE_TYPE,
                                    ENUM_PRODUCTION_ID>>        state_stack,
            PRODUCTION_TYPE                                     root_production)
    throws InvalidCFG_Input
    {
        while (true) {
            TERMINAL_TYPE terminal = input.next();

            if (terminal == eof) {
                if (state_stack.size() == 1)
                    return (PRODUCTION_TYPE) state_stack.pop();
                else
                    throw new InvalidCFG_Input();
            }

            ParseTableEntry transition = parse_table.get_transition(state_stack, terminal);

            if (transition instanceof LALR_ParseTable.SHIFT_TypeParseTableEntry)
                state_stack.push(terminal);
            else {
                REDUCE_TypeParseTableEntry reduce_transition = (LALR_ParseTable.REDUCE_TypeParseTableEntry) transition;

                PRODUCTION_TYPE parse_tree =
            }
        }
    }
}
