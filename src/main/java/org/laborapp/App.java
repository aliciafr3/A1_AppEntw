package org.laborapp;


import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        try {
            GetData dataGetter = new GetData();
            String jsonResponse = dataGetter.getData();

            ProcessData dataProcessor = new ProcessData();
            Map<String, Long> customerRuntime = dataProcessor.calculateTotalRuntime(jsonResponse);

            SendResult resultSender = new SendResult();
            resultSender.sendResult(new HashMap<>(customerRuntime));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
