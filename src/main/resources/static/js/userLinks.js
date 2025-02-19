window.onload = function () {
    const shortUrlElement = document.getElementById("shortenedUrl");
    const shortUrl = shortUrlElement.getAttribute("data-url");

    if (shortUrl) {
        localStorage.setItem("shortenedUrl", shortUrl);
    }

    // Retrieve stored URL and display it (if needed)
    const storedUrl = localStorage.getItem("shortenedUrl");
    if (storedUrl) {
        console.log("Stored Shortened URL:", storedUrl);
    }
};