<script>
    import { onMount } from "svelte";
    import mapboxgl from 'mapbox-gl';
    import 'mapbox-gl/dist/mapbox-gl.css';

    mapboxgl.accessToken = 'pk.eyJ1Ijoia2hpdGNoIiwiYSI6ImNtM2d1cXN4MTA5YWIya3B4Y3didnBxM3QifQ.GPCb_j31HQhkDYmqvwKgLg';

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

<main>
    <div class="userInputBackground">
        <form action="">
            <input type="text" id="start" name="start" value="Start" /><br /><br />
            <input type="text" id="end" name="end" value="End" />
            <input type="submit" value="Swap" /><br /><br />
            <div class="inlineElements">
                <div class="pairLabel">
                    <input
                        type="radio"
                        id="leave"
                        name="button"
                        value="leave"
                        checked
                    />
                    <label for="leave">Leave</label>
                </div>
                <div class="pairLabel">
                    <input
                        type="radio"
                        id="arrive"
                        name="button"
                        value="arrive"
                    />
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
                    <input
                        type="checkbox"
                        id="trolley"
                        name="trolley"
                        checked
                    /><br />
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
    <div class="map-container">
        <div id="map" style="height: 500px; width: 100%;"></div>
    </div>
    <div id="response-container"></div>
</main>

<style>
    main {
        display: flex;
        flex-direction: row;
        text-align: left;
        padding: 1em;
        max-width: 1000px;
        margin: 0 auto;
    }

    .userInputBackground {
        background-color: #d9d9d9;
        max-width: 360px;
        padding: 1em;
        margin-right: 20px;
    }

    .map-container {
        flex-grow: 1;
        height: 500px;
    }

    h1 {
        color: #ff3e00;
        text-transform: uppercase;
        font-size: 4em;
        font-weight: 100;
    }

    .inlineElements {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .pairLabel {
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }

    @media (min-width: 640px) {
        main {
            max-width: none;
        }
    }
</style>
