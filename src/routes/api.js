const express = require('express');
const router = express.Router();
const { Client } = require("@googlemaps/google-maps-services-js");

router.get('/locations', async (req, res) => {
    console.log(`GET /api/locations`);
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

router.get('/bus_schedules', async (req, res) => {
    console.log(`GET /api/bus_schedules`);
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

router.post('/google_directions', async (req, res) => {
    console.log(`POST /google_directions\n${req.body.start}`);
    // create google client
    const data = req.body;
    // res.json(data);
    // const client = new Client({});
    // client.directions({})
});

module.exports = router;
