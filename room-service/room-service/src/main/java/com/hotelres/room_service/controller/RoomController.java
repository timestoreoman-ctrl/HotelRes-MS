package com.hotelres.room_service.controller;

import com.hotelres.room_service.entity.Room;
import com.hotelres.room_service.repository.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomRepository repo;

    public RoomController(RoomRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Room> getRooms() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return repo.save(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id,
                                           @RequestBody Room updatedRoom) {
        return repo.findById(id).map(room -> {

            if (updatedRoom.getRoomNumber() != null)
                room.setRoomNumber(updatedRoom.getRoomNumber());

            if (updatedRoom.getType() != null)
                room.setType(updatedRoom.getType());

            if (updatedRoom.getPrice() != 0)
                room.setPrice(updatedRoom.getPrice());

            // ✅ التعديل: لا تغيّري availability إلا إذا اجت بالقيمة في الـRequest
            if (updatedRoom.getAvailable() != null) {
                room.setAvailable(updatedRoom.getAvailable());
            }

            return ResponseEntity.ok(repo.save(room));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<Room> updateAvailability(
            @PathVariable Long id,
            @RequestParam boolean available) {

        System.out.println("🔥 UPDATE AVAILABILITY CALLED -> id=" + id + " available=" + available);

        return repo.findById(id).map(room -> {
            room.setAvailable(available);
            return ResponseEntity.ok(repo.save(room));
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
