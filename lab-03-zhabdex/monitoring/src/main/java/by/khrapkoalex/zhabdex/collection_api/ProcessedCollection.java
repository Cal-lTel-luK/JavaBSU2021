package by.khrapkoalex.zhabdex.collection_api;

import java.util.Collection;

public interface ProcessedCollection <T, E> extends FinalProcessedCollection<T, Collection <? extends E>> {
    default <U> FinalProcessedCollection<T, U> compose(
            FinalProcessedCollection<E, U> collection) {
        return new FinalComposedCollection<T, E, U>(this, collection);
    }

    default <U> ProcessedCollection<T, U> compose(
            ProcessedCollection<E, U> collection) {
        return new ComposedCollection<T, E, U>(this, collection);
    }
}

class FinalComposedCollection<T, E, U>
        implements FinalProcessedCollection<T, U> {
    ProcessedCollection<T, E> collection1;
    FinalProcessedCollection<E, U> collection2;

    public FinalComposedCollection(ProcessedCollection<T, E> collection1,
                                   FinalProcessedCollection<E, U> collection2) {
        this.collection1 = collection1;
        this.collection2 = collection2;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        collection1.renew(elements);
        collection2.renew(collection1.currentState());
    }

    @Override
    public U currentState() {
        return collection2.currentState();
    }
}

class ComposedCollection<T, E, U> implements ProcessedCollection<T, U> {
    ProcessedCollection<T, E> collection1;
    ProcessedCollection<E, U> collection2;

    public ComposedCollection(ProcessedCollection<T, E> collection1,
                              ProcessedCollection<E, U> collection2) {
        this.collection1 = collection1;
        this.collection2 = collection2;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        collection1.renew(elements);
        collection2.renew(collection1.currentState());
    }

    @Override
    public Collection<? extends U> currentState() {
        return collection2.currentState();
    }
}
