const express = require('express');
const router = express.Router();
const path = require('path');

router.get('/', (req, res) => {
    console.log(`${req.ip} GET ${req.url}`);
    //const fullPath = path.join(__dirname, '..', '..', 'public', 'index.html');
    //console.log('Resolved path:', fullPath); // Log the full path
    res.sendFile(path.join(__dirname, '..', '..', 'public','index.html'));
    //res.send('yo');
});

module.exports = router;
