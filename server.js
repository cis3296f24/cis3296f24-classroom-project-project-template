// simpleServer.js
const express = require('express');
const helmet = require('helmet');
const crypto = require('crypto');

const app = express();
const port = 3000;

app.use(express.static('public')); //

// Middleware for security headers
app.use(helmet());

//For cursor glow CSP
app.use(
    helmet({
        contentSecurityPolicy: {
            useDefaults: true,
            directives: {
                defaultSrc: ["'self'"],
                scriptSrc: [
                    "'self'",
                    'https://cdnjs.cloudflare.com'
                ],
                styleSrc: ["'self'"],
                imgSrc: ["'self'", 'data:']
            },
        },
    })
);

// Middleware to set CSP and generate nonce
app.use((req, res, next) => {
    const nonce = crypto.randomBytes(16).toString('base64'); // Generate a nonce
    // Set CSP header to allow scripts with nonce
    res.setHeader("Content-Security-Policy", `script-src 'self' 'nonce-${nonce}';`);
    res.locals.nonce = nonce; // Store nonce for use in templates
    next();
});

// Route for the root path
app.get('/', (req, res) => {
    res.send(`
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Nonce Test</title>
        </head>
        <body>
            <h1>Hello, this is a CSP nonce test!</h1>
            <script nonce="${res.locals.nonce}">
                console.log('This inline script is allowed to run!');
            </script>
        </body>
        </html>
    `);
});

// Start the server
app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});
