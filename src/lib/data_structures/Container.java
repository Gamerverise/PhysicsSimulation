package lib.data_structures;

import java.util.Iterator;

import lib.tokens.enums.CopyType;

import static lib.tokens.enums.CopyType.*;

public abstract class Container<T extends Copyable> {
    Object store;

    public Container(Copyable<T> store, CopyType copy_type) {
        switch (copy_type) {
            case COPY_SHALLOW:
                this.store = store;
                break;
            case COPY_CONTAINER_ONLY:
                this.store = store.copy(COPY_CONTAINER_ONLY);
                Iterator<T> iter = store.iterator();
                while (iter.hasNext())
                    this.store.add(iter.next());
                break;
            case COPY_DEEP:
                this.store = store.copy(COPY_DEEP);
                break;
        }
    }

    public abstract Container<T> copy(CopyType copy_type);

    public abstract void add(T item);
}
