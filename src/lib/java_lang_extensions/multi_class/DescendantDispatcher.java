package lib.java_lang_extensions.multi_class;

import java.util.HashMap;

import static java_lang_extensions.flow_control.FlowControl.*;

public class DescendantDispatcher {

    HashMap<Class, _Ancestor> dispatch_table = new HashMap<>();

    @SafeVarargs
    public DescendantDispatcher(_Ancestor... _ancestors) {
        repeat (_ancestors.length, (int i) ->
        {
            dispatch_table.put(_ancestors[i].getClass(), _ancestors[i]);
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends _Ancestor> T dispatch() {
        return (T) dispatch_table.get(T.get_class());
    }
}
