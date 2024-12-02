const request = require('supertest');
const server = require('../../app'); // Import the server instance

describe('App Routes', () => {
    afterAll((done) => {
        server.close(done);
    });

    it('should serve the main page', async () => {
        const res = await request(server).get('/');
        expect(res.statusCode).toBe(200);
        expect(res.header['content-type']).toMatch(/html/);
    });

    it('should redirect to Spotify login', async () => {
        const res = await request(server).get('/login');
        expect(res.statusCode).toBe(302); // Redirect status code
        expect(res.headers.location).toContain('https://accounts.spotify.com/authorize');
    });

    it('should return unauthorized for /top-tracks without session', async () => {
        const res = await request(server).get('/top-tracks');
        expect(res.statusCode).toBe(401);
        expect(res.body.error).toBe('Unauthorized');
    });

    it('should indicate not authenticated on /auth-status without session', async () => {
        const res = await request(server).get('/auth-status');
        expect(res.statusCode).toBe(200);
        expect(res.body).toEqual({ authenticated: false });
    });

    it('should serve the profile page', async () => {
        const res = await request(server).get('/profile.html');
        expect(res.statusCode).toBe(200);
        expect(res.header['content-type']).toMatch(/html/);
    });
});