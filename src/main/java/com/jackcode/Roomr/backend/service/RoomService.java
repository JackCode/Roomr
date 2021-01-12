package com.jackcode.Roomr.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackcode.Roomr.backend.model.Bathroom;
import com.jackcode.Roomr.backend.model.Room;
import com.jackcode.Roomr.backend.repository.BathroomRepository;
import com.jackcode.Roomr.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.US_ASCII;

@Service
public class RoomService {
    private static final Logger LOGGER = Logger.getLogger(RoomService.class.getName());
    private final RoomRepository roomRepository;
    private final BathroomRepository bathroomRepository;

    public RoomService(RoomRepository roomRepository, BathroomRepository bathroomRepository) {
        this.roomRepository = roomRepository;
        this.bathroomRepository = bathroomRepository;
    }

    public List<Room> findAll() {
        return this.roomRepository.findAll();
    }

    public long count() {
        return this.roomRepository.count();
    }

    public void delete(Room room) {
        this.roomRepository.delete(room);
    }

    public void save(Room room) {
        if (room == null) {
            LOGGER.log(Level.SEVERE, "Room is null. Are you sure you have connected your form to the application?");
            return;
        }
        roomRepository.save(room);
    }

    @PostConstruct
    public void loadRoomData() {
        if (roomRepository.count() == 0) {

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String roomArrayString = Files.readString(Paths.get("src/main/resources/static/roomList.json"), US_ASCII);
                List<Room> roomList = objectMapper.readValue(roomArrayString, new TypeReference<List<Room>>() {});
                roomList.forEach(room -> {
                    Bathroom bathroom = room.getBathroom();
                    bathroomRepository.save(
                            new Bathroom(bathroom.getBathroomType(),
                                    bathroom.getNumberOfSinks(),
                                    bathroom.getNumberOfShowerHeads(),
                                    bathroom.getHasTv()));
                });
                roomRepository.saveAll(roomList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
