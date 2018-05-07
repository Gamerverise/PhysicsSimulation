package lib.java_lang_extensions.parametrized_types;

public class Consti {

    public interface ConstPre<A extends ConstPre> {}

    public interface ParamConst<A extends ParamConst<A>> {}

    public interface Const<A extends ConstPre & ParamConst<A>, B extends A> extends ConstPre, ParamConst<A> {}

    public interface SuperConst<A> extends Const<SuperConst<A>, SuperConst<A>> {}

    public interface PreLink<T, A extends PreLink, B extends PreLink<T, A, B>>
            extends
            SuperConst<PreLink<T, A, B>>
    {}

    public class Link<T> implements PreLink<T, Link, Link<T>> {}


    public interface PreF<T, A extends PreF, B extends PreF<T, A, B>>
            extends
            SuperConst<PreF<T, A, B>>
    {}

    public interface F<T, DS extends PreF<T, DS, F<T, DS>>> extends PreF<T, DS, F<T, DS>> {}

    public class LinkF<T> implements F<T, LinkF<T>> {}
}
