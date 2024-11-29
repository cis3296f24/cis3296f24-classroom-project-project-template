// modules
const express = require('express');
const path = require('path');
const app = express();
const home = require('./routes/home.js');
const api = require('./routes/api.js');


// middleware
app.use(express.json());
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
