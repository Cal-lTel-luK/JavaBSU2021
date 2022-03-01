package by.zhabdex.collection_api;

import java.util.*;
import java.util.function.Function;

public class GroupingCollection<T, K>
        implements ProcessedCollection<T, Map.Entry<? extends K, ? extends List<? extends T>>> {
    private final Function<? super T, ? extends K> classifier;
    HashMap<K, List<T>> keysElements = new HashMap<>();

    public GroupingCollection(Function<? super T, ? extends K> classifier) {
        this.classifier = classifier;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        keysElements.clear();
        elements.forEach(value -> {
            var key = classifier.apply(value);
            keysElements.putIfAbsent(key, new ArrayList<>());
            keysElements.get(key).add(value);
        });
    }

    @Override
    public Collection<? extends Map.Entry<? extends K, ? extends List<? extends T>>> currentState() {
        return keysElements.entrySet();
    }
}
