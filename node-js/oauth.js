
require('dotenv').config();
const {CLIENT_ID, CLIENT_SECRET, TOKEN_HOST, TOKEN_PATH, SCOPE} = process.env

const config = {
  client: {
    id: CLIENT_ID,
    secret:
      CLIENT_SECRET,
  },
  auth: {
    tokenHost: TOKEN_HOST,
    tokenPath: TOKEN_PATH
  },
};
const { ClientCredentials } = require("simple-oauth2");
const client = new ClientCredentials(config);

const tokenParams = {
  scope: SCOPE
};

async function generateAccessToken() {
  try {
    return await client.getToken(tokenParams);
  } catch (error) {
    console.error("Error gathering access token: ", error.message);
  }
}

async function refreshTokenIfExpired(accessToken) {
  if (accessToken.expired()) {
    console.info("Refreshing token....");
    return generateAccessToken();
  } else {
    console.info("Token not expired, returning original...");
    return accessToken;
  }
}

async function run() {
  const accessToken = await generateAccessToken();

  console.log(accessToken);
  const refreshToken = await refreshTokenIfExpired(accessToken);

  console.log(refreshToken);
}


module.exports = {
  generateAccessToken,
  refreshTokenIfExpired,
  run
};
