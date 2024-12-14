// Function to get the user's current position as a Promise
export function getCurrentPosition() {
    return new Promise((resolve, reject) => {
        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(
                function (position) {
                    resolve(position);
                },
                function (error) {
                    reject(error);
                }
            );
        } else {
            reject(new Error("Geolocation API is not available in this browser."));
        }
    });
}