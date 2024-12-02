document.addEventListener('mousemove', function(e) {
    const cursor = document.createElement('div');
    cursor.classList.add('cursor-glow');
    cursor.style.left = e.pageX + 'px';
    cursor.style.top = e.pageY + 'px';
    document.body.appendChild(cursor);

    setTimeout(() => {
        cursor.remove();
    }, 500); // Adjust duration as needed
});

document.addEventListener("DOMContentLoaded", function() {
    if (window.location.pathname === "http://localhost:3000/profile.html") {
        console.log("Skipping authentication and track fetching for profile page.");
        return; // Exit early for profile.html
    }

    // Perform authentication and fetch tracks for other pages
    checkAuthentication();
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

// Display Title on the page
document.addEventListener("DOMContentLoaded", function() {
    document.body.style.backgroundImage = "url('/sky-full-of-stars-space-4k_1540139420.jpg')";
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundPosition = "center center";
    document.body.style.backgroundRepeat = "no-repeat";

    // Change the font color for the title
    const titleElement = document.querySelector('h1');
    if (titleElement) {
        titleElement.style.color = 'white';
        titleElement.style.textAlign = 'center';
        titleElement.style.fontFamily = "'Arial', sans-serif";
    }
    const loginTitle = document.querySelector('h2');
    if (loginTitle) {
        loginTitle.style.color = 'white';
        loginTitle.style.textAlign = 'center';
        loginTitle.style.fontFamily = "'Arial', sans-serif";
    }
    const loginForm = document.querySelector('form');
    if (loginForm) {
        loginForm.style.color = 'white';
        loginForm.style.textAlign = 'center';
        loginForm.style.fontFamily = "'Arial', sans-serif";
    }
    const loginMessage = document.querySelector('p');
    if (loginMessage) {
        loginMessage.style.color = 'white';
        loginMessage.style.textAlign = 'center';
        loginMessage.style.fontFamily = "'Arial', sans-serif";
    }
    const spinner = document.getElementById('spinner');
    if (spinner) {
        spinner.style.color = 'white'; // Change spinner font color
        spinner.style.textAlign = 'center';
        spinner.style.fontFamily = "'Arial', sans-serif";
    }
    const errorMessage = document.getElementById('error-message');
    if (errorMessage) {
        errorMessage.style.color = 'white'; // Change error message font color
        errorMessage.style.textAlign = 'center';
        errorMessage.style.fontFamily = "'Arial', sans-serif";
    }

    // Check authentication and fetch tracks
    if (window.location.pathname === "http://localhost:3000/profile.html") {
        console.log("Skipping authentication for profile page.");
    } else {
        checkAuthentication(); // Perform authentication for other pages
        fetchTracks();
    }
});

document.addEventListener('DOMContentLoaded', checkAuthentication);

async function fetchTracks() {
    // Skip track fetching if on profile.html
    if (window.location.pathname === "http://localhost:3000/profile.html") {
        console.log("No need to fetch tracks on profile.html.");
        return;
    }

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

    // Aggregate data by artist
    const artistData = d3.groups(data, d => d.artists[0].id)
        .map(([id, values]) => ({
            key: values[0].artists[0].name, // Use artist name for display
            id, // Use artist ID for API calls
            value: {
                avgPopularity: d3.mean(values, d => d.popularity),
                avgDuration: d3.mean(values, d => d.duration_ms),
                count: values.length
            }
        }));

    // Sort artists by the number of tracks and take the top 10
    const topArtists = artistData.sort((a, b) => b.value.count - a.value.count).slice(0, 10);
    console.log('Top artists data:', topArtists);

    const xScale = d3.scaleLinear()
        .domain([0, 100])
        .range([100, width - 100]);

    const yScale = d3.scaleLinear()
        .domain(d3.extent(topArtists, d => d.value.avgDuration))
        .range([height - 150, 150]);

    const minSize = 30;
    const maxSize = 130;


    const tooltip = d3.select("body").append("div")
        .style("position", "absolute")
        .style("background", "rgba(0, 0, 100, 1)")
        .style("color", "#fff")
        .style("padding", "10px")
        .style("border-radius", "0px")
        .style("border-color", "white")
        .style("border-style", "solid")
        .style("pointer-events", "none")
        .style("display", "none")
        .style("font-size", "20px")
        .style("font-family", "'Share Tech Mono', monospace")
        .style("max-width", "300px")
        .style("height", "auto")
        .style("overflow", "visible")
        .style("white-space", "normal");

    console.log("Tooltip element created:", tooltip.node());

    let planetPosition = 1;
    let textPosition = 1;
    svg.selectAll("circle")
        .data(topArtists)
        .enter()
        .append("circle")
        .attr("cx", d => xScale(planetPosition++ * 10))
        .attr("cy", d => yScale(d.value.avgDuration))
        .attr("r", d => {
            const radius = minSize + ((d.value.count / 10) * (maxSize - minSize));
            return radius;
        })
        //Iterate through each circle and create a pattern for it
        .each(function (d, i) {
            const radius = minSize + ((d.value.count / 10) * (maxSize - minSize));
            const patternId = `earthpattern-${i}`;
            let planet;
            //Choose image for planet
            switch(i) {
                case 0:
                    planet = "sun.jpg"
                    break;
                case 1:
                    planet = "mercury.jpg"
                    break;
                case 2:
                    planet = "venus.jpg"
                    break;
                case 3:
                    planet = "earth.jpg"
                    break;
                case 4:
                    planet = "mars.jpg"
                    break;
                case 5:
                    planet = "jupiter.jpg"
                    break;
                case 6:
                    planet = "saturn.jpg"
                    break;
                case 7:
                    planet = "uranus.jpg"
                    break;
                case 8:
                    planet = "neptune.jpg"
                    break;
                case 9:
                    planet = "pluto.jpg"
                    break;
                default:
                    break;
            }
            svg.append("defs")
                .append("pattern")
                .attr("id", patternId)
                .attr("patternUnits", "objectBoundingBox")
                .attr("width", 1)
                .attr("height", 1)
                .append("image")
                .attr("xlink:href", planet)
                .attr("x", 0)
                .attr("y", 0)
                .attr("width", radius * 2)
                .attr("height", radius * 2);

            d3.select(this)
                .attr("fill", `url(#${patternId})`);
        })
        //.attr("stroke", "white")
        //.attr("stroke-width", 2)
        .on("mouseover", async (event, d) => {
            try {
                // Show the tooltip immediately with basic info
                tooltip.style("display", "block")
                    .html(`<strong>${d.key}</strong><br>Tracks: ${d.value.count}<br>Popularity: ${Math.round(d.value.avgPopularity)}<br>Loading more info...`);
        
                // Fetch additional artist details using the Spotify Web API
                const response = await fetch(`https://api.spotify.com/v1/artists/${d.id}`, {
                    headers: {
                        'Authorization': `Bearer ${getAccessToken()}`
                    }
                });
        
                if (!response.ok) {
                    throw new Error('Failed to fetch artist details');
                }
        
                const artistDetails = await response.json();
                const imageUrl = artistDetails.images[0]?.url || '';
        
                // Update the tooltip with additional details
                tooltip.html(`
                    <strong>${d.key}</strong><br>
                    <div style="display: flex; justify-content: center; align-items: center; text-align: center; margin-top: 10px;">
                        <img src="${imageUrl}" alt="Artist Image"
                            style="width: 100px; height: 100px; object-fit: cover; border-radius: 50%;">
                    </div>
                    Genres: ${artistDetails.genres.join(', ')}<br>
                    Followers: ${artistDetails.followers.total.toLocaleString()}<br>
                `);
                tooltip.select('img')
                    .style('width', '100px')
                    .style('height', '100px')
                    .style('object-fit', 'cover')
                    .style('border-radius', '0%');
        
            } catch (error) {
                console.error('Error fetching artist details:', error);
                tooltip.html(`<strong>${d.key}</strong><br>Error loading details.`);
            }
        })
        .on("mousemove", (event) => {
            tooltip.style("top", `${event.pageY + 10}px`)
                .style("left", `${event.pageX + 10}px`);

        })
        .on("mouseout", () => {
            tooltip.style("display", "none");
        });

    svg.selectAll(".artist-label")
        .data(topArtists)
        .enter()
        .append("text")
        .attr("class", "artist-label")
        .attr("x", d => xScale(textPosition++ * 10))
        .attr("y", d => yScale(d.value.avgDuration) + 5)
        .attr("text-anchor", "middle")
        .text(d => d.key);
}

function displayTracks(tracks) {
    const trackList = document.getElementById('track-list');
    trackList.innerHTML = '';

    // Aggregate data by artist
    const artistData = d3.groups(tracks, d => d.artists[0].name)
        .map(([key, values]) => ({
            key,
            value: {
                count: values.length,
                tracks: values.map(track => track.name) // Get track names
            }
        }));

    // Sort artists by the number of tracks and take the top 10
    const topArtists = artistData.sort((a, b) => b.value.count - a.value.count).slice(0, 10);

    topArtists.forEach(artist => {
        const artistItem = document.createElement('div');
        artistItem.textContent = `${artist.key} (${artist.value.count} tracks)`;
        artistItem.style.color = 'white';
        artistItem.style.fontWeight = 'bold';
        trackList.appendChild(artistItem);

        const trackTitles = document.createElement('ul');
        artist.value.tracks.forEach(track => {
            const trackItem = document.createElement('li');
            trackItem.textContent = track;
            trackItem.style.color = 'white';
            trackTitles.appendChild(trackItem);
        });
        trackList.appendChild(trackTitles);
    });
}

// functionality for redirection to profile page
document.addEventListener("DOMContentLoaded", () => {
    // Handle Login Form Submission
    const loginForm = document.getElementById("login-form");
  
    if (loginForm) {
      loginForm.addEventListener("submit", (event) => {
        event.preventDefault();
        
        const username = document.getElementById("user").value;
        const password = document.getElementById("pass").value;
  
        // Temporary hardcoded check (to be replaced with server-side logic)
        if (username === "Admin" && password === "Password123") {
          localStorage.setItem("username", username);
          window.location.href = "/login"; // Redirect to start server-side authentication
        } else {
          document.getElementById("error-message").textContent = "Invalid username or password.";
        }
      })
    }
    // Example: Handle logic for other pages (e.g., profile.html)
  const currentPage = window.location.pathname;

  if (currentPage === "/profile.html") {
    const accessToken = new URLSearchParams(window.location.search).get("access_token");
    if (!accessToken) {
      alert("Access token missing! Redirecting to login.");
      window.location.href = "/";
    } else {
      console.log("Access token found:", accessToken);
      // Perform authenticated actions here (e.g., fetch user data)
    }
  }
})