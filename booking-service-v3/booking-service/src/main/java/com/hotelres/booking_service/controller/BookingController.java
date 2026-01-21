package com.hotelres.booking_service.controller;

import com.hotelres.booking_service.entity.Booking;
import com.hotelres.booking_service.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getAll() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        return bookingService.saveBooking(booking);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }

    @PutMapping("/{id}")
    public Booking update(
            @PathVariable Long id,
            @RequestBody Booking booking) {
        return bookingService.updateBooking(id, booking);
    }
}
