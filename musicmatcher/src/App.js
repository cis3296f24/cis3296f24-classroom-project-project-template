import logo from './logo.svg';
import './App.css';

function App() {
    const CLIENT_ID = "9f79956a03b04bcfb5df0ff2a5a78059"
    const REDIRECT_URI = "http://localhost:3000"
    const AUTH_ENDPOINT = "https://accounts.spotify.com"
    const RESPONSE_TYPE = "token"
    
    return (
    <div className="App">
	<header className="App-header">
	     <a href={`${AUTH_ENDPOINT}?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=${RESPONSE_TYPE}`}>Login to Spotify</a>
	</header>
    </div>
  );
}

export default App;
