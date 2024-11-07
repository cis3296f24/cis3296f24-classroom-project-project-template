
// Function to get the user's current position as a Promise
function getCurrentPosition() {
    return new Promise((resolve, reject) => {
        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(
                function (position) {
                    resolve(position);
                },
                function (error) {
                    reject(error);
                }
            );
        } else {
            reject(new Error("Geolocation API is not available in this browser."));
        }
    });
}

// Add event listener to the fetch button
document.getElementById('fetch-button').addEventListener('click', async function () {
    try {
        // Get the user's current position
        const position = await getCurrentPosition();
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;

        console.log("Latitude:", latitude);
        console.log("Longitude:", longitude);

        // Fetch the data from the API
        const response = await fetch(`/api/get-locations?lat=${latitude}&lon=${longitude}`);
        console.log("req:", response);
        if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
        }
        const data = await response.json();
        console.log('Data received from server:', data);
        console.log('displaying');
        displayLocations(data);
        console.log('after');

        } catch (error) {
            console.error('Error fetching data:', error);
        }
});

// Function to display the locations
function displayLocations(locations) {
    const container = document.getElementById('response-container');
    container.innerHTML = ''; // Clear previous locations

    if (!Array.isArray(locations) || locations.length === 0) {
        container.innerText = 'No locations found.';
        return;
    }

    locations.forEach(location => {
        const locationDiv = document.createElement('div');
        locationDiv.classList.add('location-item');

        // Build each location display
        locationDiv.innerHTML = `
            <strong>${location.location_name}</strong><br>
            Coordinates: (${location.location_lat}, ${location.location_lon})<br>
            Distance: ${location.distance} miles<br>
            Location Type: ${location.location_type}
        `;

        // Append location to container
        container.appendChild(locationDiv);
    });
}
