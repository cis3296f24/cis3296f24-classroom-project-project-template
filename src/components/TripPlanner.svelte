<script>
    // import { onMount } from "svelte";
    let radio
    let bus = true
    let subway  = true
    let trolley = true
    let rail    = true
    // Swap button
        
    // Go button
    const handleClick = (event) => {
        // gather all data and store
        event.preventDefault();
        let start   = document.getElementById("start")
        let end     = document.getElementById("end")
        let date    = document.getElementById("date")
        let time    = document.getElementById("time")
        // validate input
        console.log(`Start:${start.value}, End:${end.value}, Date:${date.value}, Time:${time.value}, Radio:${radio}, Bus:${bus}, Subway:${subway}, Trolley:${trolley}, Rail:${rail}`)
    };
</script>

<div class="userInputBackground">
    <form action="">
        <input type="text" id="start" name="start" placeholder="Start" /><br/><br/>
        <input type="text" id="end" name="end" placeholder="End" />
        <input type="submit" value="Swap" /><br/><br/>
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
