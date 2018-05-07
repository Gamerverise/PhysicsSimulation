package lib.java_lang_extensions.parametrized_types;

public class ConstructableFinal {

    public interface ConstructablePrecondition
            <A extends ConstructablePrecondition> {}

    public interface ParametrizedConstructable
            <A extends ParametrizedConstructable<A>> {}

    public interface ConstructableBasis
            <A extends ConstructablePrecondition & ParametrizedConstructable<A>,
                    B extends A>
            extends ConstructablePrecondition, ParametrizedConstructable<A> {}

    public interface ConstructableWrapper_1<A>
            extends
            ConstructableBasis<ConstructableWrapper_1<A>, ConstructableWrapper_1<A>> {}

    public interface ConstructableWrapper_2
            <T,
                    A extends ConstructableWrapper_2,
                    B extends ConstructableWrapper_2<T, A, B>>
            extends
            ConstructableWrapper_1<ConstructableWrapper_2<T, A, B>>
    {}

    public interface Constructable
            <T,
                    DS extends ConstructableWrapper_2<T, DS, Constructable<T, DS>>>
            extends
            ConstructableWrapper_2<T, DS, Constructable<T, DS>> {}

    public class Link<T>
            implements Constructable<T, Link<T>> {}
}
