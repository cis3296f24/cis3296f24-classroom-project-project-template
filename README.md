# Spaceify

## Project Abstract

Spaceify is inspired by web applications like Reciptify, Instafest, and Icebergify that reimagine new ways to visualize Spotify's listening data. Spaceify allows users to display their top tracks from the past month, six months, and all time in a unique and engaging way. Users can visually explore their listening history in an aesthetic and fun manner, as well as share it with friends.

## Conceptual Design

The application features an interactive, space-themed scatter plot that visualizes a Spotify user’s top tracks over different durations. It is designed to run on any machine with moderate specs, with Linux systems being ideal for development. However, it is also compatible with Windows and macOS. The application will be built using Node.js and will involve several programming languages throughout the development process, including:

- **HTML & CSS** for the user interface
- **JavaScript** for interactivity
- **Python** (possibly with Flask) for backend services and user authentication
- **React** for building a responsive user interface
- **D3.js** or **Plotly** for data visualization

The project will utilize the Spotify Web API to fetch data regarding user listening habits.

## How to Run

To run the application locally, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/spaceify.git
   cd spaceify
   ```

2. Install the necessary dependencies:

   ```bash
   npm install
   ```

3. Set up your environment variables. Create a `.env` file in the root directory and add your Spotify API credentials:

   ```plaintext
   SPOTIFY_CLIENT_ID=your_client_id
   SPOTIFY_CLIENT_SECRET=your_client_secret
   ```

4. Start the server:

   ```bash
   npm start
   ```

5. Open your browser and navigate to `http://localhost:3000`.

## How to Contribute

Contributions are welcome! To contribute to the project, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push to your fork.
4. Create a pull request with a detailed description of your changes.

Stay updated with the latest status of the project by following this project board: [Project Board](https://github.com/orgs/cis3296f24/projects/95)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
