const express = require('express');
const router = express.Router();
const { Client, Status } = require("@googlemaps/google-maps-services-js");
const path = require('path')
// require('dotenv').config({path: path.join(__dirname,'..', '..', 'keys.env')})
require('dotenv').config({ path: './keys.env' })

// console.log(process.env.GOOGLE_API_KEY) for debugging

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
    console.log(`POST /google_directions`);
    apiKey = process.env.GOOGLE_API_KEY;
    // create google client
    const data = req.body;
    console.log(JSON.stringify(data)); // for debug
    const client = new Client({});
    client.directions({
        params: {
            origin: data.start,
            destination: data.end,
            mode: "transit",
            key: apiKey
        },
        timeout: 1000
    }).then(r => {
        // get the data needed into an object and send as response
        if (r.data.status !== Status.OK) {
            console.log(`Error fetching directions ${r.data.status}`);
        }
        // let results = {}
        // list of routes 
        const routes = r.data.routes
        res.json(JSON.stringify(routes, null, 2));
    }).catch(e => {
        console.log(e); // may want to show this client side
    });
});

module.exports = router;
