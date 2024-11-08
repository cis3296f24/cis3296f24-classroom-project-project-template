<script>
    import { onMount } from "svelte";

    onMount(() => { //hack until i look into svelte
        // src/utils/script.js

        // Function to get the user's current position as a Promise
        function getCurrentPosition() {
            return new Promise((resolve, reject) => {
                if ("geolocation" in navigator) {
                    navigator.geolocation.getCurrentPosition(
                        function (position) {
                            resolve(position);
                        },
                        function (error) {
                            reject(error);
                        },
                    );
                } else {
                    reject(
                        new Error(
                            "Geolocation API is not available in this browser.",
                        ),
                    );
                }
            });
        }

        // Add event listener to the fetch button
        document
            .getElementById("fetch-button")
            .addEventListener("click", async function () {
                try {
                    // Get the user's current position
                    const position = await getCurrentPosition();
                    const latitude = position.coords.latitude;
                    const longitude = position.coords.longitude;

                    console.log("Latitude:", latitude);
                    console.log("Longitude:", longitude);

                    // Construct the API URL using the latitude and longitude from geolocation API
                    const apiUrl = `/api/locations?lon=${longitude}&lat=${latitude}&type=bus_stops&radius=2`;

                    // Fetch the data from the API
                    const response = await fetch(apiUrl);
                    if (!response.ok) {
                        throw new Error(
                            `HTTP error! Status: ${response.status}`,
                        );
                    }
                    const data = await response.json();

                    console.log("Fetched data:", data); // Log JSON for debugging

                    // Display the locations
                    displayLocations(data);

                    // Get a valid stop_id from the locations data
                    if (data && data.length > 0) {
                        const firstLocation = data[0];
                        const stopId =
                            firstLocation.stop_id || firstLocation.location_id;

                        if (stopId) {
                            await fetchBusSchedule(stopId);
                        } else {
                            console.error(
                                "No stop_id available for the first location.",
                            );
                        }
                    } else {
                        console.error(
                            "No locations data available to extract stop_id.",
                        );
                    }
                } catch (error) {
                    console.error("Error:", error);
                    document.getElementById("response-container").innerText =
                        error.message;
                }
            });

        function printSome(latitude) {
            console.log("hello world");
            console.log(latitude);
        }

        // Function to fetch bus schedule
        async function fetchBusSchedule(stopId) {
            if (!stopId) {
                console.error("Invalid stopId:", stopId);
                return;
            }

            const apiUrl = `/api/bus_schedules?stop_id=${stopId}`;
            try {
                const response = await fetch(apiUrl);
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                const scheduleData = await response.json();
                console.log("Bus Schedule Data:", scheduleData);

                if (scheduleData.error) {
                    console.error("API Error:", scheduleData.error);
                    document.getElementById("schedule-container").innerText =
                        scheduleData.error;
                    return;
                }

                // Process the data as needed
                parseAndDisplaySchedule(scheduleData);
            } catch (error) {
                console.error("Error fetching bus schedule:", error);
            }
        }

        // Function to display the locations
        function displayLocations(locations) {
            const container = document.getElementById("response-container");
            container.innerHTML = ""; // Clear previous locations

            if (!Array.isArray(locations) || locations.length === 0) {
                container.innerText = "No locations found.";
                return;
            }

            locations.forEach((location) => {
                const locationDiv = document.createElement("div");
                locationDiv.classList.add("location-item");

                // Build each location display
                locationDiv.innerHTML = `
            <strong>${location.location_name}</strong><br>
            Coordinates: (${location.location_lat}, ${location.location_lon})<br>
            Distance: ${location.distance} miles<br>
            Location Type: ${location.location_type}<br>
            Stop ID: ${location.stop_id || location.location_id || "N/A"}
        `;

                // Append location to container
                container.appendChild(locationDiv);
            });
        }

        // Function to parse and display the bus schedule
        function parseAndDisplaySchedule(scheduleData) {
            const container = document.getElementById("schedule-container");
            container.innerHTML = ""; // Clear previous schedule

            if (scheduleData.error) {
                console.error("API Error:", scheduleData.error);
                container.innerText = scheduleData.error;
                return;
            }

            if (!Array.isArray(scheduleData) || scheduleData.length === 0) {
                console.error("No bus schedule data to display.");
                container.innerText = "No bus schedule data found.";
                return;
            }

            scheduleData.forEach((schedule) => {
                // Extract variables directly
                const route = schedule.route;
                const direction = schedule.direction;
                const scheduledTime = schedule.scheduled_time;
                const status = schedule.status;

                // Display or use the variables as needed
                console.log(`Route: ${route}`);
                console.log(`Direction: ${direction}`);
                console.log(`Scheduled Time: ${scheduledTime}`);
                console.log(`Status: ${status}`);
                console.log("------------------------");

                // For displaying in HTML
                const scheduleDiv = document.createElement("div");
                scheduleDiv.classList.add("schedule-item");
                scheduleDiv.innerHTML = `
            <strong>Route:</strong> ${route}<br>
            <strong>Direction:</strong> ${direction}<br>
            <strong>Scheduled Time:</strong> ${scheduledTime}<br>
            <strong>Status:</strong> ${status}
        `;
                container.appendChild(scheduleDiv);
            });
        }
    });
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
            <input type="submit" value="Go" />
        </form>
    </div>
    <button id="fetch-button">Fetch Locations</button>
    <div id="response-container"></div>
</main>

<style>
    main {
        text-align: left;
        padding: 1em;
        max-width: 240px;
        margin: 0 auto;
    }

    h1 {
        color: #ff3e00;
        text-transform: uppercase;
        font-size: 4em;
        font-weight: 100;
    }

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

    @media (min-width: 640px) {
        main {
            max-width: none;
        }
    }
</style>
