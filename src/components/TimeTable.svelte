<script>
    import { onMount } from 'svelte';

    let stations = []; // closest stations fetched from API
    let selectedStation = null; // curr station
    let timetable = []; // timetable data for the selected station

    // get arrivels for a given sta
    async function fetchTrainArrivals(stationName) {
    console.log(`Fetching train arrivals for station: ${stationName}`);
    const apiUrl = `/api/train_arrivals?station=${encodeURIComponent(stationName)}&results=5&direction=N`;
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        console.log("Raw Train Schedule Data:", data);

        // parse sched data
        const scheduleKey = Object.keys(data)[0];
        const northboundTrains = data[scheduleKey]?.[0]?.Northbound || [];
        const parsedSchedule = northboundTrains.map((train) => ({
            depart_time: formatTime(train.depart_time), 
            destination: train.destination,
            track: train.track || "N/A",
        }));

        console.log("Parsed Train Schedule:", parsedSchedule);
        return parsedSchedule;
    } catch (error) {
        console.error("Error fetching train arrivals:", error);
        return [];
    }
}

// format departure time
function formatTime(departTime) {
    
    const date = new Date(departTime);
    let hours = date.getHours();
    const minutes = String(date.getMinutes()).padStart(2, '0'); 
    const ampm = hours >= 12 ? 'PM' : 'AM'; 

   
    hours = hours % 12 || 12; 

    return `${hours}:${minutes} ${ampm}`; 
}

    // update timetable when sta choice changes
    async function updateTimetable() {
        if (selectedStation) {
            console.log("Updating timetable for selected station:", selectedStation);
            timetable = await fetchTrainArrivals(selectedStation);
            console.log("Updated timetable data:", timetable);
        }
    }

    // fetch nearest sta's for dropdown
    async function fetchNearestStations() {
        console.log("Fetching nearest stations...");
        const position = await getCurrentPosition();
        const { latitude, longitude } = position.coords;
        console.log("User's location:", { latitude, longitude });

        const apiUrl = `/api/locations?lon=${longitude}&lat=${latitude}&type=rail_stations&radius=2`;
        try {
            const response = await fetch(apiUrl);
            console.log(`Locations API response status: ${response.status}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const data = await response.json();
            console.log("Nearest stations data fetched:", data);

            stations = data
                .filter((station) => station.location_name) 
                .slice(0, 3) 
                .map((station) => station.location_name);

            console.log("Processed station names:", stations);

            if (stations.length > 0) {
                selectedStation = stations[0]; 
                await updateTimetable(); 
            }
        } catch (error) {
            console.error('Error fetching nearest stations:', error);
        }
    }

    async function getCurrentPosition() {
        return new Promise((resolve, reject) => {
            if ("geolocation" in navigator) {
                navigator.geolocation.getCurrentPosition(resolve, reject);
            } else {
                reject(new Error("Geolocation API is not available in this browser."));
            }
        });
    }

    onMount(() => {
        console.log("Component mounted. Fetching nearest stations...");
        fetchNearestStations();
    });
</script>

<style>
    .container {
        max-width: 600px;
        margin: 0 auto;
        padding: 1rem;
    }

    .dropdown {
        margin-bottom: 1rem;
    }

    .grid {
        display: grid;
        grid-template-columns: 1fr 1fr 1fr;
        gap: 10px;
    }

    .grid-item {
        padding: 0.5rem;
        border: 1px solid #ccc;
        border-radius: 4px;
        text-align: center;
        background-color: white;
    }

    .header {
        font-weight: bold;
    }
</style>
<div class="container">
    <label for="station-select">Select a Station:</label>
    <select id="station-select" bind:value={selectedStation} class="dropdown" on:change={updateTimetable}>
        {#each stations as station}
            <option value={station}>{station}</option>
        {/each}
    </select>

    <h2>Timetable for {selectedStation}</h2>
    <div class="grid">
        <div class="grid-item header">Departure Time</div>
        <div class="grid-item header">Destination</div>
        <div class="grid-item header">Track</div>
        {#if timetable.length > 0}
            {#each timetable as { depart_time, destination, track }}
                <div class="grid-item">{depart_time}</div>
                <div class="grid-item">{destination}</div>
                <div class="grid-item">{track}</div>
            {/each}
        {:else}
            <p>No timetable data available.</p>
        {/if}
    </div>
</div>