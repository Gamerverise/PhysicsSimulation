package lib.java_lang_extensions.parametrized_types;

public class BBB<T extends BBB<T>> extends AAA<T> {

    public BBB<? extends BBB<?>> self(T a) {
        return a;
    }
}
