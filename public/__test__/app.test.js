const request = require('supertest');
const server = require('../../app'); // Import the server instance

// Test suite for the app routes
describe('App Routes', () => {
    // Clean up after all tests have run by closing the server
    afterAll((done) => {
        server.close(done);
    });

    // Test for the main page route ('/')
    it('should serve the main page', async () => {
        // Simulate a GET request to the root URL
        const res = await request(server).get('/');

        // Assert that the response status code is 200 (OK)
        expect(res.statusCode).toBe(200);

        // Assert that the 'Content-Type' header is 'html' (indicating an HTML response)
        expect(res.header['content-type']).toMatch(/html/);
    });

    // Test for the login route ('/login')
    it('should redirect to Spotify login', async () => {
        // Simulate a GET request to the /login route
        const res = await request(server).get('/login');

        // Assert that the response status code is 302 (indicating a redirect)
        expect(res.statusCode).toBe(302); // Redirect status code

        // Assert that the 'Location' header contains the Spotify authorization URL
        expect(res.headers.location).toContain('https://accounts.spotify.com/authorize');
    });

    // Test for the '/top-tracks' route without an active session
    it('should return unauthorized for /top-tracks without session', async () => {
        // Simulate a GET request to the /top-tracks route
        const res = await request(server).get('/top-tracks');

        // Assert that the response status code is 401 (Unauthorized)
        expect(res.statusCode).toBe(401);

        // Assert that the response body contains the error message 'Unauthorized'
        expect(res.body.error).toBe('Unauthorized');
    });

    // Test for the '/auth-status' route when not authenticated
    it('should indicate not authenticated on /auth-status without session', async () => {
        // Simulate a GET request to the /auth-status route
        const res = await request(server).get('/auth-status');

        // Assert that the response status code is 200 (OK)
        expect(res.statusCode).toBe(200);

        // Assert that the response body indicates the user is not authenticated
        expect(res.body).toEqual({ authenticated: false });
    });

    // Test for the profile page route ('/profile.html')
    it('should serve the profile page', async () => {
        // Simulate a GET request to the /profile.html route
        const res = await request(server).get('/profile.html');

        // Assert that the response status code is 200 (OK)
        expect(res.statusCode).toBe(200);

        // Assert that the 'Content-Type' header is 'html' (indicating an HTML response)
        expect(res.header['content-type']).toMatch(/html/);
    });
});
