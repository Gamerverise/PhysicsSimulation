package lib.java_lang_extensions.function_types;

public class Identity<T> implements FunctionR1<T, T> {

    @Override
    public T call(T t) {
        return t;
    }
}
