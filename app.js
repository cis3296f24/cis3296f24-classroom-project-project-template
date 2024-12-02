const express = require('express');
const path = require('path');
const helmet = require('helmet');
const crypto = require('crypto');
const axios = require('axios');
const session = require('express-session');

// Configure spaceify user database and friends feature
const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const UserSchema = new mongoose.Schema({
  username: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  friends: { type: [String], default: [] } //list of friend usernames
});
const User = mongoose.model('User', UserSchema);
module.exports = User;

const app = express();
const port = 3000; // Define the port variable

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(express.static('public')); // Assuming your static files are in a 'public' directory
app.use(session({
  secret: 'your-secret-key', // Change this to a secure key
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
        imgSrc: ["'self'", 'data:', 'https://i.scdn.co', 'https://www.pixel4k.com'],
        connectSrc: ["'self'", 'https://api.spotify.com'],
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
  const redirectTo = req.query.redirect || '/'; // Default to homepage if no redirect specified

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
    res.redirect(`${redirectTo}?access_token=${accessToken}`);
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

// Serve profile.html in app.js
app.get('/profile.html', (req, res) => {
  res.sendFile(path.join(__dirname, 'public', 'profile.html'));
});

// Connect to MongoDB
mongoose.connect('mongodb+srv://myUser:myPassword@spaceify1.dt8a4.mongodb.net/?retryWrites=true&w=majority&appName=Spaceify1', {
  useNewUrlParser: true,
  useUnifiedTopology: true,
})
  .then(() => console.log('Connected to MongoDB'))
  .catch((err) => console.error('Failed to connect to MongoDB:', err));

// Register.html route
app.post('/register', async (req, res) => {
  const { username, password } = req.body;

  try {
    const existingUser = await User.findOne({ username });
    if (existingUser) {
      return res.status(400).json({ error: 'Username already exists.' });
    }

    const hashedPassword = await bcrypt.hash(password, 10);
    const newUser = new User({ username, password: hashedPassword });
    await newUser.save();

    res.status(201).json({ message: 'User registered successfully.', username });
  } catch (error) {
    console.error('Error during registration:', error);
    res.status(500).json({ error: 'Internal server error.' });
  }
});

app.post('/spaceify-login', async (req, res) => {
  const { username, password } = req.body;

  try {
    const user = await User.findOne({ username });
    if (!user) {
      return res.status(401).json({ error: 'Invalid username or password.' });
    }

    const isPasswordValid = await bcrypt.compare(password, user.password);
    if (!isPasswordValid) {
      return res.status(401).json({ error: 'Invalid username or password.' });
    }

    // Mock an access token (replace with proper session handling or JWT in production)
    const accessToken = crypto.randomBytes(16).toString('hex');

    req.session.access_token = accessToken; // Store in session
    res.status(200).json({ message: 'Login successful.', username, accessToken });
  } catch (error) {
    console.error('Error during login:', error);
    res.status(500).json({ error: 'Internal server error.' });
  }
});

// Get the list of friends for a user
app.get('/friends', async (req, res) => {
  const username = req.query.username;

  try {
      const user = await User.findOne({ username });
      if (!user) {
          return res.status(404).json({ error: 'User not found.' });
      }
      res.json({ friends: user.friends });
  } catch (error) {
      console.error('Error fetching friends:', error);
      res.status(500).json({ error: 'Internal server error.' });
  }
});

// Add a friend
app.post('/friends/add', async (req, res) => {
  const { username, friendUsername } = req.body;

  try {
      const user = await User.findOne({ username });
      const friend = await User.findOne({ username: friendUsername });

      if (!user || !friend) {
          return res.status(404).json({ error: 'User or friend not found.' });
      }

      if (user.friends.includes(friendUsername)) {
          return res.status(400).json({ error: 'Friend already added.' });
      }

      user.friends.push(friendUsername);
      await user.save();

      res.status(200).json({ message: 'Friend added successfully.', friends: user.friends });
  } catch (error) {
      console.error('Error adding friend:', error);
      res.status(500).json({ error: 'Internal server error.' });
  }
});

// Remove a friend
app.post('/friends/remove', async (req, res) => {
  const { username, friendUsername } = req.body;

  try {
      const user = await User.findOne({ username });

      if (!user) {
          return res.status(404).json({ error: 'User not found.' });
      }

      user.friends = user.friends.filter(friend => friend !== friendUsername);
      await user.save();

      res.status(200).json({ message: 'Friend removed successfully.', friends: user.friends });
  } catch (error) {
      console.error('Error removing friend:', error);
      res.status(500).json({ error: 'Internal server error.' });
  }
});