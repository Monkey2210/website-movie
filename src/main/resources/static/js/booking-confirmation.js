document.addEventListener('DOMContentLoaded', function() {
    // Lấy thông tin đặt vé từ sessionStorage
    const bookingData = JSON.parse(sessionStorage.getItem('bookingData'));
    
    if (bookingData) {
        document.getElementById('movieTitle').textContent = bookingData.movieTitle;
        document.getElementById('showtime').textContent = new Date(bookingData.showtime).toLocaleString('vi-VN');
        document.getElementById('seatCount').textContent = bookingData.seats + ' vé';
        document.getElementById('totalAmount').textContent = bookingData.total;

        // Tạo QR code (có thể sử dụng thư viện QR code)
        const qrData = `Movie: ${bookingData.movieTitle}\nShowtime: ${bookingData.showtime}\nSeats: ${bookingData.seats}`;
        document.getElementById('qrCode').src = `https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=${encodeURIComponent(qrData)}`;
        
        // Xóa dữ liệu đặt vé sau khi hiển thị
        sessionStorage.removeItem('bookingData');
    } else {
        window.location.href = '/'; // Chuyển về trang chủ nếu không có dữ liệu
    }
});