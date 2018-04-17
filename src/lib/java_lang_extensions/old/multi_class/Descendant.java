package lib.java_lang_extensions.multi_class;

public interface Descendant {

    <T extends _Ancestor> T dispatch();
}
