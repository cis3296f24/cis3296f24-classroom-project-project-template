<script>
    // import { onMount } from "svelte";
    let radio = "leave"
    let bus = true
    let subway  = true
    let trolley = true
    let rail    = true
    
    import { convertFilter } from "maplibre-gl";

    // import { onMount } from "svelte";
    // Auto suggest
    const suggestLocation = (event) => {
        console.log(`Pressed ${event.data}`)
    };
    // Swap button
    const handleSwap = (event) => {
        let start = document.getElementById("start")
        let end = document.getElementById("end")
        console.log(`Before: start: ${start.value}, end: ${end.value}`)
        let tmp = end.value
        end.value = start.value
        start.value = tmp
        console.log(`After: start: ${start.value}, end: ${end.value}`)
        console.log("Swap Button Clicked!");
    };
    // Go button
    const handleFormSubmit = (event) => {
        // gather all data and store
        event.preventDefault();
        let start = document.getElementById("start");
        let end = document.getElementById("end");
        let date = document.getElementById("date");
        let time = document.getElementById("time");
        // validate input
        // refactor html to just use a calendar for date and create a MM/DD format for time
        let data = {
            start: start.value,
            end: end.value,
            radio: radio,
            date: date.value,
            time: time.value,
            bus: bus,
            subway: subway,
            trolley: trolley,
            rail: rail,
        };
        // send data to some function
        createTrip(data);
    };

    // create potential trips
    function createTrip(data) {
        // // convert locations to coordinates
        // coords = getCoords()
        // // pass to function to get trips
        // routes = getTrips()
        // // write to DOM
        // displayTrips()
        console.log(`Creating Trip!!! Payload: ${JSON.stringify(data)}`);
    }
</script>

<div class="userInputBackground">
    <form action="">
        <input type="text" id="start" name="start" placeholder="Start" on:input={suggestLocation} />
        <input type="text" id="end" name="end" placeholder="End" />
        <button id="swap" type="button" on:click={handleSwap}>Swap</button>
        <div class="inlineElements">
            <label>
                <input type="radio" id="leave" name="button" value="leave" bind:group={radio} /> Leave
            </label>
            <label>
                <input type="radio" id="arrive" name="button" value="arrive" bind:group={radio} /> Arrive
            </label>
            <a href="">+Round Trip</a>
            <a href="">+Add Stop</a>
        </div>
        <br />
        <input type="text" id="date" name="date" placeholder="Today" /><br/><br/>
        <input type="text" id="time" name="time" placeholder="At" /><br/><br/>
        <div class="inlineElements">
            <label>
                <input type="checkbox" id="bus" name="bus" bind:checked={bus} /> Bus
            </label>
            <label>
                <input type="checkbox" id="subway" name="subway" bind:checked={subway} /> Subway
            </label>
            <label>
                <input type="checkbox" id="trolley" name="trolley" bind:checked={trolley} /> Trolley
            </label>
            <label>
                <input type="checkbox" id="rail" name="rail" bind:checked={rail} /> Rail
            </label>
        </div>
        <br/>
        <input type="submit" value="Go" id="tp-submit" on:click={handleFormSubmit} />
    </form>
</div>

<style>
    .userInputBackground {
        background-color: #d9d9d9;
        max-width: 360px;
        padding: 1em;
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
        text-align: right;
    }
</style>
