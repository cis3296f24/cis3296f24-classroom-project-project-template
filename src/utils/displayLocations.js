// functions/displayLocations.js

// Function to display the locations
export function displayLocations(locations, distance) {
    const container = document.getElementById('response-container');
    container.innerHTML = ''; // Clear previous locations

    if (!Array.isArray(locations) || locations.length === 0) {
        container.innerText = 'No locations found.';
        return;
    }

    locations.forEach(location => {
        const locationDiv = document.createElement('div');
        locationDiv.classList.add('location-item');
        
        if (location.distance < distance){
        locationDiv.innerHTML = `
            <strong>${location.location_name}</strong><br>
            Coordinates: (${location.location_lat}, ${location.location_lon})<br>
            Distance: ${location.distance} miles<br>
            Location Type: ${location.location_type}<br>
            Stop ID: ${location.stop_id || location.location_id || 'N/A'}
        `;

        container.appendChild(locationDiv);
        }
    });
}