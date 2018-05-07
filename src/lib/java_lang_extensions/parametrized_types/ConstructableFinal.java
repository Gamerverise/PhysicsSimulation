package lib.java_lang_extensions.parametrized_types;

public class ConstructableFinal {

    public interface Precondition_RawConstructable
            <A extends Precondition_RawConstructable> {}

    public interface Precondition_ParametrizedConstructable
            <A extends Precondition_ParametrizedConstructable<A>> {}

    public interface ___Constructable___
            <A extends Precondition_RawConstructable & Precondition_ParametrizedConstructable<A>,
                    B extends A>
            extends Precondition_RawConstructable, Precondition_ParametrizedConstructable<A> {}

    public interface ConstructableEncapsulation_1<A>
            extends
            ___Constructable___<ConstructableEncapsulation_1<A>, ConstructableEncapsulation_1<A>> {}

    public interface ConstructableEncapsulation_2
            <T,
                    A extends ConstructableEncapsulation_2,
                    B extends ConstructableEncapsulation_2<T, A, B>>
            extends
            ConstructableEncapsulation_1<ConstructableEncapsulation_2<T, A, B>>
    {}

    public interface Constructable          // aka ConstructableEncapsulation_3
            <T,
                    DS extends ConstructableEncapsulation_2<T, DS, Constructable<T, DS>>>
            extends
            ConstructableEncapsulation_2<T, DS, Constructable<T, DS>> {}

    public class Link<T>
            implements Constructable<T, Link<T>> {}
}
