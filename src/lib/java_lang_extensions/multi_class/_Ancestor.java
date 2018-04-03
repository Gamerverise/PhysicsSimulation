package lib_2.java_lang_extensions.multi_class;

import lib.debug.MethodNameHack;

import static lib.debug.Debug.assert_msg;

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
        assert false : assert_msg(
                _Ancestor.class,
                new MethodNameHack(){}.method_name(),
                "Each sub-class of _Ancestor must provide its own version of get_class().");
        return null;
    }
}
