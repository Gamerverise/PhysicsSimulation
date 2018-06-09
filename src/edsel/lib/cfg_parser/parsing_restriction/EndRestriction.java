package edsel.lib.cfg_parser.parsing_restriction;

public class EndRestriction implements ParsingRestriction
{
    public EndRestriction() {}

    public String sprint() {
        return sprint(0);
    }

    public String sprint(int indent) {
        return
                new StringBuilder(spaces(indent))
                        .append(id.toString())
                        .append(": ")
                        .append(src_string.get_string())
                        .toString();
    }
}
