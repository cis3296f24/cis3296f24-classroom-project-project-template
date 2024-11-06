//modules
const express = require('express');
const app = express();
const path = require('path');


const hostname = 'localhost';
const port = '9999';
app.use(express.static(path.join(__dirname, 'static')));
app.set('');

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'index.html'));
})

app.listen(port, () => {
    console.log(`listening on port: ${port}`);
})
