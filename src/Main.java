import Util.Constants;
import model.MonitoredData;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        TaskController controller = new TaskController();
        List<MonitoredData> monitoredData = controller.parseMonitoredData(Constants.getActivitiesFileName());
        System.out.println(monitoredData.toString());
        System.out.println(controller.countDistinctDays(monitoredData).toString());
        System.out.println(controller.totalActivityOccurence(monitoredData).toString());
        System.out.println(controller.dailyActivityOccurence(monitoredData).toString());
        System.out.println(controller.computeDuration(monitoredData).toString());
        System.out.println(controller.smallDurationActivities(monitoredData).toString());
    }
}
