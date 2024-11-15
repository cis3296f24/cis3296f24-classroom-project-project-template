<script>
    import { onMount } from "svelte";
    import TripPlanner from './TripPlanner.svelte';
    import mapboxgl from 'mapbox-gl';
    import 'mapbox-gl/dist/mapbox-gl.css';
    
    mapboxgl.accessToken = 'pk.eyJ1Ijoia2hpdGNoIiwiYSI6ImNtM2d1cXN4MTA5YWIya3B4Y3didnBxM3QifQ.GPCb_j31HQhkDYmqvwKgLg'; // need to move this to a .env file or secrets manager

    onMount(() => {
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

        document.getElementById("fetch-button").addEventListener("click", async function () {
            try {
                const position = await getCurrentPosition();
                const latitude = position.coords.latitude;
                const longitude = position.coords.longitude;

                console.log("Latitude:", latitude);
                console.log("Longitude:", longitude);

                // Initialize Mapbox map
                const map = new mapboxgl.Map({
                    container: 'map',
                    style: 'mapbox://styles/mapbox/streets-v11',
                    center: [longitude, latitude],
                    zoom: 13
                });

                // Create a marker for the user's current location
                new mapboxgl.Marker({ color: 'blue' })
                    .setLngLat([longitude, latitude])
                    .addTo(map);

                new mapboxgl.Popup({ offset: 25 })
                    .setLngLat([longitude, latitude])
                    .setHTML('<h3>You are here</h3>')
                    .addTo(map);

                // Add a Mapbox GL JS control for street view
                const marker = new mapboxgl.Marker()
                    .setLngLat([longitude, latitude])
                    .addTo(map);

                // Add a click event to the marker to open street view
                marker.getElement().addEventListener('click', () => {
                    // Open street view at the user's location
                    const streetViewUrl = `https://www.mapbox.com/streetview/?lat=${latitude}&lon=${longitude}`;
                    window.open(streetViewUrl, '_blank');
                });

            } catch (error) {
                console.error("Error:", error);
                document.getElementById("response-container").innerText = error.message;
            }
        });
    });
</script>

<main class="grid-container">
    <!-- Form -->
    <div class="userInputBackground">
        <form action="">
            <input type="text" id="start" name="start" value="Start" /><br /><br />
            <input type="text" id="end" name="end" value="End" />
            <input type="submit" value="Swap" /><br /><br />
            <div class="inlineElements">
                <div class="pairLabel">
                    <input type="radio" id="leave" name="button" value="leave" checked />
                    <label for="leave">Leave</label>
                </div>
                <div class="pairLabel">
                    <input type="radio" id="arrive" name="button" value="arrive" />
                    <label for="arrive">Arrive</label>
                </div>
                <a href="">+Round Trip</a>
                <a href="">+Add Stop</a>
            </div>
            <br />
            <input type="text" id="date" name="date" value="Today" /><br /><br />
            <input type="text" id="time" name="time" value="ASAP" /><br /><br />
            <div class="inlineElements">
                <div class="pairLabel">
                    <input type="checkbox" id="bus" name="bus" checked />
                    <label for="bus">Bus</label>
                </div>
                <div class="pairLabel">
                    <input type="checkbox" id="subway" name="subway" checked />
                    <label for="subway">Subway</label>
                </div>
                <div class="pairLabel">
                    <input type="checkbox" id="trolley" name="trolley" checked /><br />
                    <label for="trolley">Trolley</label>
                </div>
                <div class="pairLabel">
                    <input type="checkbox" id="rail" name="rail" checked />
                    <label for="rail">Rail</label>
                </div>
            </div>
            <br />
            <input type="button" id="fetch-button" value="Fetch Locations" />
        </form>
    </div>

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

    .userInputBackground {
        background-color: #d9d9d9;
        padding: 1em;
        grid-column: 1; 
        grid-row: 1; 
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

    h1 {
        color: #ff3e00;
        text-transform: uppercase;
        font-size: 4em;
        font-weight: 100;
    }
  
    @media (min-width: 640px) {
        .grid-container {
            max-width: none;
        }
    }
</style>