package com.stock.security.helper;

import com.stock.security.dto.GoogleUserDto;
import org.springframework.stereotype.Service;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class SocialAuthHelper {
    private static final HttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new JacksonFactory();

    private String CLIENT_ID;

    public SocialAuthHelper(
            @Value("${google.clientID}") String clientId) {
        CLIENT_ID = clientId;
    }

    public GoogleUserDto getEmailFromGoogleToken(String token) throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(token);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            return GoogleUserDto.mapGoogleResponse(payload);
        } else {
            throw new IllegalArgumentException("Invalid Google token");
        }
    }
}
