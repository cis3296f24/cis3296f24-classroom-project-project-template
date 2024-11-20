<script>
    import { onMount } from "svelte";
    import TripPlanner from './TripPlanner.svelte';
    import Map from './Map.svelte';

    let latitude, longitude;
    
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
    async function fetchLocations() {
        try {
            const position = await getCurrentPosition();
            latitude = position.coords.latitude;
            longitude = position.coords.longitude;
            console.log("Latitude:", latitude);
            console.log("Longitude:", longitude);
        } catch (error) {
            console.error("Error:", error);
        }
    }
</script>

<main class="grid-container">
  <!-- Form -->
  <TripPlanner/>
  <input type="button" id="fetch-button" value="Fetch Locations" on:click={fetchLocations} />
  
  <!-- Map -->
  <div class="map-container">
      <Map {latitude} {longitude} />
  </div>

  <!-- schedule form -->
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
        width: 50%;
    }

    @media (min-width: 640px) {
        .grid-container {
            max-width: none;
        }
    }
</style>
