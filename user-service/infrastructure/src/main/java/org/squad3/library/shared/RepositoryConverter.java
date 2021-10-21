package org.squad3.library.shared;

import java.io.Serializable;
import java.util.Optional;

public interface RepositoryConverter<T extends Serializable, P extends Serializable> {

    default T mapToTable(final P persistenceObject){
        throw new UnsupportedOperationException();
    }

    default P mapToEntity(final T tableObject){
        throw new UnsupportedOperationException();
    }
}
