const API_KEY = 'affc26a2de5fd7c69d4c6f26a2f88d05';
const BASE_URL = 'https://api.themoviedb.org/3';
const BASE_PRICE = 75000; // Giá vé cơ bản

async function loadMovieDetails() {
    const urlParams = new URLSearchParams(window.location.search);
    const movieId = urlParams.get('movieId');
    
    if (movieId) {
        document.getElementById('movieId').value = movieId;
        try {
            const response = await fetch(
                `${BASE_URL}/movie/${movieId}?api_key=${API_KEY}&language=vi-VN`
            );
            const movie = await response.json();
            
            // Cập nhật thông tin phim
            document.getElementById('movieTitle').textContent = movie.title;
            document.getElementById('movieOverview').textContent = movie.overview;
            document.getElementById('releaseDate').textContent = `Khởi chiếu: ${new Date(movie.release_date).toLocaleDateString('vi-VN')}`;
            document.getElementById('runtime').textContent = `${movie.runtime} phút`;
            document.getElementById('rating').textContent = `⭐ ${movie.vote_average.toFixed(1)}`;
            
            // Hiển thị poster thay vì trailer
            document.getElementById('moviePoster').src = `https://image.tmdb.org/t/p/w500${movie.poster_path}`;
            
            // Set giá vé cơ bản
            document.getElementById('price').value = BASE_PRICE;
            
        } catch (error) {
            console.error('Lỗi khi tải thông tin phim:', error);
        }
    }
}

// Remove loadTrailer function since we don't need it anymore

// Tính tổng tiền khi thay đổi số lượng vé
document.getElementById('seats').addEventListener('change', function(e) {
    const seats = parseInt(e.target.value) || 0;
    const total = seats * BASE_PRICE;
    document.getElementById('total').value = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(total);
});

// Khởi tạo trang
loadMovieDetails();

// Thêm xử lý form submit
document.getElementById('bookingForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const formData = {
        movieId: document.getElementById('movieId').value,
        movieTitle: document.getElementById('movieTitle').textContent,
        customerName: document.getElementById('customerName').value,
        customerEmail: document.getElementById('customerEmail').value,
        customerPhone: document.getElementById('customerPhone').value,
        showtime: document.getElementById('showtime').value,
        seats: document.getElementById('seats').value,
        total: document.getElementById('total').value.replace(/[^0-9]/g, '')
    };

    try {
        const response = await fetch('/process-booking', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            sessionStorage.setItem('bookingData', JSON.stringify(formData));
            window.location.href = '/booking-confirmation';
        } else {
            alert('Có lỗi xảy ra khi đặt vé. Vui lòng thử lại!');
        }
    } catch (error) {
        console.error('Lỗi:', error);
        alert('Có lỗi xảy ra khi đặt vé. Vui lòng thử lại!');
    }
});