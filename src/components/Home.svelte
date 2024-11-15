<script>
    import { onMount } from "svelte";
    import TripPlanner from './TripPlanner.svelte';
    import mapboxgl from 'mapbox-gl';
    import 'mapbox-gl/dist/mapbox-gl.css';

    mapboxgl.accessToken = 'pk.eyJ1Ijoia2hpdGNoIiwiYSI6ImNtM2d1cXN4MTA5YWIya3B4Y3didnBxM3QifQ.GPCb_j31HQhkDYmqvwKgLg'; // need to move this to a .env file or secrets manager


    onMount(() => {

        let map;
        // Initialize Mapbox map 
        map = new mapboxgl.Map({
            container: 'map',
            style: 'mapbox://styles/mapbox/streets-v11',
            center: [-75.1652, 39.9526],
            zoom: 13
        });

        // Function to get the current position
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

        // Event listener for the "Fetch Locations" button
        document.getElementById("fetch-button").addEventListener("click", async function () {
            try {
                const position = await getCurrentPosition();
                const latitude = position.coords.latitude;
                const longitude = position.coords.longitude;

                console.log("Latitude:", latitude);
                console.log("Longitude:", longitude);

                // Center the map to the user's location
                map.setCenter([longitude, latitude]);
                map.setZoom(13); // Optionally adjust zoom level

                // Create a marker for the user's current location
                new mapboxgl.Marker({ color: 'blue' })
                    .setLngLat([longitude, latitude])
                    .addTo(map);

                new mapboxgl.Popup({ offset: 25 })
                    .setLngLat([longitude, latitude])
                    .setHTML('<h3>You are here</h3>')
                    .addTo(map);
            } catch (error) {
                console.error("Error:", error);
                document.getElementById("response-container").innerText = error.message;
            }
        });
    });
</script>

<main class="grid-container">
  <!-- Form -->
    <TripPlanner/>
    <input type="button" id="fetch-button" value="Fetch Locations" />
   <!-- Map -->
    <div class="map-container">
        <div id="map" style="height: 500px; width: 100%;"></div>
    </div>
    <!-- schedule form-->
    <div class="scheduleFetchBackground">
        <p>asdf</p>
    </div>
</main>

<style>
    .grid-container {
        display: grid;
        grid-template-columns: 1fr 2fr; 
        grid-template-rows: auto auto; 
        gap: 20px; 
        padding: 1em;
        max-width: 1000px;
        margin: 0 auto;
    }

    .map-container {
        grid-column: 2; 
        grid-row: 1; 
        height: 500px; 
    }

    .scheduleFetchBackground {
        background-color: #ff0000;
        padding: 1em;
        grid-column: 1 / span 2; 
        grid-row: 2; 
        width: 50%
    }

    @media (min-width: 640px) {
        .grid-container {
            max-width: none;
        }
    }
</style>