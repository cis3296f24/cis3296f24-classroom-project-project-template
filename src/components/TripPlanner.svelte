<script>
    import { bus, subway, rail } from "./transportStore";

    //  Form default values
    let radio = "leave";
    let stops = ["", ""];
    let suggestions = {};
    let focusedTextBox = null;

    // clears auto suggest when user clicks out of text box
    document.body.addEventListener("click", () => {
        const suggestionsBox = document.querySelector(".autocomplete-suggestions")
        if (suggestionsBox) 
            suggestionsBox.classList.toggle("hidden");
    });
    // Auto suggest
    const suggestLocation = async (event) => {
        const str = event.target.value;
        if (str === "") {
            suggestions = {};
        } else {
            console.log(str);
            const res = await fetch("/api/autocomplete", {
                method: "POST",
                body: JSON.stringify({ input: str }),
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
            });
            let results = await res.json();
            suggestions[event.target.id] = results.predictions;
        }
    };

    const handleUnfocus = () => {
        setTimeout(() => (focusedTextBox = null), 200);
    };
    // Swap button
    const handleSwap = () => {
        let tmp = stops[1];
        stops[1] = stops[0];
        stops[0] = tmp;
        updateStopsText();
    };
    // Go button
    const handleFormSubmit = (event) => {
        // gather all data and store
        event.preventDefault();
        let start = document.getElementById("stop0");
        let end = document.getElementById(`stop${stops.length - 1}`);
        let date = document.getElementById("date");
        let time = document.getElementById("time");

        console.log(time.value);
        // validate input
        if (start === "") {
        }
        if (end === "") {
        }
        // refactor html to just use a calendar for date and create a MM/DD format for time
        const data = {
            start: start.value,
            end: end.value,
            radio: radio,
            date: date.value,
            time: time.value,
            transitModes: {
                Bus: bus,
                Subway: subway,
                Rail: rail,
            },
        };
        // send data to trip creation function
        createTrip(data);
    };

    // create potential trips
    async function createTrip(data) {
        console.log(JSON.stringify(data));
        const res = await fetch("/api/google_directions", {
            method: "POST",
            body: JSON.stringify(data),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
        });
        if (!res.ok) {
            console.log(`${res.status}: ${res.statusText}`);
            return;
        }
        let routes = await res.json();
        routes = JSON.parse(routes);
        // write to DOM
        displayTrips(routes);
    }

    function displayTrips(routes) {
        const container = document.querySelector(".routes");
        // clear previous html
        container.innerHTML = "";

        routes.forEach((route) => {
            const routeContainer = document.createElement("div");
            routeContainer.className = "route";

            route.legs.forEach((leg) => {
                const legContainer = document.createElement("div");
                legContainer.className = "leg";
                const startContainer = document.createElement("div");
                startContainer.className = "start-location";
                startContainer.innerHTML = leg.start_address;
                legContainer.appendChild(startContainer);
                console.log(leg.start_address);
                routeContainer.appendChild(legContainer);

                leg.steps.forEach((step) => {
                    const stepContainer = document.createElement("div");
                    stepContainer.className = "step";
                    console.log(step.html_instructions);
                    stepContainer.innerHTML = step.html_instructions;

                    // check if step contains substeps
                    if (step.steps) {
                        const subStepContainer = document.createElement("div");
                        subStepContainer.className = "substep";

                        step.steps.forEach((subStep) => {
                            if (containsHTML(subStep)) {
                                console.log(subStep.html_instructions);
                                subStepContainer.innerHTML =
                                    subStep.html_instructions;
                            }
                        });
                        stepContainer.appendChild(subStepContainer);
                    }
                    legContainer.appendChild(stepContainer);
                });
            });
            document.querySelector(".routes").appendChild(routeContainer);
        });

        container.style.display = "block"; // makes routes div visible
    }
    // checks whether object has key "html_instructions"
    function containsHTML(step) {
        return step.hasOwnProperty("html_instructions");
    }
    function updateStopsArray() {
        //  Keeps array up to date with user input
        for (let index = 0; index < stops.length; index++)
            stops[index] = document.getElementById("stop" + index).value;
    }

    const addStop = (event) => {
        //  Shift last position in stops[] and clear value
        stops[stops.length] = stops[stops.length - 1];
        stops[stops.length - 2] = "";
        updateStopsText();
    };

    function removeStop(index) {
        //  Remove requested index from stops[]
        stops.splice(index, 1);
        updateStopsArray();
        //updateStopsText()
    }

    function roundTrip() {
        //  Add start destination to end of stops[] as a new stop
        if (stops[stops.length - 1] != stops[0]) stops[stops.length] = stops[0];
    }
    function updateStopsText() {
        for (let index = 0; index < stops.length; index++)
            document.getElementById("stop" + index).value = stops[index];
    }
</script>

<div id="tp-body">
    <div class="userInputBackground">
        <form action="">
            <div class="input-field">
                {#each stops as stop, i}
                    <!-- Text boxes for each stop -->
                    {#if i == 0}
                        <input
                            type="text"
                            id="stop{i}"
                            name="stop{i}"
                            placeholder="Start"
                            autocomplete="off"
                            on:input={suggestLocation}
                            on:input={updateStopsArray}
                            on:focus={(event) =>
                                (focusedTextBox = event.target.id)}
                            on:blur={handleUnfocus}
                        />
                    {:else if i == stops.length - 1}
                        <input
                            type="text"
                            id="stop{i}"
                            name="stop{i}"
                            placeholder="End"
                            autocomplete="off"
                            on:input={suggestLocation}
                            on:input={updateStopsArray}
                            on:focus={(event) =>
                                (focusedTextBox = event.target.id)}
                            on:blur={handleUnfocus}
                        />
                    {:else}
                        <input
                            type="text"
                            id="stop{i}"
                            name="stop{i}"
                            placeholder="Stop {i}"
                            autocomplete="off"
                            on:input={suggestLocation}
                            on:input={updateStopsArray}
                            on:focus={(event) =>
                                (focusedTextBox = event.target.id)}
                            on:blur={handleUnfocus}
                        />
                    {/if}

                    {#if focusedTextBox === `stop${i}` && suggestions[`stop${i}`]?.length > 0}
                        <div class="autocomplete-suggestions">
                            {#each suggestions[`stop${i}`] as suggestion}
                                <button
                                    class="suggestion"
                                    on:click={(event) => {
                                        event.preventDefault();
                                        document.getElementById(
                                            `stop${i}`,
                                        ).value = suggestion.description;
                                        suggestions[`stop${i}`] = [];
                                        updateStopsArray();
                                    }}
                                >
                                    {suggestion.description}
                                </button>
                            {/each}
                        </div>
                    {/if}
                    <!-- Delete option for each stop -->
                    {#if stops.length > 2}
                        <a href="" on:click={() => removeStop(i)}>X</a>
                    {/if}
                {/each}

                {#if stops.length == 2}
                    <button id="swap" type="button" on:click={handleSwap}
                        >Swap</button
                    >
                {/if}
            </div>

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
                <a href="#" on:click={() => roundTrip()}>+Round Trip</a>
                <a href="#" on:click={() => addStop()}>+Add Stop</a>
            </div>
            <input type="date" id="date" name="date" placeholder="Today" />
            <input type="time" id="time" name="time" placeholder="At" />
            <div class="inlineElements">
                <label>
                    <input
                        type="checkbox"
                        id="bus"
                        name="bus"
                        bind:checked={$bus}
                    />
                    Bus
                </label>
                <label>
                    <input
                        type="checkbox"
                        id="subway"
                        name="subway"
                        bind:checked={$subway}
                    /> Subway
                </label>
                <label>
                    <input
                        type="checkbox"
                        id="rail"
                        name="rail"
                        bind:checked={$rail}
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
    <div class="routes"></div>
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

    .routes {
        display: none;
    }

    .autocomplete-suggestions {
        position: absolute;
        border: 1px solid #ccc;
        background-color: white;
        width: 100%;
        max-width: 300px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        z-index: 10;
        margin-top: 4px;
    }

    .suggestion {
        width: 100%;
        padding: 8px;
        text-align: left;
        cursor: pointer;
        border: none;
        background: none;
        font-size: 14px;
        display: block;
    }

    .suggestion:hover {
        background-color: #f0f0f0;
    }

    .input-field {
        position: relative;
        margin-bottom: 16px;
    }

    .input-field input[type="text"] {
        width: 100%;
    }

    button:hover {
        background-color: #d3d3d3;
    }
</style>
