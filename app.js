=======
//modules
const express = require('express');
const app = express();
const path = require('path');
const axios = require('axios');
const fs = require('fs');

// constants
const HOSTNAME = 'localhost';
const PORT = process.env.PORT || 9999;

// Middleware to serve static files (if your static files are in the same directory as this file)
app.use(express.static(path.join(__dirname)));

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'index.html'));
})
// API route to handle requests to /api/get-locations
app.get('/api/get-locations', async (req, res) => {
    const { lat, lon } = req.query; // Extract lat and lon from query params
    console.log('Latitude:', lat);
    console.log('Longitude:', lon);

    if (!lat || !lon) {
        return res.status(400).json({ error: 'Latitude and Longitude are required' });
    }

    try {
        const apiUrl = `https://www3.septa.org/api/locations/get_locations.php?lon=${lon}&lat=${lat}&type=rail_stations&radius=2`;
        console.log('Fetching from:', apiUrl);
        const response = await axios.get(apiUrl);
        res.json(response.data); // Correctly send the response data
    } catch (error) {
        console.error('Error fetching data from SEPTA API:', error.message);
        res.status(500).send('Error fetching data from the SEPTA API');
    }
});

// Start the Express server
app.listen(PORT, () => {
    console.log(`Server running at http://${HOSTNAME}:${PORT}/`);
});
