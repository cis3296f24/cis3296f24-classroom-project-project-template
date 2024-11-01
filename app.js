const express = require('express');
const path = require('path');
const app = express();
const port = 3000;


app.use(express.static('public'));

app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'public/index.html'));
});

//starts the server and logs a message to the console
app.listen(port, () => {
  console.log(`App running at http://localhost:${port}`);
});
