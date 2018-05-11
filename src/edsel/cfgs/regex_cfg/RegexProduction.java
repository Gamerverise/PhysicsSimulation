package edsel.cfgs.regex_cfg;

import edsel.lib.cfg_model.CFG_Symbol;
import edsel.lib.cfg_model.RCFG_Production;

import java.util.LinkedList;

public class
RegexProduction
        extends RCFG_Production<RegexProductionID, LinkedList<Character>>
{
    public RegexProduction(RegexProductionID id, CFG_Symbol[]... rhs)
    {
        super(id, rhs);
    }

    public LinkedList<Character> reduce(LinkedList<Character>... sub_reductions)
    {
        if (sub_reductions.length == 0)
            return null;

        LinkedList<Character> reduction = new LinkedList<>();

        for (LinkedList<Character> chars : sub_reductions)
            reduction.addAll(chars);


        return reduction;
    }
}
