import by.zhabdex.collection_api.FilteredCollection;
import by.zhabdex.collection_api.LimitedCollection;
import by.zhabdex.collection_api.SortedCollection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionsTest {
    @Test
    void FilteredCollectionTest() {
        var elements = List.of(1, 2, 3, 4, -5, -6, -7);
        var filteredCollection = new FilteredCollection<Integer>(x -> x % 2 == 0);
        filteredCollection.renew(elements);
        assertEquals(filteredCollection.currentState(), elements.stream().filter(x -> x % 2 == 0)
                .collect(Collectors.toList()));
    }

    @Test
    void LimitedCollectionTest() {
        var elements = List.of(1, 2, 3, 4, -5, -6, -7);
        var limitedCollection = new LimitedCollection<Integer>(3);
        limitedCollection.renew(elements);
        assertEquals(limitedCollection.currentState(),
                elements.stream().limit(3).collect(Collectors.toList()));
    }

    @Test
    void SortedCollectionTest() {
        var elements = new ArrayList<>(Arrays.asList(-3, 11, -7, 9, 2, 7, 77, 13));
        var sortedCollection = new SortedCollection<>(Integer::compare);
        sortedCollection.renew(elements);
        Collections.sort(elements);
        assertEquals(sortedCollection.currentState(), elements);
    }
}