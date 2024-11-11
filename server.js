// server.js
const express = require('express');
const fetch = require('node-fetch'); // Ensure you're using node-fetch version 2
const path = require('path');
const app = express();

// Serve static files from the 'public' directory
app.use(express.static('public'));

// Proxy route for the locations endpoint
app.get('/api/locations', async (req, res) => {
    const { lon, lat, type, radius } = req.query;
    const apiUrl = `https://www3.septa.org/api/locations/get_locations.php?lon=${lon}&lat=${lat}&type=${type}&radius=${radius}`;
    try {
        const response = await fetch(apiUrl);
        const data = await response.json();
        res.json(data);
    } catch (error) {
        console.error('Error fetching locations:', error);
        res.status(500).json({ error: 'Error fetching locations' });
    }
});

// Proxy route for the bus schedules endpoint
app.get('/api/bus_schedules', async (req, res) => {
    const { stop_id } = req.query;
    const apiUrl = `https://www3.septa.org/api/BusSchedules/index.php?stop_id=${stop_id}`;
    try {
        const response = await fetch(apiUrl);
        const data = await response.json();
        res.json(data);
    } catch (error) {
        console.error('Error fetching bus schedule:', error);
        res.status(500).json({ error: 'Error fetching bus schedule' });
    }
});

// Start the server
const PORT = process.env.PORT || 3333;
app.listen(3333, () => {
    console.log(`Server is running on port ${PORT}`);
});
