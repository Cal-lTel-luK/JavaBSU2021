package by.zhabdex.monitoring_lib;

import org.reflections.Reflections;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassMonitoringScanner implements MonitoringScanner {
    @Override
    public Collection<Monitoring> scan(Reflections reflection) {
        Set<Class<?>> monitorAnnotated =
                reflection.getTypesAnnotatedWith(ActiveMonitoring.class);
        Set<Class<? extends Monitoring>> monitors =
                reflection.getSubTypesOf(Monitoring.class);
        // merge/combine the classes that implement a certain interface and have the required annotation
        monitorAnnotated.retainAll(monitors);
        var result = monitorAnnotated.stream().toList();
//        monitors.retainAll(monitorAnnotated);
//        var result = monitors.stream().toList();
        return result.stream()
                .map(monitoring -> {
                    try {
                        return (Monitoring) monitoring.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }
}
