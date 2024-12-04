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

    // Test for the /register route
    it('should register a new user', async () => {
        const res = await request(server)
            .post('/register')
            .set('Content-Type', 'application/json') // Set Content-Type header
            .send({ username: 'testuser', password: 'testpassword' });

        expect(res.statusCode).toBe(201);
        expect(res.body.message).toBe('User registered successfully.');
    });

    // Test for the /spaceify-login route
    it('should login a user', async () => {
        const res = await request(server)
            .post('/spaceify-login')
            .send({ username: 'testuser', password: 'testpassword' });

        expect(res.statusCode).toBe(200);
        expect(res.body.message).toBe('Login successful.');
    });

    // Test for the /friends route
    it('should get the list of friends for a user', async () => {
        const res = await request(server)
            .get('/friends')
            .query({ username: 'testuser' });

        expect(res.statusCode).toBe(200);
        expect(res.body).toHaveProperty('friends');
    });

    // Test for the /friends/manage route
    it('should add and remove a friend', async () => {
        const resAdd = await request(server)
            .post('/friends/manage')
            .send({ username: 'testuser', friendUsername: 'frienduser' });

        expect(resAdd.statusCode).toBe(200);
        expect(resAdd.body.message).toBe('frienduser added to your friends.');

        const resRemove = await request(server)
            .post('/friends/manage')
            .send({ username: 'testuser', friendUsername: 'frienduser' });

        expect(resRemove.statusCode).toBe(200);
        expect(resRemove.body.message).toBe('frienduser removed from your friends.');
    });

    // Test for the /upload-screenshot route
    it('should upload a screenshot', async () => {
        const res = await request(server)
            .post('/upload-screenshot')
            .set('Cookie', 'session=valid-session-cookie') // Mock session cookie
            .attach('screenshot', 'path/to/test/screenshot.png');

        expect(res.statusCode).toBe(200);
        expect(res.body).toHaveProperty('screenshot');
    });

    // Test for the /profile-data route
    it('should fetch profile data', async () => {
        const res = await request(server)
            .get('/profile-data')
            .query({ username: 'testuser' });

        expect(res.statusCode).toBe(200);
        expect(res.body).toHaveProperty('username', 'testuser');
    });
});
