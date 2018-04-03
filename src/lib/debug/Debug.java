package lib.debug;

public class Debug {
    public static String assert_msg(Class clazz, String method_name, String msg) {
        return clazz.getName() + "." + method_name + ": Failed assertion: " + msg;
    }

}
