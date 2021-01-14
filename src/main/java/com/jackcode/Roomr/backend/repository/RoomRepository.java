package com.jackcode.Roomr.backend.repository;

import com.jackcode.Roomr.backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r from Room r " +
            "where r.roomNumber like concat('%', :roomNumberSearch, '%')")
    List<Room> search(String roomNumberSearch);
}
