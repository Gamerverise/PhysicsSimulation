package lib_2.java_lang_extensions.multi_class;

import java.util.HashMap;

public interface Descendant {

    <T extends _Ancestor> T dispatch();
}
