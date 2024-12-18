import model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class Consumer {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://94.198.50.185:7081/api/users";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String sessionId = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        User user = new User(3L, "James", "Brown", (byte)18);
        ResponseEntity<String> response1 = restTemplate.exchange(url,HttpMethod.POST, new HttpEntity<>(user, headers), String.class);

        user.setName("Thomas");
        user.setLastName("Shelby");
        ResponseEntity<String> response2 = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(user, headers), String.class);

        ResponseEntity<String> response3 = restTemplate.exchange(url + "/" + 3L, HttpMethod.DELETE, new HttpEntity<>(headers), String.class);

        System.out.println("Result: " + response1.getBody() + response2.getBody() + response3.getBody());
    }
}
