package com.aexp.ea.ecm.service;

import io.vertx.core.json.JsonObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class GetJwtToken {
    public String getApiAuthToken() throws IOException {

        String ADS_ID = "svc.ecm.user";
        String PASS = "m6#j5qW2";
        String auth = ADS_ID + ":" + PASS;

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        URL obj = new URL("https://eob-dev.aexp.com/api/v1/mgmt/auth");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("X-ECM-Prc-Group", "gg-ads-ecm-owner-600000712");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setRequestProperty("Authorization", authHeaderValue);


        int responseCode = postConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonObject json = new JsonObject(response.toString());
            return json.getString("jwt");

        } else {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        GetJwtToken getJwtToken = new GetJwtToken();
        System.out.println(getJwtToken.getApiAuthToken());
    }
}