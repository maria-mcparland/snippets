// oauth.test.js

require('dotenv').config();
const { ClientCredentials } = require("simple-oauth2");

const { generateAccessToken, refreshTokenIfExpired, run } = require('./oauth');

describe('OAuth Tests', () => {
  let clientMock;

  beforeEach(() => {
    // Mock the ClientCredentials class
    clientMock = jest.spyOn(ClientCredentials.prototype, 'getToken');
  });

  afterEach(() => {
    // Clear the mock after each test
    jest.clearAllMocks();
  });

  afterAll(() => {
    // Restore the original implementation after all tests
    jest.restoreAllMocks();
  });

  it('should generate an access token', async () => {
    // Arrange
    clientMock.mockResolvedValue({ token: { access_token: 'mocked_access_token' } });

    // Act
    const accessToken = await generateAccessToken();

    // Assert
    expect(accessToken.token.access_token).toBe('mocked_access_token');
    expect(clientMock).toHaveBeenCalledTimes(1);
  });

  it('should refresh the token if expired', async () => {
    // Arrange
    const expiredToken = { expired: jest.fn(() => true) };
    clientMock.mockResolvedValue({ token: { access_token: 'refreshed_access_token' } });

    // Act
    const refreshedToken = await refreshTokenIfExpired(expiredToken);

    // Assert
    expect(refreshedToken.token.access_token).toBe('refreshed_access_token');
    expect(expiredToken.expired).toHaveBeenCalledTimes(1);
    expect(clientMock).toHaveBeenCalledTimes(1);
  });

  it('should not refresh the token if not expired', async () => {
    // Arrange
    const notExpiredToken = { expired: jest.fn(() => false) };

    // Act
    const originalToken = await refreshTokenIfExpired(notExpiredToken);

    // Assert
    expect(originalToken).toBe(notExpiredToken);
    expect(notExpiredToken.expired).toHaveBeenCalledTimes(1);
    expect(clientMock).not.toHaveBeenCalled();
  });
});
