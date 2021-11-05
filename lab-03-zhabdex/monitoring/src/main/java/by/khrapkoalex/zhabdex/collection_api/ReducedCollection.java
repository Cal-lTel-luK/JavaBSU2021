package by.khrapkoalex.zhabdex.collection_api;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BinaryOperator;

public class ReducedCollection<T> implements FinalProcessedCollection<T, Optional<T>> {
    private final BinaryOperator<T> reducer;
    Optional<T> data = Optional.empty();

    public ReducedCollection(BinaryOperator<T> reducer) {
        this.reducer = reducer;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        data = elements.stream().map(element -> (T) element).reduce(reducer);
    }

    @Override
    public Optional<T> currentState() {
        return data;
    }
}