import Util.Constants;

public class Main {

    public static void main(String[] args) {
        TaskController controller = new TaskController();
        controller.parseMonitoredData(Constants.getActivitiesFileName());
    }
}
