package org.example.OtherBots;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeezerApiKey {
    public  void main() {
        try {
            // Replace YOUR_APP_ID and YOUR_APP_SECRET with your actual app id and secret
            String url = "https://connect.deezer.com/oauth/access_token.php?app_id=578682&secret=6695c3e38af1260a1b5f05e35f6ee73f&code=fr58115aa4feae2a318a25e3e7c56130";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            // add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print in String
            System.out.println(response.toString());

            //Get the API key from the response
            String apiKey = response.toString().split("=")[1].split("&")[0];
            System.out.println("API Key: " + apiKey);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
