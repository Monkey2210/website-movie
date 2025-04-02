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

const API_KEY = 'da9ccbd4756ce38e95874484fd4eec9b';
const BASE_URL = 'https://api.themoviedb.org/3';

async function showTrailer(movieId) {
    try {
        let response = await fetch(`${BASE_URL}/movie/${movieId}/videos?api_key=${API_KEY}&language=vi-VN`);
        let data = await response.json();
        let trailer = data.results.find(video => video.type === "Trailer");

        if (!trailer) {
            response = await fetch(`${BASE_URL}/movie/${movieId}/videos?api_key=${API_KEY}&language=en-US`);
            data = await response.json();
            trailer = data.results.find(video => video.type === "Trailer") || data.results[0];
        }

        if (trailer) {
            const modal = document.getElementById('trailerModal');
            const trailerFrame = document.getElementById('trailerFrame');
            trailerFrame.src = `https://www.youtube.com/embed/${trailer.key}`;
            modal.style.display = 'block';

            // Đóng modal khi click nút close
            document.querySelector('.close-modal').onclick = function() {
                modal.style.display = 'none';
                trailerFrame.src = '';
            }

            // Đóng modal khi click bên ngoài
            window.onclick = function(event) {
                if (event.target == modal) {
                    modal.style.display = 'none';
                    trailerFrame.src = '';
                }
            }
        } else {
            alert('Không tìm thấy trailer cho phim này!');
        }
    } catch (error) {
        console.error('Lỗi khi tải trailer:', error);
        alert('Có lỗi xảy ra khi tải trailer!');
    }
}

async function showMovieInfo(movieId) {
    try {
        // Lấy thông tin chi tiết phim
        const movieResponse = await fetch(
            `${BASE_URL}/movie/${movieId}?api_key=${API_KEY}&language=vi-VN`
        );
        const movieData = await movieResponse.json();

        // Lấy thông tin diễn viên
        const creditsResponse = await fetch(
            `${BASE_URL}/movie/${movieId}/credits?api_key=${API_KEY}`
        );
        const creditsData = await creditsResponse.json();

        // Cập nhật modal
        document.getElementById('modalMoviePoster').src = `https://image.tmdb.org/t/p/w500${movieData.poster_path}`;
        document.getElementById('modalMovieTitle').textContent = movieData.title;
        document.getElementById('modalMovieOverview').textContent = movieData.overview;
        document.getElementById('modalMovieRelease').textContent = new Date(movieData.release_date).toLocaleDateString('vi-VN');
        document.getElementById('modalMovieRating').textContent = `${movieData.vote_average}/10 ⭐`;

        // Hiển thị thể loại
        const genresContainer = document.getElementById('modalMovieGenres');
        genresContainer.innerHTML = movieData.genres
            .map(genre => `<span class="genre-tag">${genre.name}</span>`)
            .join('');

        // Hiển thị diễn viên
        const castContainer = document.getElementById('modalMovieCast');
        castContainer.innerHTML = creditsData.cast
            .slice(0, 6)
            .map(actor => `
                <div class="cast-item">
                    <img src="https://image.tmdb.org/t/p/w185${actor.profile_path}" 
                         onerror="this.src='/images/no-profile.png'"
                         alt="${actor.name}">
                    <p>${actor.name}</p>
                </div>
            `)
            .join('');

        // Hiển thị modal
        const modal = document.getElementById('movieInfoModal');
        modal.style.display = 'block';

        // Xử lý đóng modal
        const closeBtn = modal.querySelector('.close-modal');
        closeBtn.onclick = () => modal.style.display = 'none';
        window.onclick = (event) => {
            if (event.target == modal) modal.style.display = 'none';
        }
    } catch (error) {
        console.error('Lỗi khi tải thông tin phim:', error);
        alert('Có lỗi xảy ra khi tải thông tin phim!');
    }
}

// Thêm sự kiện tìm kiếm phim
document.querySelector('.search-bar input').addEventListener('keyup', function(e) {
    if (e.key === 'Enter') {
        const searchTerm = this.value.trim();
        if (searchTerm) {
            window.location.href = `/search?query=${encodeURIComponent(searchTerm)}`;
        }
    }
});

// Call these functions when page loads
loadGenres();
loadPopularActors();