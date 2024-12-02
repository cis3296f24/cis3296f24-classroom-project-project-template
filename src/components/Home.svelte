<script>
    import TripPlanner from './TripPlanner.svelte';
    import TimeTable from "./TimeTable.svelte";
    import Map from './Map.svelte';

<<<<<<< Updated upstream
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
  <!-- schedule form-->
  <div class="scheduleFetchBackground">
      <TimeTable />
  </div>
=======
    mapboxgl.accessToken = 'pk.eyJ1Ijoia2hpdGNoIiwiYSI6ImNtM2d1cXN4MTA5YWIya3B4Y3didnBxM3QifQ.GPCb_j31HQhkDYmqvwKgLg'; // Move this to .env or secrets manager

    let warnings = "Loading SEPTA alerts...";
    let map;

   // Fetch SEPTA alerts for all routes with warnings
const fetchSEPTAAlerts = async () => {
    const endpoint = `http://localhost:3333/api/septa_alerts`;

    try {
        const response = await fetch(endpoint);
        if (!response.ok) {
            throw new Error(`Failed to fetch SEPTA alerts: ${response.statusText}`);
        }

        const data = await response.json();
        console.log('Fetched data:', data); // Debugging

        // Filter alerts to include only those with an advisory message
        const alertsWithWarnings = data.filter(alert => alert.advisory_message);

        // Build the running text with warnings
        warnings = alertsWithWarnings.length > 0
            ? alertsWithWarnings.map(alert => `${alert.route_name}: ${alert.advisory_message}`).join(" • ")
            : "No current alerts for any routes.";
    } catch (error) {
        console.error('Error fetching SEPTA alerts:', error);
        warnings = "Failed to load SEPTA alerts.";
    }
};

    // Initialize map and fetch alerts on mount
    onMount(() => {
        fetchSEPTAAlerts();

        map = new mapboxgl.Map({
            container: 'map',
            style: 'mapbox://styles/mapbox/streets-v11',
            center: [-75.1652, 39.9526], // Philadelphia coordinates
            zoom: 13
        });
    });
</script>

<main class="grid-container">
    <!-- Marquee for Warnings -->
    <div id="marquee">
        <span>{warnings}</span>
    </div>

    <!-- Form -->
    <TripPlanner />

    <!-- Map -->
    <div class="map-container">
        <div id="map" style="height: 500px; width: 100%;"></div>
    </div>

    <!-- Schedule Form -->
    <div class="scheduleFetchBackground">
        <TimeTable />
    </div>
>>>>>>> Stashed changes
</main>

<style>
    /* Marquee for SEPTA Alerts */
    #marquee {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 30px;
        background-color: #f44336; /* Red background for alerts */
        color: white;
        font-size: 0.9rem;
        display: flex;
        align-items: center;
        overflow: hidden;
        white-space: nowrap;
        z-index: 1000; /* Ensure it stays on top of other elements */
    }

    #marquee span {
        display: inline-block;
        animation: scroll 100s linear infinite;
        padding-left: 100%; /* Start off-screen */
    }

    @keyframes scroll {
        from {
            transform: translateX(0%);
        }
        to {
            transform: translateX(-100%);
        }
    }

    /* Main Grid Layout */
    .grid-container {
        display: grid;
        grid-template-columns: 1fr 2fr;
        grid-template-rows: auto auto;
        gap: 20px;
        padding: 1em;
        max-width: 1000px;
        margin: 40px auto; /* Add space for the marquee */
    }

    .map-container {
        grid-column: 2;
        grid-row: 1;
        height: 500px;
    }

    .scheduleFetchBackground {
        background-color: #d9d9d9;
        padding: 1em;
<<<<<<< Updated upstream
        grid-column: 1 / span 2; 
        grid-row: 2; 
=======
        grid-column: 1 / span 2;
        grid-row: 2;
>>>>>>> Stashed changes
        width: 50%;
    }

    @media (min-width: 640px) {
        .grid-container {
            max-width: none;
        }
    }
</style>
