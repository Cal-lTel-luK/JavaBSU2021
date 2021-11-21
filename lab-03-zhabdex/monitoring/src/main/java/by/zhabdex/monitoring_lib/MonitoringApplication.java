package by.zhabdex.monitoring_lib;

import by.derovi.service_monitoring.visualizer.Table;
import by.derovi.service_monitoring.visualizer.TerminalRenderer;
import by.zhabdex.common.Service;
import by.zhabdex.common.Tools;
import com.fasterxml.jackson.core.type.TypeReference;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MonitoringApplication {
    String packageName;
    String serviceURL;
    List<Monitoring> monitorings = new ArrayList<>();
    List<MonitoringScanner> scanners = new ArrayList<>();
    TerminalRenderer terminalRenderer;
//    public MonitoringApplication(String packageName, String serviceURL,
//                                 List<MonitoringScanner> scanners,
//                                 List<Monitoring> monitorings)
//            throws IOException, URISyntaxException, InterruptedException {
//        this.packageName = packageName;
//        this.serviceURL = serviceURL;
//        this.scanners = scanners;
//        this.monitorings = monitorings;
//    }

    private MonitoringApplication() {}

    public static MonitoringApplication.Builder builder() {
        var newMonitoringApplication = new MonitoringApplication();
        return newMonitoringApplication.new Builder();
    }

    private List<Service> fetchServices() throws IOException, URISyntaxException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(serviceURL))
                .GET().build();
        HttpResponse<String> httpResponse = HttpClient.newBuilder()
                .build()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return  Tools.JSON.readValue(httpResponse.body(), new TypeReference<>() {});
    }

    public void start() throws InterruptedException, IOException {
        try {
            TerminalRenderer terminalRenderer = TerminalRenderer.init(monitorings.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (var i : scanners) {
            Reflections reflection = new Reflections(
                    new ConfigurationBuilder().addScanners(new MethodAnnotationsScanner(), new TypeAnnotationsScanner())
                    .forPackages(packageName));
            monitorings.addAll(i.scan(reflection));
        }

        while (true) {
            try {
                List<Service> services = fetchServices();
                var tables = new ArrayList<Table>();
                for (var i : monitorings) {
                    i.update(services);
                    tables.add(i.getStatistics());
                }
                terminalRenderer.render(tables);
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

        public MonitoringApplication build() {
            return MonitoringApplication.this;
        }
    }

}
