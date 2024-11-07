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

let map;

// Add event listener to the fetch button
document.getElementById('fetch-button').addEventListener('click', async function () {
    try {
        // Get the user's current position
        const position = await getCurrentPosition();
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;

        console.log("Latitude:", latitude);
        console.log("Longitude:", longitude);

        // Initialize the map
        map = L.map('map', {
            attributionControl: false // Disable the default attribution control
        }).setView([latitude, longitude], 13); // Set zoom level

        // Add OpenStreetMap tile layer
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(map);

        // Create a circle marker for the user's current location
        let userMarker = L.circleMarker([latitude, longitude], {
            color: 'blue', // Set the border color
            fillColor: 'blue', // Set the fill color
            fillOpacity: 0.5, // Make it semi-transparent
            radius: 10, // Set the radius of the circle
            weight: 3, // Set the thickness of the border
            opacity: 1
        }).addTo(map)
            .bindPopup("<b>You are here</b>")
            .openPopup();

        // Apply a glow effect by adding a CSS class to the marker's path
        userMarker.on('add', function () {
            const path = userMarker._path; // Get the SVG path element
            path.classList.add('glow-effect'); // Add the glow effect class to the path
        });

        // Construct the API URL using the latitude and longitude from geolocation API
        const apiUrl = `https://cors-anywhere.herokuapp.com/https://www3.septa.org/api/locations/get_locations.php?lon=${longitude}&lat=${latitude}&type=rail_stations&radius=2`;

        // Fetch the data from the API
        const data = await APIcall(apiUrl);

        // Display the locations
        displayLocations(data, map); // Pass map to display function
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('response-container').innerText = error.message;
    }
});




// API call to fetch data from the API
async function APIcall(apiUrl) {
    try {
        const response = await fetch(apiUrl, {
            method: 'GET',
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Origin': window.location.origin, // This is to set the correct origin header
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        console.log('Fetched data:', data); // Log JSON for debugging
        return data;  // Return the fetched data

    } catch (error) {
        console.error("Error fetching data:", error);
        throw error;  // Re-throw the error so it can be caught in the calling code
    }
}

// Function to display the locations on the map as markers
function displayLocations(locations, map) {
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

        // Add a marker for each location on the map
        const lat = location.location_lat;
        const lon = location.location_lon;
        const popupContent = `
            <strong>${location.location_name}</strong><br>
            Coordinates: (${lat}, ${lon})<br>
            Distance: ${location.distance} miles<br>
            Location Type: ${location.location_type}
        `;

        // Add the marker to the map with a default icon
        L.marker([lat, lon]).addTo(map)
            .bindPopup(popupContent);
    });
}
