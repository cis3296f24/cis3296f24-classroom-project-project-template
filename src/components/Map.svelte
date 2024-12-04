<script>
    import { onMount, afterUpdate } from "svelte";
    import mapboxgl from 'mapbox-gl';
    import 'mapbox-gl/dist/mapbox-gl.css';
    import { bus, subway, trolley, rail } from './transportStore';
    import { getCurrentPosition } from "../utils/getCurrentPosition"; 

    export let latitude;
    export let longitude;

    let mapContainer;
    let map;
    let markers = [];
    let allLocations = [];

    //allocate bus stop locatoins

    let userLocationMarker = null;
    let userLocationPopup = null;

    mapboxgl.accessToken = 'pk.eyJ1Ijoia2hpdGNoIiwiYSI6ImNtM2d1cXN4MTA5YWIya3B4Y3didnBxM3QifQ.GPCb_j31HQhkDYmqvwKgLg';

    onMount(() => {
        initMap();
        fetchLocations();
        setUserLocation();
    });

    afterUpdate(() => {
        updateMarkers();  // after updating checkboxes update markers
    });

    // Fetch locations from the API
    async function fetchLocations() {
        try {
            // Fetch locations for bus stops
            const busResponse = await fetch('/api/locations?lon=-75.15513912656482&lat=39.98011533574885&type=bus_stops&radius=1000');
            if (!busResponse.ok) {
                throw new Error(`HTTP error! Status: ${busResponse.status}`);
            }
            const busData = await busResponse.json();
            console.log("Bus Data:", busData);

            // Fetch locations for rail stations
            const railResponse = await fetch('/api/locations?lon=-75.15513912656482&lat=39.98011533574885&type=rail_stations&radius=1000');
            if (!railResponse.ok) {
                throw new Error(`HTTP error! Status: ${railResponse.status}`);
            }
            const railData = await railResponse.json();
            console.log("Rail Data:", railData);

            // Fetch locations for trolley stops
            const trolleyResponse = await fetch('/api/locations?lon=-75.15513912656482&lat=39.98011533574885&type=trolley_stops&radius=1000');
            if (!trolleyResponse.ok) {
                throw new Error(`HTTP error! Status: ${trolleyResponse.status}`);
            }
            const trolleyData = await trolleyResponse.json();
            console.log("Trolley Data:", trolleyData);

            // Combine all data into one array
            allLocations = [...busData, ...railData, ...trolleyData];
            console.log("All Locations:", allLocations);

            // Update markers 
            updateMarkers();

        } catch (error) {
            console.error('Error fetching locations:', error);
        }
}



    // Initialize the map
    function initMap() {
        map = new mapboxgl.Map({
            container: mapContainer,
            style: 'mapbox://styles/khitch/cm3ngdbhy003e01rzaq77e11i',
            center: [longitude || -75.1652, latitude || 39.9526],
            zoom: 13
        });

        // Wait until the map is fully loaded before adding markers
        map.on('load', () => {
            fetchLocations(); // Fetch locations after the map is loaded
        });
    }

    // Set the user's location on the map
    function setUserLocation() {
        getCurrentPosition()
            .then(position => {
                latitude = position.coords.latitude;
                longitude = position.coords.longitude;
                map.setCenter([longitude, latitude]);

                userLocationMarker = new mapboxgl.Marker({ color: 'blue' })
                    .setLngLat([longitude, latitude])
                    .addTo(map);

                userLocationPopup = new mapboxgl.Popup({ offset: 25 })
                    .setLngLat([longitude, latitude])
                    .setHTML('<h3>Your Location</h3>')
                    .addTo(map);
            })
            .catch(error => {
                console.error("Error getting geolocation: ", error);
            });
    }

    // Update markers based on checkbox states
    function updateMarkers() {
        if (map) {
            // Remove any existing markers except for the user's location and update the array
            markers.forEach(marker => marker.remove());
            markers = [];

            //create set of seen locations
            const seenLocations = new Set();

            // Add markers for the locations that are selected by checkboxes
            allLocations.forEach(location => {
                //see if marker should display based on if it is checked and if it is not already seen
                if (shouldDisplayLocation(location) && !seenLocations.has(location.location_name)) {
                    seenLocations.add(location.location_name);

                    let iconUrl = ''; // Default icon URL

                    // home is users location
                    if (location.location_type !== 'home') {
                        iconUrl = 'septa.png'; // path to custom icon
                    }

                    // Create the marker for the location
                    const marker = new mapboxgl.Marker({
                        element: location.location_type !== 'home' ? createCustomMarker(iconUrl) : null // Use custom icon for non home locations
                    })
                        .setLngLat([location.location_lon, location.location_lat])
                        .addTo(map);

                    // If it’s not the home location, use the custom marker, otherwise use the default blue marker
                    if (location.location_type === 'home') {
                        // Create the default blue marker for home location
                        new mapboxgl.Marker({ color: 'blue' })
                            .setLngLat([location.location_lon, location.location_lat])
                            .addTo(map);
                    } else {
                        markers.push(marker); // Store custom marker in the array to be removed later
                    }

                    const popup = new mapboxgl.Popup({ offset: 25 })
                        .setLngLat([location.location_lon, location.location_lat])
                        .setHTML(`<h3>${location.location_name}</h3>`);

                    // Attach the popup to the marker
                    marker.setPopup(popup);
                }
            });
        }
    }

    // Helper function to create a custom marker with image
    function createCustomMarker(iconUrl) {
        const markerElement = document.createElement('div');
        markerElement.className = 'custom-marker';
        markerElement.style.backgroundImage = `url(${iconUrl})`;
        markerElement.style.backgroundSize = 'cover';
        markerElement.style.width = '16px'; 
        markerElement.style.height = '16px'; 
        return markerElement;
    }


    // Check if the location should be displayed based on the selected checkboxes
    function shouldDisplayLocation(location) {
        switch (location.location_type) {
            case "bus_stops":
                return $bus;
            case "subway":
                return $subway;
            case "trolley_stops":
                return $trolley;
            case "rail_stations":
                return $rail;
            default:
                return false;
        }
    }
</script>


<!-- The container for the map -->
<div bind:this={mapContainer} style="height: 100%; width: 100%"></div>

<style>
    div {
        width: 100%;
        height: 100%;
    }
</style>
