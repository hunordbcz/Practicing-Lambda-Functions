import controller.TaskController;
import model.MonitoredData;
import util.Constants;
import view.FileHandler;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        TaskController controller = new TaskController();
        FileHandler task1 = new FileHandler(Constants.getTask1FileName());
        FileHandler task2 = new FileHandler(Constants.getTask2FileName());
        FileHandler task3 = new FileHandler(Constants.getTask3FileName());
        FileHandler task4 = new FileHandler(Constants.getTask4FileName());
        FileHandler task5 = new FileHandler(Constants.getTask5FileName());
        FileHandler task6 = new FileHandler(Constants.getTask6FileName());
        List<MonitoredData> monitoredData = controller.parseMonitoredData(Constants.getActivitiesFileName());

        task1.write(monitoredData.toString());
        task2.write(controller.countDistinctDays(monitoredData).toString());
        controller.totalActivityOccurence(monitoredData).forEach(
                (activity, count) -> task3.writeln(activity + ": " + count)
        );
        controller.dailyActivityOccurence(monitoredData).forEach(
                (day, activty) -> {
                    task4.writeln("Day: " + day);
                    activty.forEach(
                            (activity, count) -> task4.writeln("\t" + activity + ": " + count)
                    );
                }
        );
        controller.computeDuration(monitoredData).forEach(
                (activity, duration) -> {
                    task5.writeln(activity + "\n\t"
                            + " Days:" + duration.toDays()
                            + " Hours:" + duration.toHoursPart()
                            + " Minutes:" + duration.toMinutesPart()
                            + " Seconds:" + duration.toSecondsPart());
                }
        );
        task6.write(controller.smallDurationActivities(monitoredData).toString());

        task1.close();
        task2.close();
        task3.close();
        task4.close();
        task5.close();
        task6.close();
    }
}
