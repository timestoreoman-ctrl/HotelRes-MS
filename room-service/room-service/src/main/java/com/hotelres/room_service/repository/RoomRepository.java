package com.hotelres.room_service.repository;

import com.hotelres.room_service.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
