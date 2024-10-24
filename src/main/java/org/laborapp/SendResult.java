package org.laborapp;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.google.gson.Gson;
import java.util.HashMap;

public class SendResult {
    public void sendResult(HashMap<String, Long> result) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost postRequest = new HttpPost("http://localhost:8080/v1/result");
            Gson gson = new Gson();
            String json = gson.toJson(result);
            postRequest.setEntity(new StringEntity(json, "UTF-8"));
            postRequest.setHeader("Content-type", "application/json");
            httpClient.execute(postRequest);
        }
    }
}
