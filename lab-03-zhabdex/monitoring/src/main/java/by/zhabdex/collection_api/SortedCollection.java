package by.zhabdex.collection_api;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortedCollection<T> implements ProcessedCollection<T, T> {
    private final Comparator<T> comparator;
    Collection<? extends T> data;

    public SortedCollection(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public <V extends Comparable<? super V>> SortedCollection(
            Function<T, V> keyExtractor, boolean reversed) {
        comparator = reversed ? Comparator.comparing(keyExtractor).reversed() : Comparator.comparing(keyExtractor);
    }

    public <V extends Comparable<? super V>> SortedCollection(Function<T, V> keyExtractor) {
        comparator = Comparator.comparing(keyExtractor);
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        data = elements.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends T> currentState() {
        return data;
    }
}
