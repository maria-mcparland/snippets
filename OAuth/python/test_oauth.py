import unittest
from unittest.mock import patch
from oauth import generateAccessToken, refreshTokenIfExpired
import time 

class TestStringMethods(unittest.TestCase):
    @patch('oauth.OAuth2Session.fetch_token')
    def test_generateAccessToken(self, mock_fetch_token):
       # Arrange
        mock_fetch_token.return_value = {'access_token': 'mocked_access_token'}

        # Act
        result = generateAccessToken()

        # Assert
        mock_fetch_token.assert_called_once()
        self.assertEqual(result, {'access_token': 'mocked_access_token'})

    @patch('oauth.generateAccessToken')
    @patch('oauth.time')
    def test_refreshTokenIfExpired_expired(self, mock_time, mock_generateAccessToken):
        # Arrange
        mock_time.time.return_value = 0
        expired_token = {'expires_at': 0}
        mock_generateAccessToken.return_value = {'access_token': 'mocked_access_token'}

        # Act
        result = refreshTokenIfExpired(expired_token)

        # Assert
        mock_time.time.assert_called_once()
        mock_generateAccessToken.assert_called_once()
        self.assertEqual(result, {'access_token': 'mocked_access_token'})

    @patch('oauth.generateAccessToken')
    @patch('oauth.time')
    def test_refreshTokenIfExpired_not_expired(self, mock_time, mock_generateAccessToken):
        # Arrange
        mock_time.time.return_value = 1
        not_expired_token = {'expires_at': time.time() + 3600}  # expires in 1 hour

        # Act
        result = refreshTokenIfExpired(not_expired_token)

        # Assert
        mock_time.time.assert_called_once()
        mock_generateAccessToken.assert_not_called()
        self.assertEqual(result, not_expired_token)

if __name__ == '__main__':
    unittest.main()