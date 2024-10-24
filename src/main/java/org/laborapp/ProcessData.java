package org.laborapp;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessData {

    public Map<String, Long> calculateTotalRuntime(String jsonResponse) {
        Gson gson = new Gson();

        ResponseData responseData = gson.fromJson(jsonResponse, ResponseData.class);
        Map<String, Long> customerRuntime = new HashMap<>();
        Map<String, Long> workloadStartTimes = new HashMap<>();

        for (Event event : responseData.getEvents()) {
            String customerId = event.getCustomerId();
            String workloadId = event.getWorkloadId();
            long timestamp = event.getTimestamp();
            String eventType = event.getEventType();

            if (eventType.equals("start")) {
                workloadStartTimes.put(workloadId, timestamp);
            }

            else if (eventType.equals("stop")) {
                Long startTime = workloadStartTimes.get(workloadId);
                if (startTime != null) {
                    long runtime = timestamp - startTime;
                    customerRuntime.put(customerId, customerRuntime.getOrDefault(customerId, 0L) + runtime);
                    workloadStartTimes.remove(workloadId);
                }
            }
        }
        return customerRuntime;
    }
}

class ResponseData {
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}

class Event {
    private String customerId;
    private String workloadId;
    private long timestamp;
    private String eventType;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getWorkloadId() {
        return workloadId;
    }

    public void setWorkloadId(String workloadId) {
        this.workloadId = workloadId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}