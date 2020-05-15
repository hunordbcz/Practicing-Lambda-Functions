package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitoredData {
    private Date start_time;
    private Date end_time;
    private String activity;

    public MonitoredData() {

    }

    public MonitoredData(String[] values) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            start_time = parser.parse(values[0]);
            end_time = parser.parse(values[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        activity = values[2].replaceAll("\t", "");
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Integer getLengthInSeconds() {
        long diff = end_time.getTime() - start_time.getTime();
        return (int) (diff / 1000);
    }

    @Override
    public String toString() {
        return activity + ": " + start_time + " - " + end_time + "\n";
    }
}
