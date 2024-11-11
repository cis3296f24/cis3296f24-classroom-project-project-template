// public/script.js

import { getCurrentPosition } from './functions/getCurrentPosition.js';
import { fetchBusSchedule } from './functions/fetchBusSchedule.js';
import { displayLocations } from './functions/displayLocations.js';
import { parseAndDisplaySchedule } from './functions/parseAndDisplaySchedule.js';

// Add event listener to the fetch button
document.getElementById('fetch-button').addEventListener('click', async function () {
    try {
        const position = await getCurrentPosition();
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;
        const distance = 0.2;

        console.log("Latitude:", latitude);
        console.log("Longitude:", longitude);

        const apiUrl = `/api/locations?lon=${longitude}&lat=${latitude}&type=bus_stops&radius=2`;

        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();

        console.log('Fetched data:', data);

        displayLocations(data, distance);

        if (data && data.length > 0) {
            const firstLocation = data[0];
            const stopId = firstLocation.stop_id || firstLocation.location_id;

            if (stopId) {
                await fetchBusSchedule(stopId);
            } else {
                console.error('No stop_id available for the first location.');
            }
        } else {
            console.error('No locations data available to extract stop_id.');
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('response-container').innerText = error.message;
    }
});