package lib.java_lang_extensions.parametrized_types;

public interface ConstructorShallowDataStructure
    <T extends Constructor<T>,
            CONSTRUCTOR_SHALLOW_TYPE extends ConstructorShallowDataStructure<T, CONSTRUCTOR_SHALLOW_TYPE>>
{
    CONSTRUCTOR_SHALLOW_TYPE self();

    CONSTRUCTOR_SHALLOW_TYPE new_instance(Object... args);

    CONSTRUCTOR_SHALLOW_TYPE new_copy();
}
