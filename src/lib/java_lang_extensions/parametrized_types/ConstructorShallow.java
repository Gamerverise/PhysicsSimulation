package lib.java_lang_extensions.parametrized_types;

public interface ConstructorShallow
    <T extends Constructor<T>>
    extends ConstructorShallowDataStructure<T, T>
{}
