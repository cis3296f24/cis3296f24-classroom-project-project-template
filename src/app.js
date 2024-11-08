// app.js
const express = require('express');
// const fetch = require('node-fetch'); // Ensure you're using node-fetch version 2
const path = require('path');
const app = express();
const home = require('../routes/home.js');
const api = require('../routes/api.js');

// Serve static files from the 'public' directory
app.use(express.static(path.join(__dirname, 'public')));
// endpoints
app.use('/', home);
app.use('/api', api);

// Proxy route for the locations endpoint
// app.get('/api/locations', async (req, res) => {
    // const { lon, lat, type, radius } = req.query;
    // const apiUrl = `https://www3.septa.org/api/locations/get_locations.php?lon=${lon}&lat=${lat}&type=${type}&radius=${radius}`;
    // try {
    //     const response = await fetch(apiUrl);
    //     const data = await response.json();
    //     res.json(data);
    // } catch (error) {
    //     console.error('Error fetching locations:', error);
    //     res.status(500).json({ error: 'Error fetching locations' });
    // }
// });

// // Proxy route for the bus schedules endpoint
// app.get('/api/bus_schedules', async (req, res) => {
    // const { stop_id } = req.query;
    // const apiUrl = `https://www3.septa.org/api/BusSchedules/index.php?stop_id=${stop_id}`;
    // try {
    //     const response = await fetch(apiUrl);
    //     const data = await response.json();
    //     res.json(data);
    // } catch (error) {
    //     console.error('Error fetching bus schedule:', error);
    //     res.status(500).json({ error: 'Error fetching bus schedule' });
    // }
// });

// Start the server
const PORT = process.env.PORT || 3333;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
