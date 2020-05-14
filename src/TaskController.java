import model.MonitoredData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

public class TaskController {

    List<MonitoredData> monitoredData;

    public TaskController() {
        monitoredData = new LinkedList<>();
    }

    public void parseMonitoredData(String input) {
        try (Stream<String> stream = Files.lines(Paths.get(input))) {
            stream.forEach(val -> monitoredData.add(new MonitoredData(val.split("\t\t"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Integer task2 = this.countDistinctDays();
        Map<String, Integer> task3 = this.countActivityOccurence();
        Map<Integer, Map<String, Integer>> task4 = this.task4();
        Map<String, Integer> task5 = this.task5();
        List<String> task6 = this.task6();
    }

    public Integer countDistinctDays() {
        return monitoredData.stream().collect(
                Collectors.groupingBy(
                        val -> val.getStart_time().toInstant().truncatedTo(DAYS)
                )
        ).size();
    }

    public Map<String, Integer> countActivityOccurence() {
        return monitoredData.stream().collect(
                Collectors.groupingBy(
                        MonitoredData::getActivity,
                        Collectors.summingInt(val -> 1)
                )
        );
    }

    public Map<Integer, Map<String, Integer>> task4() {
        return monitoredData.stream().collect(
                Collectors.groupingBy(
                        val -> val.getStart_time().getDate(),
                        Collectors.groupingBy(
                                MonitoredData::getActivity,
                                Collectors.summingInt(val -> 1)
                        )
                )
        );
    }

    public Map<String, Integer> task5() {
        return monitoredData.stream()
                .collect(
                        Collectors.groupingBy(
                                MonitoredData::getActivity,
                                Collectors.summingInt(MonitoredData::getLength)
                        )
                );
    }

    public List<String> task6() {
        return null;
    }
}
