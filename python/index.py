import os
from oauthlib.oauth2 import BackendApplicationClient
from requests.auth import HTTPBasicAuth
from requests_oauthlib import OAuth2Session
from dotenv import load_dotenv
import time

# Load environment variables

load_dotenv()
CLIENT_ID = os.getenv('CLIENT_ID')
CLIENT_SECRET = os.getenv('CLIENT_SECRET')
TOKEN_HOST = os.getenv('TOKEN_HOST')
TOKEN_PATH = os.getenv('TOKEN_PATH')
SCOPE = os.getenv('SCOPE')

# Configuration
client_auth = HTTPBasicAuth(CLIENT_ID, CLIENT_SECRET)
token_url = f"{TOKEN_HOST}{TOKEN_PATH}"
# Create OAuth2 session
client = BackendApplicationClient(client_id=CLIENT_ID)
oauth = OAuth2Session(client=client)


def generateAccessToken():
    # Obtain access token
    token = oauth.fetch_token(
        token_url=token_url,
        auth=client_auth,
        scope=SCOPE
    )
    return token

def refreshTokenIfExpired(token):
    # Check if the access token is expired
    current_time = int(time.time())
    expiration_time = token.get('expires_at', 0)

    if expiration_time <= current_time:
        print("Token is expired. Refreshing...")
        token = generateAccessToken();
        return token;
    else:
        print("Token is not expired. Returning original")
        return token;


access_token = generateAccessToken();
print("Access Token:", access_token)

refresh_token = refreshTokenIfExpired(access_token);
print("Refreshed Token:", refresh_token)


