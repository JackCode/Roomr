package com.jackcode.Roomr.backend.repository;

import com.jackcode.Roomr.backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}