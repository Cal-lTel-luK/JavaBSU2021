package by.zhabdex.monitoring_lib;

import by.derovi.service_monitoring.visualizer.Table;
import by.zhabdex.collection_api.FinalProcessedCollection;
import by.zhabdex.common.Service;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ContainerMonitoringScanner implements MonitoringScanner {
    @Override
    public Collection<Monitoring> scan(Reflections reflection) {
        return reflection.getTypesAnnotatedWith(MonitoringContainer.class)
                .stream()
                .map(Class::getDeclaredMethods)
                .flatMap(Arrays::stream)
                .filter(method -> method.isAnnotationPresent(ActiveMonitoring.class))
                .filter(method -> FinalProcessedCollection.class.isAssignableFrom(method.getReturnType()))
                .map(method -> {
                    method.setAccessible(true);
                    try {
                        return (FinalProcessedCollection<Service, Table>) method.invoke(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .map(collection -> new Monitoring() {

                    @Override
                        public void update(Collection<? extends Service> collections) {
                            collection.renew(collections);
                        }

                        @Override
                        public Table getStatistics() {
                            return collection.currentState();
                        }

                }).collect(Collectors.toList());
    }
}
