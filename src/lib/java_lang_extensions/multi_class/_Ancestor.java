package lib.java_lang_extensions.multi_class;

public class _Ancestor<T> {

    public T descendant;

    public _Ancestor(T descendant) {
        this.descendant = descendant;
    }

    /******************************************************/

    // Instead of requiring each sub-class of _Ancestor to provide its own version of get_clas(), we
    // could write a class loader to do a bit of initialization each time a sub-class of _Ancestor is
    // loaded.

    static public Class get_class() {
        assert false : "_Ancestor: get_class: Each sub-class of _Ancestor must provide its own version of get_class().";
        return null;
    }
}
