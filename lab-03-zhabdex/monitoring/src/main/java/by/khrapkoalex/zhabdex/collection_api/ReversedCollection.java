package by.khrapkoalex.zhabdex.collection_api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ReversedCollection<T> implements ProcessedCollection<T, T> {
    Collection<? extends T> data = null;

    @Override
    public void renew(Collection<? extends T> elements) {
        var copyCollection = new ArrayList<>(elements);
        Collections.reverse(copyCollection);
        data = copyCollection;
    }

    @Override
    public Collection<? extends T> currentState() {
        return data;
    }
}
