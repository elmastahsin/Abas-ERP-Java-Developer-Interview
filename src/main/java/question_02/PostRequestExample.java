package question_02;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.json.JSONObject;

public class PostRequestExample {

    private static final String POST_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final String POST_PARAMS = "{\"title\":\"ABAS\",\"body\":\"JAVA DEVELOPER\",\"userId\":1}";

    public static void main(String[] args) {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection();

            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("RESPONSE CODE: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                Scanner scanner = new Scanner(httpURLConnection.getInputStream());
                StringBuilder response = new StringBuilder();

                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                System.out.println("Raw Response: " + response);


                if (jsonResponse.has("id")) {
                    System.out.println("ID: " + jsonResponse.getInt("id"));
                }
                if (jsonResponse.has("title")) {
                    System.out.println("Title: " + jsonResponse.getString("title"));
                }
                if (jsonResponse.has("body")) {
                    System.out.println("Body: " + jsonResponse.getString("body"));
                }
                if (jsonResponse.has("userId")) {
                    System.out.println("User ID: " + jsonResponse.getInt("userId"));
                }

            } else {
                System.out.println("UNSUCCESSFUL");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL(POST_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        try (OutputStream os = httpURLConnection.getOutputStream()) {
            byte[] input = POST_PARAMS.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return httpURLConnection;
    }
}
