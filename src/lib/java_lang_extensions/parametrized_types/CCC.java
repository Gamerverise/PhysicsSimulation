package lib.java_lang_extensions.parametrized_types;

public class CCC<T extends CCC<T>> extends AAA<T> {

    public CCC<? extends CCC<?>> self(T a){
        return a;
    }
}
