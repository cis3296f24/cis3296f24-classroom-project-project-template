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

        // Construct the API URL using the latitude and longitude from geolocation API
        const apiUrl = `https://cors-anywhere.herokuapp.com/https://www3.septa.org/api/locations/get_locations.php?lon=${longitude}&lat=${latitude}&type=rail_stations&radius=2`;

        // Fetch the data from the API
        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();

        console.log('Fetched data:', data); // Log JSON for debugging

        // Display the locations
        displayLocations(data);
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('response-container').innerText = error.message;
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
