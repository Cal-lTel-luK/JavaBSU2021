package by.zhabdex.monitoring;

import by.derovi.service_monitoring.visualizer.Table;
import by.zhabdex.collection_api.*;
import by.zhabdex.common.Service;
import by.zhabdex.monitoring_lib.ActiveMonitoring;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class MonitoringContainerEx {
    @ActiveMonitoring
    static FinalProcessedCollection<Service, Table> top2Nodes() {
        return new SortedCollection<>(Service::getNodesCount)
                .compose(new LimitedCollection<>(2))
                .compose(new TableViewCollection<>("top2 nodes", List.of(
                        TableViewCollection.ColumnProvider.of("Name", Service::getName),
                        TableViewCollection.ColumnProvider.of("Available nodes", Service::getNodesCount)
                )));
    }

    @ActiveMonitoring
    static FinalProcessedCollection<Service, Table> summaryPing() {
        return new GroupingCollection<>(Service::getDataCenter)
                .compose(new MappedCollection<>(
                                entry -> new AbstractMap.SimpleEntry<>(entry.getKey(),
                                        entry.getValue().stream().mapToLong(
                                        Service::getRequestsPerSecond).sum())))
                .compose(new TableViewCollection<>("Summary ping",
                        List.of(TableViewCollection.ColumnProvider.of("Name", Map.Entry::getKey),
                                TableViewCollection.ColumnProvider.of("Available nodes", Map.Entry::getValue))));
    }
}
