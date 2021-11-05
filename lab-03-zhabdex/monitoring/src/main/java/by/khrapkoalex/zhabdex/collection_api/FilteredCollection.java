package by.khrapkoalex.zhabdex.collection_api;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilteredCollection<T> implements ProcessedCollection <T, T> {
    private final Predicate<T> predicate;
    Collection<? extends T> data = null;

    public FilteredCollection(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        data = elements.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends T> currentState() {
        return data;
    }
}
