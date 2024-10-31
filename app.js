const { createServer } = require('node:http');
const path = require('node:path');
const fs = require('fs');
const url = require('url');

const hostname = 'localhost';
const port = '9999';

const server = createServer((req, res) => {
    filePath = "." + url.parse(req.url).pathname;
    if (filePath.localeCompare("./") == 0){
        filePath = "./index.html"
    }
    console.log(filePath);
    fs.readFile(filePath, (err, data) => {
        if (err) {
            res.writeHead(404, {"Content-Type": "text/html"});
            res.write("Page Not Found.");
            res.end();
        }
        else {
            res.writeHead(200, {"Content-Type": "text/html"});
            res.write(data);
            res.end();
        }
    });
});

server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});

