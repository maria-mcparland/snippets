package com.example.oauth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

public class OAuthExampleTest {

    @Test
    public void testRefreshTokenIfExpiredExpired() {
        // Mocking current time to simulate an expired token
        long currentTime = System.currentTimeMillis();
        long expiredTime = currentTime - 1000; // 1 second ago
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("expires_at", String.valueOf(expiredTime));

        // Mocking the generateAccessToken method
        OAuthExample oAuthExample = Mockito.spy(new OAuthExample());
        Mockito.doReturn(tokenMap).when(oAuthExample).generateAccessToken();

        // Call the method under test
        oAuthExample.refreshTokenIfExpired(tokenMap);

        // Verify that generateAccessToken is called once
        Mockito.verify(oAuthExample, times(1)).generateAccessToken();
    }

    @Test
    public void testRefreshTokenIfExpiredNotExpired() {
        // Mocking current time to simulate a token that is not expired
        long currentTime = System.currentTimeMillis();
        long futureTime = currentTime + 3600000; // 1 hour in the future
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("expires_at", String.valueOf(futureTime));

        // Mocking the generateAccessToken method
        OAuthExample oAuthExample = Mockito.spy(new OAuthExample());
        Mockito.doReturn(tokenMap).when(oAuthExample).generateAccessToken();

        // Call the method under test
        oAuthExample.refreshTokenIfExpired(tokenMap);

        // Verify that generateAccessToken is called once
        Mockito.verify(oAuthExample, Mockito.never()).generateAccessToken();
    }

    @Test
    public void testGenerateAccessToken() {
        // Mocking the System.currentTimeMillis to fix the time for consistency
        long currentTime = System.currentTimeMillis();
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("expires_in", "3600");
        OAuthExample oAuthExample = Mockito.spy(new OAuthExample());

        // Calling the generateAccessToken method
        Map<String, String> result = oAuthExample.generateAccessToken();

        // Verify the generated token
        assertEquals("new-access-token", result.get("access_token"));
        assertEquals("3600", result.get("expires_in"));
        assertTrue(Long.parseLong(result.get("expires_at")) > currentTime);
    }
}
