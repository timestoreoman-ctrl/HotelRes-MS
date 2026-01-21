package com.hotelres.room_service.service;

import com.hotelres.room_service.entity.Room;
import com.hotelres.room_service.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> getAll() {
        return repository.findAll();
    }

    public Room getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Room create(Room room) {
        return repository.save(room);
    }

    public Room update(Long id, Room room) {
        return repository.findById(id).map(r -> {

            if (room.getRoomNumber() != null) {
                r.setRoomNumber(room.getRoomNumber());
            }

            if (room.getType() != null) {
                r.setType(room.getType());
            }

            if (room.getPrice() != 0) {
                r.setPrice(room.getPrice());
            }

            // ✅ التعديل: لا تغيّري availability إلا إذا اجت بالقيمة في الـRequest (غير null)
            if (room.getAvailable() != null) {
                r.setAvailable(room.getAvailable());
            }

            return repository.save(r);
        }).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
