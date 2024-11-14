const express = require('express');
const router = express.Router();
const path = require('path');

router.get('/', (req, res) => {
    console.log(`GET ${req.url}`);
    res.sendFile(path.join(__dirname, '..', '..', 'public','index.html'));
});

module.exports = router;
