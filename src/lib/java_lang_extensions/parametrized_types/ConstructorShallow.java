package lib.java_lang_extensions.parametrized_types;

public interface ConstructorShallow
    <T extends ConstructorShallow<T, CONSTRUCTOR_SHALLOW_TYPE>,
            CONSTRUCTOR_SHALLOW_TYPE extends ConstructorShallow<T, CONSTRUCTOR_SHALLOW_TYPE>>
{
    CONSTRUCTOR_SHALLOW_TYPE self();

    CONSTRUCTOR_SHALLOW_TYPE new_instance(Object... args);

    CONSTRUCTOR_SHALLOW_TYPE new_copy();
}
