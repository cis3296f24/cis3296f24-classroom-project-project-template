<script>

    let radio = "leave";
    let bus = true;
    let subway = true;
    let trolley = true;
    let rail = true;

    // Auto suggest
    const suggestLocation = (event) => {
        // console.log(`Pressed ${event.data}`);
    };
    // Swap button
    const handleSwap = () => {
        let start = document.getElementById("start");
        let end = document.getElementById("end");
        let tmp = end.value;
        end.value = start.value;
        start.value = tmp;
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
        console.log(JSON.stringify(data));
        const res = await fetch("/api/google_directions", {
            method: "POST",
            body: JSON.stringify(data),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
        });
        if (!res.ok) {
            throw new Error(`HTTP error! Status: ${res.status}`);
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
            routeContainer.setAttribute("class", "route");
            
            route.legs.forEach((leg) => {
                const legContainer = document.createElement("div");
                legContainer.setAttribute("class", "leg");
                legContainer.innerHTML += leg.start_address;
                console.log(leg.start_address);
                routeContainer.appendChild(legContainer);

                leg.steps.forEach((step) => {
                    const stepContainer = document.createElement("div");
                    stepContainer.setAttribute("class", "step");
                    console.log(step.html_instructions);
                    stepContainer.innerHTML = step.html_instructions;

                    // check if step contains substeps
                    if (step.steps) {
                        const subStepContainer = document.createElement("div");
                        subStepContainer.setAttribute("class", "substep");

                        step.steps.forEach((subStep) => {
                            if(containsHTML(subStep)){
                                console.log(subStep.html_instructions);
                                subStepContainer.innerHTML = subStep.html_instructions;
                            }
                        });
                        stepContainer.appendChild(subStepContainer);
                    }
                    legContainer.appendChild(stepContainer);
                });
            });
            document.querySelector(".routes").appendChild(routeContainer);
        });
        
        container.style.display = "block";
    }

    // checks whether object has key "html_instructions"
    function containsHTML(step) {
        return step.hasOwnProperty('html_instructions');
    }
</script>

<div id="tp-body">
    <div class="userInputBackground">
        <form action="">
            <input
                type="text"
                id="start"
                name="start"
                placeholder="Start"
                autocomplete="off"
                on:input={suggestLocation}
            />
            <input
                type="text"
                id="end"
                name="end"
                placeholder="End"
                autocomplete="off"
            />
            <button id="swap" type="button" on:click={handleSwap}>Swap</button>
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
                <a href="">+Round Trip</a>
                <a href="">+Add Stop</a>
            </div>
            <input type="date" id="date" name="date" placeholder="Today" />
            <input type="time" id="time" name="time" placeholder="At" />
            <div class="inlineElements">
                <label>
                    <input
                        type="checkbox"
                        id="bus"
                        name="bus"
                        bind:checked={bus}
                    />
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
    <div class="routes">
        <!-- <TripDirections /> -->
    </div>
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

    #trip-container {
        border: 2px;
        border-radius: 25%;
    }
</style>
