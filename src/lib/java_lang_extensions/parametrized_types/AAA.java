package lib.java_lang_extensions.parametrized_types;

public class AAA<T extends AAA<T>> {

    public AAA<? extends AAA<?>> self() {
        return this;
    }
}
