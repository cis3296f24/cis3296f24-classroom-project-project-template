<script>
    import TripPlanner from './TripPlanner.svelte';
    import TimeTable from './TimeTable.svelte';
    import mapboxgl from 'mapbox-gl';
    import 'mapbox-gl/dist/mapbox-gl.css';
  import Marquee from "./Marquee.svelte";

    mapboxgl.accessToken = 'pk.eyJ1Ijoia2hpdGNoIiwiYSI6ImNtM2d1cXN4MTA5YWIya3B4Y3didnBxM3QifQ.GPCb_j31HQhkDYmqvwKgLg'; // Move this to .env or secrets manager

   
</script>

<main class="grid-container">
    <!-- Marquee for Warnings -->
    <Marquee/>

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
</main>

<style>
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
