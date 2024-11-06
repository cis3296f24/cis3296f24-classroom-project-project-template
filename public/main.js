document.addEventListener("DOMContentLoaded", function() {
    fetchTracks();
});

function getAccessToken() {
    const params = new URLSearchParams(window.location.search);
    return params.get('access_token');
}

async function checkAuthentication() {
    try {
        const response = await fetch('/auth-status');
        const data = await response.json();

        if (data.authenticated) {
            loadTopTracks();
        } else {
            alert("User is not authenticated. Please log in.");
            window.location.href = '/login';
        }
    } catch (error) {
        console.error("Error checking authentication:", error);
    }
}

async function loadTopTracks() {
    try {
        const response = await fetch('/top-tracks');
        const tracks = await response.json();
        displayTracks(tracks);
    } catch (error) {
        console.error("Error loading top tracks:", error);
    }
}

document.addEventListener('DOMContentLoaded', checkAuthentication);

async function fetchTracks() {
    const accessToken = getAccessToken();
    const spinner = document.getElementById('spinner');

    if (!accessToken) {
        document.getElementById('error-message').innerText = 'Error: Access token not found in the URL.';
        return;
    }

    try {
        spinner.style.display = 'block';
        const response = await fetch(`/top-tracks?access_token=${accessToken}`);

        if (!response.ok) {
            const errorData = await response.json();
            document.getElementById('error-message').innerText = `Error: ${errorData.error || 'An unknown error occurred.'}`;
            throw new Error(`Network response was not ok: ${response.statusText}`);
        }

        const trackData = await response.json();
        console.log('Fetched track data:', trackData);

        if (trackData && trackData.length) {
            renderTracks(trackData);
        } else {
            console.error('No tracks found in the response:', trackData);
            document.getElementById('error-message').innerText = 'No tracks found.';
        }
    } catch (error) {
        console.error('Fetch error:', error);
        document.getElementById('error-message').innerText = 'An error occurred while fetching track data. Please try again later.';
    } finally {
        spinner.style.display = 'none';
    }
}

function renderTracks(data) {
    d3.select("#chart").selectAll("*").remove();

    console.log("Fetched data:", data); // Verify data received

    const width = window.innerWidth;
    const height = window.innerHeight;

    const planetColors = ["#f75", "#b9e", "#72e", "#3a0", "#436"];

    const svg = d3.select("#chart")
        .append("svg")
        .attr("width", width)
        .attr("height", height);

    const xScale = d3.scaleLinear()
        .domain([0, 100])
        .range([0, 1000]);

    const yScale = d3.scaleLinear()
        .domain(d3.extent(data, d => d.duration_ms))  // Use min and max of duration
        .range([height -50 , 50]);  // Top and bottom padding

    const minSize = 5;
    const maxSize = 100;

    // Filtering valid data entries to avoid errors
    const validData = data.filter(d => typeof d.popularity === 'number' && typeof d.duration_ms === 'number');
    console.log("Filtered data for valid entries:", validData);

    const tooltip = d3.select("#tooltip");

    svg.selectAll("circle")
        .data(validData)
        .enter()
        .append("circle")
        .attr("cx", d => xScale(d.popularity))
        .attr("cy", d => yScale(d.duration_ms))
        .attr("r", d => {
            const radius = minSize + ((d.popularity / 100) * (maxSize - minSize));
            console.log(`Radius for track "${d.name}":`, radius); // Debug radius calculation
            return radius;
        }) // Dynamic radius based on popularity
        .attr("fill", (d, i) => planetColors[i % planetColors.length])
        .attr("stroke", "white")
        .attr("stroke-width", 2)
        .on("mouseover", (event, d) => {
            tooltip.style("opacity", 1) // Show the tooltip
                   .html(`<strong>${d.artist}</strong><br>Track: ${d.name}<br>Popularity: ${d.popularity}<br>Duration: ${d.duration} sec`)
                   .style("left", (event.pageX + 10) + "px") // Position the tooltip
                   .style("top", (event.pageY - 30) + "px");
          })
          .on("mousemove", (event) => {
            tooltip.style("left", (event.pageX + 10) + "px")
                   .style("top", (event.pageY - 30) + "px");
          })
          .on("mouseout", () => {
            tooltip.style("opacity", 0); // Hide the tooltip
          });

    svg.selectAll(".artist-label")
        .data(validData)
        .enter()
        .append("text")
        .attr("class", "artist-label")
        .attr("x", d => xScale(d.popularity))
        .attr("y", d => yScale(d.duration_ms) + 5)
        .attr("text-anchor", "middle")
        .text(d => d.artists[0].name);
}


function displayTracks(tracks) {
    const trackList = document.getElementById('track-list');
    trackList.innerHTML = '';

    tracks.forEach(track => {
        const trackItem = document.createElement('div');
        trackItem.textContent = `${track.name} by ${track.artists.map(artist => artist.name).join(', ')}`;
        trackList.appendChild(trackItem);
    });
}


