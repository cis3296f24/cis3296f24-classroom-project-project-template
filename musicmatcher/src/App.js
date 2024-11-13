/* Resources:
   - https://dev.to/dom_the_dev/how-to-use-the-spotify-api-in-your-react-js-app-50pn#authentication
   - https://developer.spotify.com/documentation/web-api/concepts/authorization
*/

import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from 'react';

/* Import axios for simpler GET requests */
import axios from 'axios';


function App() {
    const CLIENT_ID = "9f79956a03b04bcfb5df0ff2a5a78059"
    const REDIRECT_URI = "http://localhost:3000"
    const AUTH_ENDPOINT = "https://accounts.spotify.com"
    const RESPONSE_TYPE = "token"

    const [token, setToken] = useState("");

    const [profile, setProfile] = useState([]);

    useEffect(() => {
	const hash = window.location.hash;
	let token = window.localStorage.getItem("token");

	if (!token && hash) {
	    token = hash.substring(1).split("&").find(elem => elem.startsWith("access_token")).split("=")[1]

	    window.location.hash = "";
	    window.localStorage.setItem("token", token)
	}

	setToken(token)
    
    }, [])

    const logout = () => {
	setToken("")
	window.localStorage.removeItem("token")
    }

    const headers = { 'Authorization': `Bearer ${token}`}

    async function getProfile() {
	fetch("https://api.spotify.com/v1/me", {headers})
	    .then(response => response.json())
	    .then(data => console.log(data));
    }
    
    return (
    <div className="App">
	<header className="App-header"> 
	    <h2>MusicMatcher</h2>
	   
	    <a href={`${AUTH_ENDPOINT}?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=${RESPONSE_TYPE}`}>Login to Spotify</a>
	    

	    <button onClick={getProfile}>Get your profile data</button>
	</header>
    </div>
  );
}

export default App;
