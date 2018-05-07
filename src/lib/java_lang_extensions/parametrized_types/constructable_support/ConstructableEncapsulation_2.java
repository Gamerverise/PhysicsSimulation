package lib.java_lang_extensions.parametrized_types.constructable_support;

public interface ConstructableEncapsulation_2
        <T,
                A extends ConstructableEncapsulation_2,
                B extends ConstructableEncapsulation_2<T, A, B>>
        extends
        ConstructableEncapsulation_1<ConstructableEncapsulation_2<T, A, B>>
{}
