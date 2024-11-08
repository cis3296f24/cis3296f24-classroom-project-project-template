// modules
const express = require('express');
// const fetch = require('node-fetch'); // Ensure you're using node-fetch version 2
const path = require('path');
const app = express();
const home = require('./routes/home.js');
const api = require('./routes/api.js');

// endpoints
app.use('/', home);
app.use('/api', api);

// Serve static files from the 'public' directory
app.use(express.static(path.join(__dirname, '..', 'public')));

// Start the server
const PORT = process.env.PORT || 3333;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
