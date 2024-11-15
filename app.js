const express = require('express');
const path = require('path');
const helmet = require('helmet');
const crypto = require('crypto');
const axios = require('axios');
const session = require('express-session');

const app = express();
const port = 3000; // Define the port variable

//add cursor glow
//Original code sourced from Nick Sheffield at https://codepen.io/nicksheffield/pen/GgdNop
var canvas = document.querySelector('#c'),
    ctx = canvas.getContext('2d'),
    points = [],
    m = {x: null, y: null},
    r = 0;
var a = 10; //how many dots
var b = 5;
var c = 0.1;
var d = 100;
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;
m.x = canvas.width / 2;
m.y = canvas.height / 2;
window.addEventListener('mousemove', function(e){
  TweenMax.to(m, 0.3, {x: e.clientX, y: e.clientY, ease: 'linear'})
  document.querySelector('.message').className = 'hide';
})
for(var i=0;i<a;i++){
  points.push({
    r: 360 / a * i,
    p: {x: null, y: null},
    w: Math.random()*5,
    c: '#fff',
    d: Math.random() * (d + 5) - 5,
    s: Math.random() * (b + 5) - 5
  })
}
function render(){
  if(m.x == null || m.y == null) return;
  ctx.fillStyle = 'rgba(0,0,0,'+c+')';
  ctx.fillRect(0, 0, canvas.width, canvas.height);
  ctx.lineCap = 'round';
  for(var i=0; i<points.length; i++){
    var p = points[i];
    p.r += p.s;
    if(p.r >= 360) p.r = p.r - 360;
    var vel = {
      x: p.d * Math.cos(p.r * Math.PI / 180),
      y: p.d * Math.sin(p.r * Math.PI / 180)
    };
    if(p.p.x != null && p.p.y != null){
      ctx.strokeStyle = p.c;
      ctx.lineWidth = 2;
      ctx.beginPath();
      ctx.moveTo(p.p.x, p.p.y);
      ctx.lineTo(m.x + vel.x, m.y + vel.y)
      ctx.stroke();
      ctx.closePath();
    }
    p.p.x = m.x + vel.x;
    p.p.y = m.y + vel.y;
  }
}
window.requestAnimFrame = (function(){
  return  window.requestAnimationFrame   ||
      window.webkitRequestAnimationFrame ||
      window.mozRequestAnimationFrame    ||
      function(callback){
        window.setTimeout(callback, 1000 / 60);
      };
})();
(function animloop(){
  requestAnimFrame(animloop);
  render();
})();
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(express.static('public')); // Assuming your static files are in a 'public' directory
app.use(session({
  secret: process.env.SESSION_SECRET || 'fallback-secret-key',
  resave: false,
  saveUninitialized: true
}));

// Middleware to generate a nonce for each request
app.use((req, res, next) => {
  res.locals.nonce = crypto.randomBytes(16).toString('base64');
  next();
});

// Helmet configuration with CSP
app.use(
  helmet({
    contentSecurityPolicy: {
      useDefaults: true,
      directives: {
        defaultSrc: ["'self'"],
        scriptSrc: ["'self'", (req, res) => `'nonce-${res.locals.nonce}'`, 'https://d3js.org'],
        styleSrc: ["'self'", (req, res) => `'nonce-${res.locals.nonce}'`],
        imgSrc: ["'self'", 'data:', 'https://www.pixel4k.com'],
      },
    },
  })
);


const CLIENT_ID = '89cd82b2bc114cde888005822373c259'; // Your Spotify client ID
const CLIENT_SECRET = 'c7fdc87c8d12446d89080c9691a05ea9'; // Your Spotify client secret
const REDIRECT_URI = 'http://localhost:3000/callback';


// Redirect to Spotify authorization page
app.get('/login', (req, res) => {
  const authUrl = `https://accounts.spotify.com/authorize?client_id=${CLIENT_ID}&response_type=code&redirect_uri=${encodeURIComponent(REDIRECT_URI)}&scope=user-top-read user-read-private user-read-email`;
  res.redirect(authUrl);
});

// Handle Spotify callback
app.get('/callback', async (req, res) => {
  const code = req.query.code;
  const authOptions = {
    url: 'https://accounts.spotify.com/api/token',
    form: {
      code: code,
      redirect_uri: REDIRECT_URI,
      grant_type: 'authorization_code'
    },
    headers: {
      'Authorization': 'Basic ' + Buffer.from(`${CLIENT_ID}:${CLIENT_SECRET}`).toString('base64'),
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    json: true
  };

  try {
    const response = await axios.post(authOptions.url, new URLSearchParams(authOptions.form), { headers: authOptions.headers });
    const accessToken = response.data.access_token;
    req.session.access_token = accessToken; // Store the access token in session

    // Redirect to frontend with the access token as a query parameter
    res.redirect(`/?access_token=${accessToken}`);
  } catch (error) {
    console.error('Error getting access token:', error);
    res.status(500).send('Authentication failed');
  }
});


// Fetch top tracks from Spotify
app.get('/top-tracks', async (req, res) => {
  const accessToken = req.session.access_token;

  if (!accessToken) {
    return res.status(401).json({ error: 'Unauthorized' });
  }

  try {
    const response = await axios.get('https://api.spotify.com/v1/me/top/tracks', {
      headers: { 'Authorization': `Bearer ${accessToken}` }
    });
    res.json(response.data.items); // Send back the top tracks
    //res.json({ firstName: 'Tobi' });
  } catch (error) {
    console.error('Error fetching top tracks:', error);
    res.status(500).json({ error: 'Failed to fetch top tracks' });
  }
});
app.get('/auth-status', (req, res) => {
  if (req.session.access_token) {
    res.json({ authenticated: true });
  } else {
    res.json({ authenticated: false });
  }
});


// Serve the main page
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'public', 'index.html')); // Serve your HTML file
});

//starts the server and logs a message to the console
app.listen(port, () => {
  console.log(`App running at http://localhost:${port}`);
});