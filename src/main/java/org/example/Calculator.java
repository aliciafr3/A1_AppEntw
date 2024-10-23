package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/api")  // Base-URL hinzugefügt
public class Calculator {
    public static void main(String[] args) {
        SpringApplication.run(Calculator.class, args);
    }

    @GetMapping("/aggregate")
    public Map<String, Long> aggregate() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://assessment_service:8080/v1/dataset";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> events = (List<Map<String, Object>>) response.get("events");

        Map<String, Long> result = new HashMap<>();
        Map<String, Long> startTimes = new HashMap<>();

        for (Map<String, Object> event : events) {
            String customerId = (String) event.get("customerId");
            long timestamp = (long) event.get("timestamp");
            String eventType = (String) event.get("eventType");

            if ("start".equals(eventType)) {
                startTimes.put(customerId, timestamp);
            } else if ("stop".equals(eventType)) {
                Long startTime = startTimes.get(customerId);
                if (startTime != null) {
                    long duration = timestamp - startTime;
                    result.put(customerId, result.getOrDefault(customerId, 0L) + duration);
                    startTimes.remove(customerId);
                }
            }
        }

        Map<String, Object> resultPayload = new HashMap<>();
        resultPayload.put("result", result);
        restTemplate.postForObject("http://assessment_service:8080/v1/result", resultPayload, Void.class);

        return result;
    }
}
