<script>
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
        let leave = document.getElementById("leave");
        let arrive = document.getElementById("arrive");
        let date = document.getElementById("date");
        let time = document.getElementById("time");
        // validate input
        // refactor html to just use a calendar for date and create a MM/DD format for time
        let data = {
            start: start.value,
            end: end.value,
            leave: leave.value,
            arrive: arrive.value,
            date: date.value,
            time: time.value,
            round_trip: null,
            bus: null,
            subway: null,
            trolley: null,
            rail: null,
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
                <input type="radio" id="arrive" name="button" value="arrive" />
                <label for="arrive">Arrive</label>
            </div>
            <a href="">+Round Trip</a>
            <a href="">+Add Stop</a>
        </div>
        <input type="date" id="date" name="date" placeholder="Today" />
        <input type="text" id="time" name="time" placeholder="at" />
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
                <input type="checkbox" id="trolley" name="trolley" checked />
                <label for="trolley">Trolley</label>
            </div>
            <div class="pairLabel">
                <input type="checkbox" id="rail" name="rail" checked />
                <label for="rail">Rail</label>
            </div>
        </div>
        <input
            type="submit"
            value="Go"
            id="tp-submit"
            on:click={handleFormSubmit}
        />
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
