package com.jackcode.Roomr.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackcode.Roomr.backend.model.Room;
import com.jackcode.Roomr.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.US_ASCII;

@Service
public class RoomService {
    private static final Logger LOGGER = Logger.getLogger(RoomService.class.getName());
    private final RoomRepository roomRepository;
    private final String jsonFile = "src/main/resources/static/roomList.json";

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
            return roomRepository.search(roomNumberSearch);
        }
    }

    public long count() {
        return this.roomRepository.count();
    }

    public void delete(Room room) {
        if (room != null) {
            this.roomRepository.delete(room);
            this.saveRepoToFile();
        }
    }

    public void save(Room room) {
        if (room == null) {
            LOGGER.log(Level.SEVERE, "Room is null. Are you sure you have connected your form to the application?");
            return;
        }
        if (room.getPhotos() != null && room.getPhotos().isEmpty()) {
            room.setPhotos(getTemplateImageList(room));
        }
        roomRepository.save(room);
        this.saveRepoToFile();
    }

    private List<String> getTemplateImageList(Room room) {
        int roomNum = room.getRoomNumber();
        int photoNumber = 0;
        List<String> imageList = new ArrayList<>();
        while (photoNumber <= 5) {
            try {
                imageList.add("img/"
                        + String.valueOf(roomNum) + "/" + String.valueOf(photoNumber) + ".jpg");
            } catch (Exception e) {
                e.printStackTrace();
            }
            photoNumber++;
        }
        return imageList;
    }

    private void saveRepoToFile() {
        // Logic to save to Json
        ObjectMapper objMapper = new ObjectMapper();
        StringBuffer jsonString = new StringBuffer();
        jsonString.append("[");
        int numTokens = (int) roomRepository.count();
        int writtenTokens = 0;

        for (Room room : roomRepository.findAll()) {
            try {
                writtenTokens++;
                jsonString.append(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(room));
                if (writtenTokens >= numTokens) {
                    break;
                }
                jsonString.append(",\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        jsonString.append("]");
        try {
            Files.writeString(Paths.get(jsonFile), jsonString.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        roomRepository.deleteAll();
        loadRoomData();
    }

    @PostConstruct
    public void loadRoomData() {
        if (roomRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String roomArrayString = Files.readString(Paths.get("src/main/resources/static/roomList.json"), US_ASCII);
                Room[] roomList = objectMapper.readValue(roomArrayString, Room[].class);
                roomRepository.saveAll(Arrays.asList(roomList));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
