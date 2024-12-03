/**
 * @file main.test.js
 * @description Unit tests for main.js. This file uses Jest to test key functionalities
 * such as cursor glow effect, authentication checks, fetching track data, and rendering.
 */

jest.mock('d3', () => ({
  select: jest.fn().mockReturnThis(),
  selectAll: jest.fn().mockReturnThis(),
  append: jest.fn().mockReturnThis(),
  attr: jest.fn().mockReturnThis(),
  style: jest.fn().mockReturnThis(),
  remove: jest.fn().mockReturnThis(),
  on: jest.fn().mockReturnThis(),
  data: jest.fn().mockReturnThis(),
  enter: jest.fn().mockReturnThis(),
  exit: jest.fn().mockReturnThis(),
  merge: jest.fn().mockReturnThis(),
  call: jest.fn().mockReturnThis(),
  text: jest.fn().mockReturnThis(),
  each: jest.fn().mockReturnThis(),
  transition: jest.fn().mockReturnThis(),
  duration: jest.fn().mockReturnThis(),
  ease: jest.fn().mockReturnThis(),
  delay: jest.fn().mockReturnThis(),
  scaleLinear: jest.fn().mockReturnThis(),
  scaleTime: jest.fn().mockReturnThis(),
  axisBottom: jest.fn().mockReturnThis(),
  axisLeft: jest.fn().mockReturnThis(),
  line: jest.fn().mockReturnThis(),
  area: jest.fn().mockReturnThis(),
  curveMonotoneX: jest.fn().mockReturnThis(),
  extent: jest.fn().mockReturnThis(),
  mean: jest.fn().mockReturnThis(),
  groups: jest.fn().mockReturnThis()
}));

const { JSDOM } = require('jsdom');
const fetchMock = require('jest-fetch-mock');
fetchMock.enableMocks();

// Set up JSDOM for simulating the DOM
let dom;
let document;

beforeEach(() => {
    dom = new JSDOM(`<!DOCTYPE html>
    <html>
    <body>
        <div id="chart"></div>
        <h1>Test Title</h1>
        <h2>Login</h2>
        <form id="login-form">
            <input id="user" type="text">
            <input id="pass" type="password">
            <button type="submit">Login</button>
        </form>
        <div id="error-message"></div>
        <div id="spinner" style="display: none;"></div>
        <div id="track-list"></div>
    </body>
    </html>`, { url: "http://localhost:3000" });
    document = dom.window.document;
    global.document = document;
    global.window = dom.window;
    global.fetch = fetchMock;
    jest.useFakeTimers(); // Use fake timers
});

// Reset fetch mocks and timers after each test
afterEach(() => {
    fetchMock.resetMocks();
    jest.runAllTimers(); // Run all timers
});

describe("Cursor glow effect", () => {
    test("should add and remove cursor glow on mousemove", () => {
        const event = new dom.window.Event('mousemove');
        event.pageX = 100;
        event.pageY = 200;

        document.dispatchEvent(event);

        const cursor = document.querySelector('.cursor-glow');
        expect(cursor).not.toBeNull();
        expect(cursor.style.left).toBe('100px');
        expect(cursor.style.top).toBe('200px');

        // Simulate timeout
        jest.advanceTimersByTime(500);
        expect(document.querySelector('.cursor-glow')).toBeNull();
    });
});

describe("Authentication functionality", () => {
    test("should redirect to login if not authenticated", async () => {
        fetchMock.mockResponseOnce(JSON.stringify({ authenticated: false }));

        const { checkAuthentication } = require('../main.js');
        await checkAuthentication();

        expect(window.location.href).toBe('/login');
    });

    test("should load top tracks if authenticated", async () => {
        fetchMock.mockResponseOnce(JSON.stringify({ authenticated: true }));
        fetchMock.mockResponseOnce(JSON.stringify([{ name: 'Track 1', artists: [{ name: 'Artist 1' }] }]));

        const { checkAuthentication } = require('../main.js');
        await checkAuthentication();

        expect(fetch).toHaveBeenCalledWith('/top-tracks');
        expect(fetch).toHaveBeenCalledTimes(2); // Auth check + Top tracks
    });
});

describe("Fetch tracks functionality", () => {
    test("should display error if access token is missing", async () => {
        const { fetchTracks } = require('../main.js');
        window.history.pushState({}, '', '/'); // Simulate missing token

        await fetchTracks();

        const errorMessage = document.getElementById('error-message');
        expect(errorMessage.textContent).toBe('Error: Access token not found in the URL.');
    });

    test("should fetch tracks and render them", async () => {
        const mockTracks = [
            { name: 'Track 1', artists: [{ id: '1', name: 'Artist 1' }], popularity: 80, duration_ms: 200000 },
            { name: 'Track 2', artists: [{ id: '2', name: 'Artist 2' }], popularity: 75, duration_ms: 180000 },
        ];

        fetchMock.mockResponseOnce(JSON.stringify(mockTracks));
        const { fetchTracks, renderTracks } = require('../main.js');

        // Simulate token presence
        window.history.pushState({}, '', '/?access_token=test-token');
        await fetchTracks();

        expect(fetch).toHaveBeenCalledWith('/top-tracks?access_token=test-token');
        expect(renderTracks).toHaveBeenCalledWith(mockTracks);
    });
});

describe("Rendering functionality", () => {
    test("should render tracks correctly on the chart", () => {
        const { renderTracks } = require('../main.js');
        const mockTrackData = [
            { key: 'Artist 1', value: { avgPopularity: 80, avgDuration: 200000, count: 10 } },
            { key: 'Artist 2', value: { avgPopularity: 75, avgDuration: 180000, count: 8 } },
        ];

        renderTracks(mockTrackData);

        const circles = document.querySelectorAll('circle');
        expect(circles.length).toBe(mockTrackData.length);

        const labels = document.querySelectorAll('.artist-label');
        expect(labels.length).toBe(mockTrackData.length);
        expect(labels[0].textContent).toBe('Artist 1');
        expect(labels[1].textContent).toBe('Artist 2');
    });
});

describe("Login form functionality", () => {
    test("should display error for invalid login credentials", () => {
        const { loginFormHandler } = require('../main.js');
        const userInput = document.getElementById('user');
        const passInput = document.getElementById('pass');
        const errorMessage = document.getElementById('error-message');

        userInput.value = 'wrongUser';
        passInput.value = 'wrongPass';

        loginFormHandler(new Event('submit'));

        expect(errorMessage.textContent).toBe('Invalid username or password.');
    });

    test("should redirect to login with correct credentials", () => {
        const { loginFormHandler } = require('../main.js');
        const userInput = document.getElementById('user');
        const passInput = document.getElementById('pass');

        userInput.value = 'Admin';
        passInput.value = 'Password123';

        loginFormHandler(new Event('submit'));

        expect(localStorage.getItem('username')).toBe('Admin');
        expect(window.location.href).toBe('/login');
    });
});