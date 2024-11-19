const clientID = "9f79956a03b04bcfb5df0ff2a5a78059";
const params = new URLSearchParams(window.location.search);
const code = params.get("code");


if (!code) {
    redirectToAuthCodeFlow(clientID);
} else {
    const accessToken = await getAccessToken(clientID, code);
    const profile = await fetchProfile(accessToken);
    const topArtists = await getTopArtists(accessToken);
    const topSongs = await getTopSongs(accessToken);
    populateUI(profile);
    showTopArtists(topArtists);
    showTopSongs(topSongs);
}

export async function redirectToAuthCodeFlow(clientID) {
    /* Redirects to spotify authorization  */
    const verifier = generateCodeVerifier(128);
    const challenge = await generateCodeChallenge(verifier);

    localStorage.setItem("verifier", verifier);

    const params = new URLSearchParams();
    params.append("client_id", clientID);
    params.append("response_type", "code");
    params.append("redirect_uri", "http://localhost:5173/callback");
    params.append("scope", "user-read-private user-read-email user-top-read");
    params.append("code_challenge_method", "S256");
    params.append("code_challenge", challenge);

    document.location = `https://accounts.spotify.com/authorize?${params.toString()}`;
}

function generateCodeVerifier(length) {
    let text = '';
    let possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    
    for (let i = 0; i < length; i++) {
	text += possible.charAt(Math.floor(Math.random() * possible.length));
    }

    return text;
}

async function generateCodeChallenge(codeVerifier) {
    const data = new TextEncoder().encode(codeVerifier);
    const digest = await window.crypto.subtle.digest('SHA-256', data);

    return btoa(String.fromCharCode.apply(null, [...new Uint8Array(digest)]))
	.replace(/\+/g, '-')
	.replace(/\//g, '_')
	.replace(/=+$/, '');
}  

export async function getAccessToken(clientID, code) {
    /* gets access code for token  */
    
    const verifier = localStorage.getItem("verifier");

    const params = new URLSearchParams();
    params.append("client_id", clientID);
    params.append("grant_type", "authorization_code");
    params.append("code", code);
    params.append("redirect_uri", "http://localhost:5173/callback");
    params.append("code_verifier", verifier);

    const result = await fetch("https://accounts.spotify.com/api/token", {
	method: "POST",
	headers: { "Content-Type": "application/x-www-form-urlencoded" },
	body: params
    });

    const { access_token } = await result.json();
    return access_token;
}

async function fetchProfile(token) {
    /* calls API to get user data  */

    const result = await fetch("https://api.spotify.com/v1/me", {
	method: "GET", headers: { Authorization: `Bearer ${token}` }
    });

    if (!result.ok) {
	const errorMessage = await result.text();
	console.error("Could not fetch profile:", errorMessage);
	return null;
    }

    return await result.json();
}

async function getTopArtists(token) {
    const result = await fetch("https://api.spotify.com/v1/me/top/artists", {
	method: "GET", headers: { Authorization:`Bearer ${token}` }
    });

    return await result.json();
}

function showTopArtists(topArtists) {
    const topArtistsList = document.getElementById("topArtistsList");
    topArtistsList.innerHTML = "";

    if (topArtists.items.length == 0) {
	document.getElementById("topArtistsList").innerText = "No artists found";
    } else {
	const artistCount = Math.min(topArtists.items.length, 10);
	for (let i = 0; i < artistCount; i++) {
	    const artist = topArtists.items[i];
	    const listItem = document.createElement("li");
	    listItem.innerText = artist.name;
	    topArtistsList.appendChild(listItem);
	}
    }
}

async function getTopSongs(token) {
    const result = await fetch("https://api.spotify.com/v1/me/top/tracks", {
	method: "GET", headers: { Authorization:`Bearer ${token}` }
    });

    return await result.json();
}

function showTopSongs(topSongs) {
    const topSongsList = document.getElementById("topSongsList");
    topSongsList.innerHTML = "";

    if (topSongs.items.length == 0) {
	document.getElementById("topSongsList").innerText = "No songs found";
    } else {
	const songCount = Math.min(topSongs.items.length, 10);
	for (let i = 0; i < songCount; i++) {
	    const song = topSongs.items[i];
	    const listItem = document.createElement("li");
	    listItem.innerText = `${song.name} by ${song.artists[0].name}`;
	    topSongsList.appendChild(listItem);
	}
    }
}


function populateUI(profile) {
    /* display user's data in UI*/

    document.getElementById("displayName").innerText = profile.display_name;
    if (profile.images[0]) {
	const profileImage = new Image(200, 200);
	profileImage.src = profile.images[0].url;
	document.getElementById("avatar").appendChild(profileImage);
    }
}
