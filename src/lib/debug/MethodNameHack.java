package lib.debug;

public class MethodNameHack {
    public String method_name() {
        return getClass().getEnclosingMethod().getName();
    }
}
