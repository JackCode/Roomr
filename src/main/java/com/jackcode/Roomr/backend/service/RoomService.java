package com.jackcode.Roomr.backend.service;

import com.jackcode.Roomr.backend.model.Room;
import com.jackcode.Roomr.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RoomService {
    private static final Logger LOGGER = Logger.getLogger(RoomService.class.getName());
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll() {
        return this.roomRepository.findAll();
    }

    public List<Room> findAll(String roomNumberSearch) {
        if (roomNumberSearch == null || roomNumberSearch.isEmpty()) {
            return this.roomRepository.findAll();
        } else {
            return roomRepository.findAll();
        }
    }

    public long count() {
        return this.roomRepository.count();
    }

    public void delete(Room room) {
        if (room != null) {
            this.roomRepository.delete(room);
        }
    }

    public void save(Room room) {
        if (room == null) {
            LOGGER.log(Level.SEVERE, "Room is null. Are you sure you have connected your form to the application?");
            return;
        }
        roomRepository.save(room);
    }
}
