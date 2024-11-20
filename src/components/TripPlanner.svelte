<script>
    import { each } from "svelte/internal";

    //  Form default values
    let radio = "leave";
    let bus = true;
    let subway = true;
    let trolley = true;
    let rail = true;
    let stops = ["", ""];

    // Auto suggest
    const suggestLocation = (event) => {
        // console.log(`Pressed ${event.data}`);
    };
    // Swap button
    const handleSwap = (event) => {
        let tmp = stops[1]
        stops[1] = stops[0]
        stops[0] = tmp
        updateStopsText()
    };
    // Go button
    const handleFormSubmit = (event) => {
        // gather all data and store
        event.preventDefault();
        let start = document.getElementById("stop0");
        let end = document.getElementById("stop" + stops.length-1);
        let date = document.getElementById("date");
        let time = document.getElementById("time");
        // validate input
        // refactor html to just use a calendar for date and create a MM/DD format for time
        const data = {
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
        // send data to trip creation function
        createTrip(data);
    };

    // create potential trips
    async function createTrip(data) {
        console.log(JSON.stringify(data))
        const res = await fetch("/api/google_directions", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        }
        });
        if (!res.ok) {
            throw new Error(`HTTP error! Status: ${res.status}`);
        }
        let routes = await res.json();
        routes = JSON.parse(routes)
        // const array = [{test: 0, test2: 1}, {ok: "lol"}]
        // array.forEach((e) => {
        //     console.log(e)
        // })
        // write to DOM
        displayTrips(routes)
    }

    function displayTrips(routes) {
        const container = document.createElement("div")
        container.setAttribute("class", "trip-data")
        routes.forEach((route) => {
            route.legs.forEach((leg) => {
                console.log(leg)
                container.innerHTML += leg.start_address + "<br><br>";
                console.log(leg.start_address)
                leg.steps.forEach((step) => {
                    // check if step contains substeps
                    // step.forEach((subStep) => {
                    //     console.log(subStep)
                    // })
                    container.innerHTML += step.html_instructions + "<br><br>";
                    console.log(step.html_instructions)
                })
            })
        });
        document.getElementById("trip-container").appendChild(container)
    }

    function updateStopsArray() {
        //  Keeps array up to date with user input
        for (let index = 0; index < stops.length; index++)
            stops[index] = document.getElementById("stop" + index).value;
    }

    const addStop = (event) => {
        //  Shift last position in stops[] and clear value
        stops[stops.length] = stops[stops.length-1]
        stops[stops.length-2] = ""
        updateStopsText()
    }

    function removeStop(index) {
        //  Remove requested index from stops[]
        stops.splice(index, 1)
        updateStopsArray()
        //updateStopsText()
    }

    function roundTrip() {
        //  Add start destination to end of stops[] as a new stop
        if (stops[stops.length-1] != stops[0]) stops[stops.length] = stops[0]
    }
    function updateStopsText() {
        for (let index = 0; index < stops.length; index++)
            document.getElementById("stop"+index).value = stops[index]
    }
</script>

<div class="userInputBackground">
    <form action="">
        {#each stops as stop, i}
            <!-- Text boxes for each stop -->
            {#if (i == 0)}
                <input type="text" id="stop{i}" name="stop{i}" placeholder="Start" autocomplete="off" on:input={suggestLocation, updateStopsArray}/>
            {:else if (i==stops.length-1)}
                <input type="text" id="stop{i}" name="stop{i}" placeholder="End" autocomplete="off" on:input={suggestLocation, updateStopsArray}/>
            {:else}
                <input type="text" id="stop{i}" name="stop{i}" placeholder="Stop {i}" autocomplete="off" on:input={suggestLocation, updateStopsArray}/>
            {/if}

            <!-- Delete option for each stop -->
            {#if (stops.length > 2)}
                <a href="#" on:click={() => (removeStop(i))}>X</a>
            {/if}
        {/each}

        {#if (stops.length == 2)}
            <button id="swap" type="button" on:click={handleSwap}>Swap</button>
        {/if}

        <div class="inlineElements">
            <label>
                <input
                    type="radio"
                    id="leave"
                    name="button"
                    value="leave"
                    bind:group={radio}
                /> Leave
            </label>
            <label>
                <input
                    type="radio"
                    id="arrive"
                    name="button"
                    value="arrive"
                    bind:group={radio}
                /> Arrive
            </label>
            <a href="#" on:click={() => (roundTrip())}>+Round Trip</a>
            <a href="#" on:click={() => (addStop())}>+Add Stop</a>
        </div>
        <input type="date" id="date" name="date" placeholder="Today" />
        <input type="time" id="time" name="time" placeholder="At" />
        <div class="inlineElements">
            <label>
                <input type="checkbox" id="bus" name="bus" bind:checked={bus} />
                Bus
            </label>
            <label>
                <input
                    type="checkbox"
                    id="subway"
                    name="subway"
                    bind:checked={subway}
                /> Subway
            </label>
            <label>
                <input
                    type="checkbox"
                    id="trolley"
                    name="trolley"
                    bind:checked={trolley}
                /> Trolley
            </label>
            <label>
                <input
                    type="checkbox"
                    id="rail"
                    name="rail"
                    bind:checked={rail}
                /> Rail
            </label>
        </div>
        <input
            type="submit"
            value="Go"
            id="tp-submit"
            on:click={handleFormSubmit}
        />
    </form>
</div>
<div id="trip-container">

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
</style>
