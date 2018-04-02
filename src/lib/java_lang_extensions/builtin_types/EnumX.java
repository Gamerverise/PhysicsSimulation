package lib.java_lang_extensions.builtin_types;

public class EnumX<E extends Class<Enum>> {

    Class enum_class;

    EnumX(E enum_class) {
        this.enum_class = enum_class;
    }

    int get_size() {
        return enum_class.getEnumConstants().length;
    }

    enum Gg {AA, BB}

    static Class<Gg> clazz = Gg.class;

    void hmm() {
        Gg q = Gg.AA;
        Gg v = clazz.AA;
    }
}