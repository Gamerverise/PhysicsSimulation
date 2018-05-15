package lib.data_structures.container;

import lib.java_lang_extensions.parametrized_types.Instantiator;

import java.util.Collection;

public abstract class
ContainerCopyableData
        <DT extends Instantiator<DT>,
                UDS extends Collection<DT>,
                DS extends ContainerCopyableData<DT, UDS, DS>>
        extends ContainerAdapter<DT, UDS, DS>
{}
