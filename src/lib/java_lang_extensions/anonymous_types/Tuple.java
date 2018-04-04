package lib.java_lang_extensions.anonymous_types;

public class Tuple extends TypedTuple<Object> {
   <T> T get(int i) {
        return (T) data[i];
    }
}
