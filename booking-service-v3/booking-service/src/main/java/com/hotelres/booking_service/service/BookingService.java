package com.hotelres.booking_service.service;

import com.hotelres.booking_service.entity.Booking;
import com.hotelres.booking_service.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Transactional
    public Booking updateBooking(Long id, Booking newBooking) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        existing.setCustomerName(newBooking.getCustomerName());
        existing.setRoomId(newBooking.getRoomId());
        existing.setFromDate(newBooking.getFromDate()); // ✅ مضافة
        existing.setToDate(newBooking.getToDate());

        return existing;
    }
}
