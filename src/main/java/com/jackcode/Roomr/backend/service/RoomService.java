package com.jackcode.Roomr.backend.service;

import com.jackcode.Roomr.backend.model.Room;
import com.jackcode.Roomr.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<Room> findAll(String roomNumber) {
        if (roomNumber == null || roomNumber.equals("null") || roomNumber.isEmpty()) {
            return this.roomRepository.findAll();
        } else {
            return roomRepository.findAll(roomNumber);
        }
    }

    public Room findOneByRoomNumber(String roomNumber) {
        if (roomNumber == null || roomNumber.isEmpty()) {
            return new Room();
        } else {
            return roomRepository.findOneByRoomNumber(roomNumber);
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

    public List<String> getRoomNumberList() {
        List<String> roomNumbers = new ArrayList<>();
        List<Room> rooms = roomRepository.findAll();

        rooms.forEach(r -> roomNumbers.add(r.getRoomNumber()));
        roomNumbers.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1) - Integer.valueOf(o2);
            }
        });
        return roomNumbers;
    }
}
