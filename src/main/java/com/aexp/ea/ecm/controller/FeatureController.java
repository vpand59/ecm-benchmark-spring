package com.aexp.ea.ecm.controller;

import com.aexp.ea.ecm.service.GetJwtToken;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Component
@EnableScheduling
@RestController
public class FeatureController {
    private final WebClient webClient;

    @Autowired
    public FeatureController(WebClient webClient, GetJwtToken getJwtToken) {
        this.webClient = webClient;
    }

    //Creating a new Feature
    @PostMapping("/feature")
    public HttpEntity<String> CreateNewFeatureUsingRestTemplate() throws IOException {

        // Step 1: Create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Step 2: Define the request URL and payload --- > Post Call
        String apiUrl2 = "https://eob-dev.aexp.com/api/v1/onboarding/features";

        // Step 3: Create a HttpHeaders object (optional)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOlsiZ2ctYWRzLWVjbS1vd25lci02MDAwMDA3MTIiLCJlYV9lY21fZW5nIl0sImFkcyI6InZwYW5kNTkiLCJlbWFpbCI6InZhaXNobmF2aS5wYW5kZXlAYWV4cC5jb20iLCJuYW1lIjoiVmFpc2huYXZpIFBhbmRleSIsImxvYyI6IkJlbmdhbHVydSBVcmJhbiIsImFnZW50IjoiUG9zdG1hblJ1bnRpbWUvNy4yOS4wIiwiYXVkIjoiRUNNLUNsaWVudCIsImlhdCI6MTY4NjcxODc1NiwiZXhwIjoxNjg2NzM2NzU2LCJpc3MiOiJFQS1FQ00ifQ.gRzM4WdFp3m7BQ-CQY8y80M_zoJl1jKjzRipR0rXmyCovp3J8HGomIigv1Q5Aj1xeOT-ek3Hfrbk9m7ymRU99Z6Ih-DVRljhEja234EW7OHHCG8xgaD2RNDI80z1UxUHDrfosSolpSE8Tdc0NE2AuHULaitwgYCKfhmdIjesIj0L5ElXXEL3SPYUj9xf-Z9SlD1Ppz371-8JjfpDyxR3SbStM3pQdsL8iQIGSEOMy7kGE9vyHnKuiN0U7d4uxwjxVmGOia1aVkyvglgzFMUVTP7f5jl7kMQdQVgtfgau8UhPMe_cdcx-b6dhmhYMRKIOeoqN0b06w0Oj5P-cTDU3lg");
        // Read the JSON file for Payload
        String jsonStr = new String(Files.readAllBytes(Paths.get("src/main/resources/feature.json")));

        // Step 4: Create a HttpEntity object
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonStr, headers);

        // Step 5.2: Make the POST request
        String responseEntity = restTemplate.postForObject(apiUrl2, requestEntity, String.class);

        // Step 6: Process the response
        System.out.println(responseEntity);

        return requestEntity;
    }

    //Toggle ON/OFF scheduling...

    public JsonObject getFeatureToggleValue() throws IOException {
        // Step 1: Create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Step 2: Define the request URL and payload --- > Get Call
        String apiUrl = "https://eob-dev.aexp.com/api/v1/onboarding/features/CB64A5822184A1181012165DFCEA047C";

        // Step 3: Create a HttpHeaders object (optional)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOlsiZ2ctYWRzLWVjbS1vd25lci02MDAwMDA3MTIiLCJlYV9lY21fZW5nIl0sImFkcyI6InZwYW5kNTkiLCJlbWFpbCI6InZhaXNobmF2aS5wYW5kZXlAYWV4cC5jb20iLCJuYW1lIjoiVmFpc2huYXZpIFBhbmRleSIsImxvYyI6IkJlbmdhbHVydSBVcmJhbiIsImFnZW50IjoiUG9zdG1hblJ1bnRpbWUvNy4yOS4wIiwiYXVkIjoiRUNNLUNsaWVudCIsImlhdCI6MTY4NjcxODc1NiwiZXhwIjoxNjg2NzM2NzU2LCJpc3MiOiJFQS1FQ00ifQ.gRzM4WdFp3m7BQ-CQY8y80M_zoJl1jKjzRipR0rXmyCovp3J8HGomIigv1Q5Aj1xeOT-ek3Hfrbk9m7ymRU99Z6Ih-DVRljhEja234EW7OHHCG8xgaD2RNDI80z1UxUHDrfosSolpSE8Tdc0NE2AuHULaitwgYCKfhmdIjesIj0L5ElXXEL3SPYUj9xf-Z9SlD1Ppz371-8JjfpDyxR3SbStM3pQdsL8iQIGSEOMy7kGE9vyHnKuiN0U7d4uxwjxVmGOia1aVkyvglgzFMUVTP7f5jl7kMQdQVgtfgau8UhPMe_cdcx-b6dhmhYMRKIOeoqN0b06w0Oj5P-cTDU3lg");

        // Step 4: Create a request entity with headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Make GET request with headers
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

        // Process the response
        String responseBody = response.getBody();
        /*System.out.println("\n Response from get Call");
        System.out.println(responseBody);*/

        // Step 5: Convert it into Json Object
        JsonObject json = new JsonObject(responseBody);

        return json;
    }

    @Scheduled(cron = "0 */1 * ? * *")
    public void ModifyFeatureToggle() throws IOException, InterruptedException {

        // Step 1: Create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Step 2: Define the request URL and payload --- > Post Call
        String apiUrl2 = "https://eob-dev.aexp.com/api/v1/onboarding/features";

        // Step 3: Create a HttpHeaders object (optional)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOlsiZ2ctYWRzLWVjbS1vd25lci02MDAwMDA3MTIiLCJlYV9lY21fZW5nIl0sImFkcyI6InZwYW5kNTkiLCJlbWFpbCI6InZhaXNobmF2aS5wYW5kZXlAYWV4cC5jb20iLCJuYW1lIjoiVmFpc2huYXZpIFBhbmRleSIsImxvYyI6IkJlbmdhbHVydSBVcmJhbiIsImFnZW50IjoiUG9zdG1hblJ1bnRpbWUvNy4yOS4wIiwiYXVkIjoiRUNNLUNsaWVudCIsImlhdCI6MTY4NjcxODc1NiwiZXhwIjoxNjg2NzM2NzU2LCJpc3MiOiJFQS1FQ00ifQ.gRzM4WdFp3m7BQ-CQY8y80M_zoJl1jKjzRipR0rXmyCovp3J8HGomIigv1Q5Aj1xeOT-ek3Hfrbk9m7ymRU99Z6Ih-DVRljhEja234EW7OHHCG8xgaD2RNDI80z1UxUHDrfosSolpSE8Tdc0NE2AuHULaitwgYCKfhmdIjesIj0L5ElXXEL3SPYUj9xf-Z9SlD1Ppz371-8JjfpDyxR3SbStM3pQdsL8iQIGSEOMy7kGE9vyHnKuiN0U7d4uxwjxVmGOia1aVkyvglgzFMUVTP7f5jl7kMQdQVgtfgau8UhPMe_cdcx-b6dhmhYMRKIOeoqN0b06w0Oj5P-cTDU3lg");

        JsonObject json = getFeatureToggleValue();

        System.out.println("\nThe current toggle value is : " + json.getString("toggle") + "\n");

        String requestBody;

        String currentToggle = json.getString("toggle");
        String toggleOn = "on";
        if(currentToggle.equals(toggleOn)) {
            requestBody = "{\"aimId\":\"600000712\",\"features\":[{\"name\":\"Do_Not_Delete\",\"toggle\":\"off\"}]}";
        }
        else {
            requestBody = "{\"aimId\":\"600000712\",\"features\":[{\"name\":\"Do_Not_Delete\",\"toggle\":\"on\"}]}";
        }

        // Step 4: Create a HttpEntity object
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Step 5.2: Make the POST request
        String responseEntity = restTemplate.postForObject(apiUrl2, requestEntity, String.class);

        // Step 6: Process the response
        System.out.println("Submitted a Job for Changing the toggle Value\n");
        System.out.println(responseEntity);
    }
}
