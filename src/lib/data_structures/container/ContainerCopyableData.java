package lib.data_structures.container;

import lib.java_lang_extensions.parametrized_types.Constructor;
import lib.tokens.enums.CopyType;

import java.util.Collection;
import java.util.Iterator;

public abstract class
ContainerCopyableData
        <DT extends Constructor<DT>,
                UDS extends Collection<DT>,
                DS extends ContainerCopyableData<DT, UDS, DS>>
        extends ContainerAdapter<DT, UDS, DS>
{}
