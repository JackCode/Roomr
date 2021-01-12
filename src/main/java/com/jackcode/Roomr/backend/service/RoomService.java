package com.jackcode.Roomr.backend.service;

import com.jackcode.Roomr.backend.model.Bathroom;
import com.jackcode.Roomr.backend.model.Facing;
import com.jackcode.Roomr.backend.model.Room;
import com.jackcode.Roomr.backend.model.RoomType;
import com.jackcode.Roomr.backend.repository.BathroomRepository;
import com.jackcode.Roomr.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

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
        // Logic for saving (don't forget null check)
    }

    @PostConstruct
    public void populateTestData() {
        if (bathroomRepository.count() == 0) {
            bathroomRepository.save(
                    new Bathroom(Bathroom.BathroomType.SEPARATE_BATH_AND_SHOWER,
                            2,
                            2,
                            true));
        }

        if (roomRepository.count() == 0) {
            Bathroom bathroom = bathroomRepository.findAll().get(0);

            Set<Integer> connectingRooms = new TreeSet<>();
            connectingRooms.add(201);
            connectingRooms.add(205);

            Set<Facing> facing = new TreeSet<>();
            facing.add(Facing.VINE);
            facing.add(Facing.SIXTH);

            List<URL> photos = new ArrayList<>();
            try {
                photos.add(new URL("https://cvgcc.s3.us-east-2.amazonaws.com/roomImages/203/1.jpg"));
                photos.add(new URL("https://cvgcc.s3.us-east-2.amazonaws.com/roomImages/203/2.jpg"));
                photos.add(new URL("https://cvgcc.s3.us-east-2.amazonaws.com/roomImages/203/3.jpg"));
                photos.add(new URL("https://cvgcc.s3.us-east-2.amazonaws.com/roomImages/203/4.jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Room testRoom = new Room();
            testRoom.setBathroom(bathroom);
            testRoom.setConnectingRooms(connectingRooms);
            testRoom.setHasBalcony(true);
            testRoom.setHasBuiltInDrawers(false);
            testRoom.setHasFireplace(true);
            testRoom.setHasSkylight(false);
            testRoom.setRoomNumber(203);
            testRoom.setRoomType(RoomType.Q2T);
            testRoom.setHasSofa(false);
            testRoom.setSquareFootage(250);
            testRoom.setFacing(facing);
            testRoom.setPhotos(photos);

            List<Room> rooms = new ArrayList<>();
            rooms.add(testRoom);
            roomRepository.saveAll(rooms);
        }
    }
}
