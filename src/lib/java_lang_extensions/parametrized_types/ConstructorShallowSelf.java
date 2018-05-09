package lib.java_lang_extensions.parametrized_types;

public interface
ConstructorShallowSelf<T extends ConstructorShallowSelf<T>>
        extends ConstructorShallow<T, T>
{
    T new_instance(Object... args);

    T new_copy();
}
