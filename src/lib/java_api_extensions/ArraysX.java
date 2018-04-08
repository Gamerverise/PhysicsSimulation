package lib.java_api_extensions;

public class ArraysX {

    @SafeVarargs
    public static <T> T[] A(T... ts) {
        return ts;
    }
}
