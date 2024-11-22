const express = require('express');
const router = express.Router();
const { Client, Status } = require("@googlemaps/google-maps-services-js");
const path = require('path')
const fs = require('node:fs'); // for debug
// require('dotenv').config({path: path.join(__dirname,'..', '..', 'keys.env')})
require('dotenv').config({ path: './keys.env' });

const apiKey = process.env.GOOGLE_API_KEY;

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
    console.log(`POST /api/google_directions`);
    
    
    const data = req.body;
    // console.log(JSON.stringify(data)); // for debug
    // sanitize data

    // check leave/depart choice + time
    time = (Date.parse(data.date + "T" + data.time) / 1000); // conversion to seconds
    // console.log(time);
    let travel_time = "";
    if (data.radio === "leave") {
        travel_time = "departure_time";
    }
    else {
        travel_time = "arrival_time";
    }
    console.log(`${travel_time} at ${time.toString()}`)
    // check transit modes
    let transitModes = [];
    const selections = data.transitModes;
    for (mode in selections) {
        // console.log(`${mode}: ${selections[mode]}`); for debug
        if (selections[mode] === true)
            transitModes.push(mode.toLowerCase());
    }
    // console.log(transitModes); for debug

    // send req
    const client = new Client({});
    client.directions({
        params: {
            alternatives: true,
            origin: data.start,
            destination: data.end,
            [travel_time]: time,
            mode: "transit",
            transit_mode: transitModes,
            region: "us",
            key: apiKey
        },
        timeout: 1000
    }).then(r => {
        fs.writeFile("./directions_output.json", JSON.stringify(r.data, null, 2), (err) => {
            if (err) {
                console.error(err);
            }
        }); // comment this out if you dont want response output written to disk
        const routes = r.data.routes;
        res.json(JSON.stringify(routes));
    }).catch(e => {
        console.error(e);
        res.status(e.status).json({e: `Error fetching directions`});
    });
});

router.post('/autocomplete', async (req, res) => {
    console.log(`POST /api/autocomplete`);
    const client = new Client({});
    client.placeAutocomplete({
        params: {
            components: ["country:us"],
            language: "en",
            location: [39.9526, -75.1652], //philly coords
            radius: 20000, // entirety of philly + room for error
            input: req.body.input,
            key: apiKey
        }
    }).then(r => {
        fs.writeFile("./autocomplete_suggestions.json", JSON.stringify(r.data, null, 2), (err) => {
            if (err) {
                console.error(err);
            }
        }); // comment this out if you dont want response output written to disk
        res.json(r.data);
    }).catch(e => {
        console.error(e);
        res.status(e.status).json({e: `${e.error_message}`});
    });
});
module.exports = router;
