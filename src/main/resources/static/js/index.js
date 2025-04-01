// Add this after your existing code
async function loadGenres() {
    try {
        const response = await fetch(
            `${BASE_URL}/genre/movie/list?api_key=${API_KEY}&language=vi-VN`
        );
        const data = await response.json();
        const genreDropdown = document.getElementById('genreDropdown');
        
        data.genres.forEach(genre => {
            const link = document.createElement('a');
            link.href = `/?genre=${genre.id}`;
            link.textContent = genre.name;
            genreDropdown.appendChild(link);
        });
    } catch (error) {
        console.error('Error loading genres:', error);
    }
}

async function loadPopularActors() {
    try {
        const response = await fetch(
            `${BASE_URL}/person/popular?api_key=${API_KEY}&language=vi-VN`
        );
        const data = await response.json();
        const actorsDropdown = document.getElementById('actorsDropdown');
        
        data.results.slice(0, 10).forEach(actor => {
            const link = document.createElement('a');
            link.href = `/actor/${actor.id}`;
            link.textContent = actor.name;
            actorsDropdown.appendChild(link);
        });
    } catch (error) {
        console.error('Error loading actors:', error);
    }
}

// Call these functions when page loads
loadGenres();
loadPopularActors();