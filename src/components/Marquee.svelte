<script>
    import { onMount } from "svelte";

    import mapboxgl from 'mapbox-gl';
    import 'mapbox-gl/dist/mapbox-gl.css';
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
        console.log('Fetched data:', data); // Debugging: Log the entire response

        // Filter alerts to include only those with a valid advisory message
        const alertsWithWarnings = data.filter(alert => alert.advisory && alert.advisory.trim() !== "");

        console.log('Filtered alerts with warnings:', alertsWithWarnings); // Debugging

        // Create the marquee text
        warnings = alertsWithWarnings.length > 0
            ? alertsWithWarnings.map(alert => `${alert.route_name}: ${alert.description}`).join(" • ")
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
<div id="marquee">
    <span>{warnings}</span>
</div>
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
    </style>