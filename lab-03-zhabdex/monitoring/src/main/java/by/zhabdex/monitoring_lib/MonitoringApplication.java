package by.zhabdex.monitoring_lib;

import by.derovi.service_monitoring.visualizer.Table;
import by.derovi.service_monitoring.visualizer.TerminalRenderer;
import by.zhabdex.common.Service;
import by.zhabdex.common.Tools;
import org.reflections.Reflections;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MonitoringApplication {
    String packageName;
    String serviceURL;
    List<Monitoring> monitorings = new ArrayList<>();
    List<MonitoringScanner> scanners = new ArrayList<>();
    public MonitoringApplication(String packageName, String serviceURL,
                                 List<MonitoringScanner> scanners,
                                 List<Monitoring> monitorings)
            throws IOException, URISyntaxException, InterruptedException {
        this.packageName = packageName;
        this.serviceURL = serviceURL;
        this.scanners = scanners;
        this.monitorings = monitorings;
    }
    private MonitoringApplication() {}

    public static MonitoringApplication.Builder builder() {
        var newMonitoringApplication = new MonitoringApplication();
        return newMonitoringApplication.new Builder();
    }

    private List<Service> fetchServices() throws IOException {
        return  Tools.JSON.readValue(new URL(serviceURL),
                Tools.JSON.getTypeFactory().constructCollectionType(List.class, Service.class));
    }
    public void start() throws InterruptedException, IOException {
        // TODO(Alex): add try catch
        TerminalRenderer renderer = TerminalRenderer.init(monitorings.size());
//        for (var i : monitorings) {
//            Reflections reflection = new Reflections(
//                    new ConfigurationBuilder().addScanners(
//                                    new MethodAnnotationsScanner(),
//                                    new TypeAnnotationsScanner())
//                            .forPackages(packageName));
//            monitorings.addAll(i.scan(reflection));
//        }
//
        while (true) {
            try {
                List<Service> services = fetchServices();
                var tables = new ArrayList<Table>();
                for (var i : monitorings) {
                    i.update(services);
                    tables.add(i.getStatistics());
                }
                renderer.render(tables);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public class Builder {
        private Builder() {}

        public MonitoringApplication.Builder packageName(String packageName) {
            MonitoringApplication.this.packageName = packageName;
            return this;
        }

        public MonitoringApplication.Builder serviceURL(String serviceURL) {
            MonitoringApplication.this.serviceURL = serviceURL;
            return this;
        }

        public MonitoringApplication.Builder addScanner(MonitoringScanner scanner) {
            MonitoringApplication.this.scanners.add(scanner);
            return this;
        }

        public MonitoringApplication start() throws IOException, URISyntaxException, InterruptedException {
            scanners.forEach(scanner ->
                    monitorings.addAll(
                            scanner.scan(new Reflections(packageName))));
            return new MonitoringApplication(packageName, serviceURL, scanners, monitorings);
        }
    }

}
