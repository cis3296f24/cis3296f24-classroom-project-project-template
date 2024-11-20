<script>
    import { onMount } from 'svelte';

    // mock data, need to figure out autocomplete functionality
    const stations = ['Cecil B. Moore Sta.', 'Temple Univ. Sta.', 'Girard Sta.'];

    // mock data, need to figure out station id's from API
    const timeTableData = {
        'Cecil B. Moore Sta.': [
            { arrival: '08:00', departure: '08:15' },
            { arrival: '10:00', departure: '10:15' },
            { arrival: '12:00', departure: '12:15' },
        ],
        'Temple Univ. Sta.': [
            { arrival: '09:00', departure: '09:10' },
            { arrival: '11:00', departure: '11:10' },
            { arrival: '13:00', departure: '13:10' },
        ],
        'Girard Sta.': [
            { arrival: '07:30', departure: '07:45' },
            { arrival: '09:30', departure: '09:45' },
            { arrival: '11:30', departure: '11:45' },
        ],
    };

    let selectedStation = stations[0];
    let timetable = timeTableData[selectedStation];

    // update when dropdown selection is changed
    function updateTimetable() {
        timetable = timeTableData[selectedStation];
    }
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
        grid-template-columns: 1fr 1fr;
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
        <div class="grid-item header">Arrival Time</div>
        <div class="grid-item header">Departure Time</div>
        {#each timetable as { arrival, departure }}
            <div class="grid-item">{arrival}</div>
            <div class="grid-item">{departure}</div>
        {/each}
    </div>
</div>