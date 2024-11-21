// functions/parseAndDisplaySchedule.js

// Function to parse and display the bus schedule
export function parseAndDisplaySchedule(scheduleData) {
    const container = document.getElementById('schedule-container');
    container.innerHTML = ''; // Clear previous schedule

    if (scheduleData.error) {
        console.error('API Error:', scheduleData.error);
        container.innerText = scheduleData.error;
        return;
    }

    if (!Array.isArray(scheduleData) || scheduleData.length === 0) {
        console.error('No bus schedule data to display.');
        container.innerText = 'No bus schedule data found.';
        return;
    }

    scheduleData.forEach(schedule => {
        const scheduleDiv = document.createElement('div');
        scheduleDiv.classList.add('schedule-item');
        scheduleDiv.innerHTML = `
            <strong>Route:</strong> ${schedule.route}<br>
            <strong>Direction:</strong> ${schedule.direction}<br>
            <strong>Scheduled Time:</strong> ${schedule.scheduled_time}<br>
            <strong>Status:</strong> ${schedule.status}
        `;
        container.appendChild(scheduleDiv);
    });
}