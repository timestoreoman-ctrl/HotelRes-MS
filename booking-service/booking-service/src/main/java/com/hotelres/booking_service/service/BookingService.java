package com.hotelres.booking_service.service;

import com.hotelres.booking_service.dto.RoomDTO;
import com.hotelres.booking_service.entity.Booking;
import com.hotelres.booking_service.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;

    public BookingService(BookingRepository bookingRepository,
                          RestTemplate restTemplate) {
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplate;
    }

    public Booking createBooking(Booking booking) {

        String roomServiceUrl =
                "http://room-service/rooms/" + booking.getRoomId();



        RoomDTO room;
        try {
            room = restTemplate.getForObject(roomServiceUrl, RoomDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Room not found");
        }

        if (room == null || room.getAvailable() == null || !room.getAvailable()) {
            throw new RuntimeException("Room is not available");
        }

        // 1️⃣ احفظ الحجز
        Booking savedBooking = bookingRepository.save(booking);

        // 2️⃣ غيّر حالة الغرفة إلى false
        String updateAvailabilityUrl =
                "http://ROOM-SERVICE/rooms/" + booking.getRoomId()
                        + "/availability?available=false";

        restTemplate.put(updateAvailabilityUrl, null);

        return savedBooking;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }


    // ✅ Update booking (بدون تغيير availability للغرفة)
    public Booking updateBooking(Long id, Booking updated) {
        return bookingRepository.findById(id).map(b -> {

            // تحديث جزئي آمن
            if (updated.getRoomId() != null) {
                b.setRoomId(updated.getRoomId());
            }
            if (updated.getGuestName() != null) {
                b.setGuestName(updated.getGuestName());
            }
            if (updated.getCheckIn() != null) {
                b.setCheckIn(updated.getCheckIn());
            }
            if (updated.getCheckOut() != null) {
                b.setCheckOut(updated.getCheckOut());
            }

            return bookingRepository.save(b);
        }).orElse(null);
    }

    // ✅ Delete booking + رجّع الغرفة available=true
    @Transactional
    public void deleteBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        bookingRepository.deleteById(id);

        String updateAvailabilityUrl =
                "http://ROOM-SERVICE/rooms/" + booking.getRoomId() + "/availability?available=true";

        restTemplate.put(updateAvailabilityUrl, null);
    }
}
