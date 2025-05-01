let storedUrls;
try {
    storedUrls = JSON.parse(localStorage.getItem("shortenedUrl")) || [];
} catch (e) {
    storedUrls = [];
}


window.onload = function () {
    const shortUrlElement = document.getElementById("shortenedUrl");
    const urlListElement = document.getElementById("urlList");

    if (shortUrlElement) {
        const shortUrl = shortUrlElement.getAttribute("data-url");

        if (shortUrl && !storedUrls.includes(shortUrl)) {
            storedUrls.push(shortUrl);
            localStorage.setItem("shortenedUrl", JSON.stringify(storedUrls));
        }
    }

    if (urlListElement) {
        urlListElement.innerHTML = "";
        const heading = document.createElement("h2");

        heading.innerText = "Your Previous URLs"
        urlListElement.appendChild(heading);
    }


    storedUrls.forEach(url => {
        if (urlListElement) {
            const listItem = document.createElement("div");


            const copyButton = document.createElement("button");
            copyButton.innerText = url;

            copyButton.classList.add("copy-url-btn","waves-effect", "waves-light", "btn", "col", "s12", "green"); // Materialize styling


            copyButton.addEventListener("click", function () {
                if (navigator.clipboard && navigator.clipboard.writeText) {
                    navigator.clipboard.writeText(url).then(() => {
                        M.toast({ html: "URL copied to clipboard!", classes: "green" });
                    }).catch(err => {
                        console.error("Failed to copy: ", err);
                    });
                } else {

                    const tempInput = document.createElement("input");
                    document.body.appendChild(tempInput);
                    tempInput.value = url;
                    tempInput.select();
                    document.execCommand("copy");
                    document.body.removeChild(tempInput);
                    M.toast({ html: "URL copied (fallback method)!", classes: "green" });
                }
            });

            listItem.appendChild(copyButton);
            urlListElement.appendChild(listItem);
        }
    });
};