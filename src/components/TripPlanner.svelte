<script>
    // import { onMount } from "svelte";
    // Swap button

    // Go button
        const handleClick = (event) => {
            // gather all data and store
            event.preventDefault();
            let start = document.getElementById("start")
            let end = document.getElementById("end")
            // validate input
            console.log(`Start:${start.value}, End:${end.value}`)
        };
</script>

<div class="userInputBackground">
    <form action="">
        <input type="text" id="start" name="start" placeholder="Start" /><br /><br />
        <input type="text" id="end" name="end" placeholder="End" />
        <input type="submit" value="Swap" /><br /><br />
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
        <br />
        <input type="text" id="date" name="date" placeholder="Today" /><br /><br />
        <input type="text" id="time" name="time" placeholder="at" /><br /><br />
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
                <input type="checkbox" id="trolley" name="trolley" checked /><br
                />
                <label for="trolley">Trolley</label>
            </div>
            <div class="pairLabel">
                <input type="checkbox" id="rail" name="rail" checked />
                <label for="rail">Rail</label>
            </div>
        </div>
        <br />
        <input type="submit" value="Go" id="tp-submit" on:click={handleClick} />
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
    }
</style>
