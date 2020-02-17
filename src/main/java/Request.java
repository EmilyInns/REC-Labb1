import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class Request {

    private HttpClient client = HttpClient.newHttpClient();


    public  void get() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost8080")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("-----------------------------");
        System.out.println(response.body());
        System.out.println("-----------------------------");
    }

    public void post() throws IOException, InterruptedException {
        var values = new HashMap<String, String>() {{
            put("name", "John Doe");
            put("Name", "Jane Doe");
        }};
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost8080/post"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("-----------------------------");
        System.out.println(response.body());
        System.out.println("-----------------------------");
    }

}
