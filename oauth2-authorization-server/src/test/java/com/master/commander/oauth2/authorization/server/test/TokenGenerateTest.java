package com.master.commander.oauth2.authorization.server.test;

import com.master.commander.oauth2.authorization.server.AuthorizationServerApplication;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(classes = AuthorizationServerApplication.class)
@RequiredArgsConstructor
public class TokenGenerateTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();


    @Test
    void generateToken() {
        String uriString = UriComponentsBuilder.fromUriString("http://localhost/login")
                .queryParam("username", "user")
                .queryParam("password", "password").toUriString();
        ResponseEntity<String> response = restTemplate.exchange(uriString, HttpMethod.GET, null, String.class);

        HttpHeaders headers = response.getHeaders();
        String cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        String sessionId = cookie.split(";")[0].split("=")[1];
        System.out.println(sessionId);



    }
}
