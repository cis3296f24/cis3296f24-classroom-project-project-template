<script>
    let el = document.getElementById("tp-submit")
    console.log(el)
</script>

<main>
    <div class="userInputBackground">
        <form action="">
            <input type="text" id="start" name="start" value="Start" /><br /><br
            />
            <input type="text" id="end" name="end" value="End" />
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
                    <input
                        type="radio"
                        id="arrive"
                        name="button"
                        value="arrive"
                    />
                    <label for="arrive">Arrive</label>
                </div>
                <a href="">+Round Trip</a>
                <a href="">+Add Stop</a>
            </div>
            <br />
            <input type="text" id="date" name="date" value="Today" /><br /><br
            />
            <input type="text" id="time" name="time" value="ASAP" /><br /><br />
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
                    <input
                        type="checkbox"
                        id="trolley"
                        name="trolley"
                        checked
                    /><br />
                    <label for="trolley">Trolley</label>
                </div>
                <div class="pairLabel">
                    <input type="checkbox" id="rail" name="rail" checked />
                    <label for="rail">Rail</label>
                </div>
            </div>
            <br />
            <input type="submit" value="Go" id="tp-submit" />
        </form>
    </div>
</main>

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