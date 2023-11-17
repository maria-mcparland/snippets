package com.example.oauth;

import java.util.Map;
import java.util.HashMap;

import org.apache.oltu.oauth2.client.*;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class OAuthExample {

    public static void main(String[] args) {
        // Load environment variables
        String clientId = System.getenv("CLIENT_ID");
        String clientSecret = System.getenv("CLIENT_SECRET");
        String tokenHost = System.getenv("TOKEN_HOST");
        String tokenPath = System.getenv("TOKEN_PATH");
        String scope = System.getenv("SCOPE");

        // Configuration
        String tokenUrl = tokenHost + tokenPath;
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        // Create OAuth2 request
        OAuthClientRequest.TokenRequestBuilder requestBuilder = OAuthClientRequest
                .tokenLocation(tokenUrl)
                .setGrantType(GrantType.CLIENT_CREDENTIALS)
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setScope(scope);

        // Fetch access token
        OAuthAccessTokenResponse oAuthResponse;
        try {
            oAuthResponse = oAuthClient.accessToken(requestBuilder.buildBodyMessage(), OAuth.HttpMethod.POST);
            String accessToken = oAuthResponse.getAccessToken();

            System.out.println("Access Token: " + accessToken);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshTokenIfExpired(Map<String, String> tokenMap) {
        // Check if the access token is expired
        long currentTime = System.currentTimeMillis();
        long expirationTime = Long.parseLong(tokenMap.get("expires_at"));

        if (expirationTime <= currentTime) {
            System.out.println("Token is expired. Refreshing...");
            generateAccessToken();
        } else {
            System.out.println("Token is not expired. Returning original");
        }
    }

    public Map<String, String> generateAccessToken() {
        // Simulate obtaining access token
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("access_token", "new-access-token");
        tokenMap.put("expires_in", "3600");
        tokenMap.put("expires_at", String.valueOf(System.currentTimeMillis() + 3600000));

        System.out.println("New Access Token: " + tokenMap.get("access_token"));

        return tokenMap;
    }
}
