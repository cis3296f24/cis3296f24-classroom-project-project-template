const express = require('express');
const router = express.Router();

router.get('/api/locations', async (req, res) => {
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

router.get('/api/bus_schedules', async (req, res) => {
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