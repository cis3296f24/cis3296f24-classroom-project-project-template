document.getElementById('fetch-button').addEventListener('click', fetchLocations);

function fetchLocations() {
    //const apiUrl = 'https://www3.septa.org/api/locations/get_locations.php?lon=-75.15457&lat=39.98034&type=rail_stations&radius=2'
    //^normal url for when we've implemented server
    
    const apiUrl = 'https://cors-anywhere.herokuapp.com/https://www3.septa.org/api/locations/get_locations.php?lon=-75.15457&lat=39.98034&type=rail_stations&radius=2';
    //delete when backend is implemented and use top URL^^
    //https://cors-anywhere.herokuapp.com/corsdemo
    //have to request permissions to bypass CORS proxy whenever testing on live server, shouldnt be a problem later

    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('Fetched data:', data); // log JSON for debugging
            displayLocations(data);
        })
        .catch(error => {
            console.error('Error fetching data:', error);
            document.getElementById('response-container').innerText = 'Error fetching data.';
        });
}

function displayLocations(locations) {
    const container = document.getElementById('response-container');
    container.innerHTML = ''; //clear prev loc

    if (locations.length === 0) {
        container.innerText = 'No locations found.';
        return;
    }

    locations.forEach(location => {
        const locationDiv = document.createElement('div');
        locationDiv.classList.add('location-item'); 

        // build each location
        locationDiv.innerHTML = `
            <strong>${location.location_name}</strong><br>
            Coordinates: (${location.location_lat}, ${location.location_lon})<br>
            Distance: ${location.distance} miles<br>
            Location Type: ${location.location_type}
        `;

        // Append location to children
        container.appendChild(locationDiv);
    });
}