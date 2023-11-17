# Node JS OAuth Snippet

This guide provides instructions on how to use the provided Node.js code to obtain and refresh access tokens using the OAuth 2.0 Client Credentials flow.

## Prerequisites

Before using the code, ensure you have the following:

1. **Node.js**: Make sure you have Node.js (18+) installed on your system. If not, you can download and install it from [nodejs.org](https://nodejs.org/).

2. **Environment Variables**: Create a `.env` file in the project root and set the following environment variables:

   ```env
   CLIENT_ID=your_client_id
   CLIENT_SECRET=your_client_secret
   TOKEN_HOST=token_host_url
   TOKEN_PATH=token_path_url
   SCOPE=desired_scopes
   ```

   Replace your_client_id, your_client_secret, token_host_url, token_path_url, and desired_scopes with your actual OAuth 2.0 client credentials and scope.

## Usage

The code provides two main functions:

- generateAccessToken: Retrieves an access token using the client credentials.
- refreshTokenIfExpired: Checks if the access token is expired and refreshes it if needed.

The code is based on [https://github.com/lelylan/simple-oauth2](https://github.com/lelylan/simple-oauth2) package

## Run the code

Execute the following command to run the code:

```bash
    yarn
    yarn start
```

This will output the access token and the refreshed token if applicable.

Feel free to integrate this code into your project or modify it according to your specific requirements.
