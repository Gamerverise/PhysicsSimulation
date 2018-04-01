package lib_2.java_lang_extensions.unnamed_types;

public class Tuple<T> {
    T[] elems;

    Tuple(int n) {
        elems = (T[]) new Object[n];
    }

    <K extends T> K get(int i) {
        return (K) elems[i];
    }

    void put(int i, T elem) {
        elems[i] = elem;
    }
}
