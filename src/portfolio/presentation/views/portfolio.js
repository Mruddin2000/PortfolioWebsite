// Wait for the DOM to be fully loaded before executing the code
document.addEventListener("DOMContentLoaded", function() {
    // Get the portfolio data from the server
    fetch("/api/portfolio")
        .then(response => response.json())
        .then(data => {
            // Iterate over each portfolio item
            data.forEach(portfolio => {
                // Create a new image element
                const img = document.createElement("img");
                img.setAttribute("src", "data:image/png;base64," + portfolio.imageData);
                img.setAttribute("alt", "Portfolio Image");
                
                // Create a new div for the portfolio item
                const div = document.createElement("div");
                div.classList.add("col-12", "col-md-4", "mb-3");
                div.innerHTML = `
                    <div class="card rounded experience-card">
                        <div class="card-body p-4">
                            <h5 class="card-title">${portfolio.title}</h5>
                            <p class="card-text">${portfolio.details}</p>
                        </div>
                    </div>
                `;
                
                // Append the image to the card body
                div.querySelector(".card-body").appendChild(img);
                
                // Append the portfolio item to the portfolio container
                document.getElementById("portfolio-container").appendChild(div);
            });
        })
        .catch(error => console.error("Error fetching portfolio data:", error));
});
