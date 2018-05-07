package lib.java_lang_extensions.parametrized_types;

public class NodeX
        <T_RAW_TYPE extends ConstructableRawType,
                T_PARAMETRIZED_TYPE extends T_RAW_TYPE>
    implements
        ConstructableRawType<T_RAW_TYPE, T_PARAMETRIZED_TYPE, NodeX<T_RAW_TYPE, T_PARAMETRIZED_TYPE>>
{
    NodeX<T_RAW_TYPE, T_PARAMETRIZED_TYPE> left;
    NodeX<T_RAW_TYPE, T_PARAMETRIZED_TYPE> right;
    NodeX<NodeX, NodeX<T_RAW_TYPE, T_PARAMETRIZED_TYPE>> righ;
}
