package by.zhabdex.collection_api;

import by.derovi.service_monitoring.visualizer.Table;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TableViewCollection<T> implements FinalProcessedCollection <T, Table> {
    public static class ColumnProvider<T> {
        private final String title;
        private final Function<T, String> fieldExtractor;

        private ColumnProvider(String title, Function<T, String> fieldExtractor) {
            this.title = title;
            this.fieldExtractor = fieldExtractor;
        }

        private String getTitle() {
            return title;
        }

        private String getField(T value) {
            return fieldExtractor.apply(value);
        }

        public static <T, U> ColumnProvider<T> of(String title, Function<T, U> fieldExtractor) {
            return new ColumnProvider<T>(title, value -> fieldExtractor.apply(value).toString());
        }
    }

    private final String tableName;
    private Table table;
    private final List<ColumnProvider<T>> columnProviders;

    public TableViewCollection(String tableName, List<ColumnProvider<T>> columnProviders) {
        this.tableName = tableName;
        this.columnProviders = columnProviders;
//        table = new Table(tableName).addRow(columnProviders.stream()
//                                                           .map(ColumnProvider::getTitle)
//                                                           .collect(Collectors.toList()));
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        table = new Table(tableName).addRow(columnProviders.stream()
                                                           .map(ColumnProvider::getTitle)
                                                           .collect(Collectors.toList()));
        elements.stream()
                .map(element -> columnProviders.stream()
                                               .map(value -> value.getField(element))
                                               .collect(Collectors.toList()))
                .forEach(table::addRow);
    }

    @Override
    public Table currentState() {
        return table;
    }
}
