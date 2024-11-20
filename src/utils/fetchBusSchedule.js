// functions/fetchBusSchedule.js

import { parseAndDisplaySchedule } from './parseAndDisplaySchedule.js';

// Function to fetch bus schedule
export async function fetchBusSchedule(stopId) {
    if (!stopId) {
        console.error('Invalid stopId:', stopId);
        return;
    }

    const apiUrl = `/api/bus_schedules?stop_id=${stopId}`;
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const scheduleData = await response.json();
        console.log('Bus Schedule Data:', scheduleData);

        if (scheduleData.error) {
            console.error('API Error:', scheduleData.error);
            document.getElementById('schedule-container').innerText = scheduleData.error;
            return;
        }

        parseAndDisplaySchedule(scheduleData);
    } catch (error) {
        console.error('Error fetching bus schedule:', error);
    }
}