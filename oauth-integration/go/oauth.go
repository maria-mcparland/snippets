package main

import (
	"context"
	"fmt"
	"net/http"
	"os"
	"time"

	"github.com/joho/godotenv"
	"golang.org/x/oauth2"
	"golang.org/x/oauth2/clientcredentials"
)

func main() {
	// Load environment variables
	err := godotenv.Load()
	clientID := os.Getenv("CLIENT_ID")
	clientSecret := os.Getenv("CLIENT_SECRET")
	tokenHost := os.Getenv("TOKEN_HOST")
	tokenPath := os.Getenv("TOKEN_PATH")
	scope := os.Getenv("SCOPE")

	// Configuration
	config := &clientcredentials.Config{
		ClientID:     clientID,
		ClientSecret: clientSecret,
		TokenURL:     fmt.Sprintf("%s%s", tokenHost, tokenPath),
		Scopes:       []string{scope},
	}
	// Create OAuth2 client
	client := config.Client(context.Background())

	// Run the functions
	accessToken, err := generateAccessToken(client)
	if err != nil {
		fmt.Printf("Error gathering access token: %s\n", err.Error())
		return
	}

	fmt.Printf("Access Token: %s\n", accessToken)

	refreshToken, err := refreshTokenIfExpired(config, accessToken)
	if err != nil {
		fmt.Printf("Error refreshing token: %s\n", err.Error())
		return
	}

	fmt.Printf("Refreshed Token: %s\n", refreshToken)

}

func generateAccessToken(client *http.Client) (string, error) {
	// Obtain access token
	token, err := client.Transport.(*oauth2.Transport).Source.Token()
	fmt.Println(token)
	if err != nil {
		return "", err
	}
	return token.AccessToken, nil
}

func refreshTokenIfExpired(config *clientcredentials.Config, accessToken string) (string, error) {
	// Check if the access token is expired
	currentTime := int(time.Now().Unix())
	tokenInfo, err := config.Token(context.Background())
	if err != nil {
		return "", err
	}

	expirationTime := int(tokenInfo.Expiry.Unix())
	if expirationTime <= currentTime {
		fmt.Println("Token is expired. Refreshing...")
		accessToken, err := generateAccessToken(config.Client(context.Background()))
		if err != nil {
			return "", err
		}
		return accessToken, nil
	} else {
		fmt.Println("Token is not expired. Returning original")
		return accessToken, nil
	}
}
