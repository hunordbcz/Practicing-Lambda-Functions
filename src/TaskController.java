import model.MonitoredData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

public class TaskController {
    public TaskController() {

    }

    public List<MonitoredData> parseMonitoredData(String input) {
        List<MonitoredData> monitoredData = new LinkedList<>();
        try (Stream<String> stream = Files.lines(Paths.get(input))) {
            stream.forEach(val -> monitoredData.add(new MonitoredData(val.split("\t\t"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return monitoredData;
    }

    public Integer countDistinctDays(List<MonitoredData> monitoredData) {
        return monitoredData.stream().collect(Collectors.groupingBy(
                val -> val.getStart_time().toInstant().truncatedTo(DAYS)
        )).size();
    }

    public Map<String, Integer> totalActivityOccurence(List<MonitoredData> monitoredData) {
        return monitoredData.stream().collect(Collectors.groupingBy(
                MonitoredData::getActivity,
                Collectors.summingInt(val -> 1)
        ));
    }

    public Map<Integer, Map<String, Integer>> dailyActivityOccurence(List<MonitoredData> monitoredData) {
        return monitoredData.stream().collect(Collectors.groupingBy(
                val -> val.getStart_time().toInstant().truncatedTo(DAYS),
                Collectors.groupingBy(
                        MonitoredData::getActivity,
                        Collectors.summingInt(val -> 1)
                )
        )).entrySet().stream().collect(Collectors.toMap(
                e -> Date.from(e.getKey()).getDate(),
                Map.Entry::getValue
        ));
    }

    public Map<String, Duration> computeDuration(List<MonitoredData> monitoredData) {
        return monitoredData.stream().collect(Collectors.groupingBy(
                MonitoredData::getActivity,
                Collectors.summingInt(MonitoredData::getLengthInSeconds)
        )).entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> Duration.ofSeconds(e.getValue())
        ));
    }

    private Map<String, Double> maxFiveMinActivities(List<MonitoredData> monitoredData) {
        return monitoredData.stream().collect(Collectors.groupingBy(
                MonitoredData::getActivity,
                Collectors.summingDouble(var -> var.getLengthInSeconds() < (5 * 60) ? 1 : 0)
        ));
    }

    public List<String> smallDurationActivities(List<MonitoredData> monitoredData) {
        Map<String, Double> fiveMinActivities = maxFiveMinActivities(monitoredData);
        Map<String, Integer> activityOccurrence = totalActivityOccurence(monitoredData);

        return monitoredData.stream().map(MonitoredData::getActivity).distinct().collect(
                Collectors.filtering(
                        val -> fiveMinActivities.get(val) / activityOccurrence.get(val) > 0.9,
                        Collectors.toList()
                ));
    }
}
