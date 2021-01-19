package com.jackcode.Roomr.backend.repository;

import com.jackcode.Roomr.backend.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RoomRepository extends MongoRepository<Room, String> {

    @Query("{ 'roomNumber' : { $regex : ?0} }")
    List<Room> findAll(String roomNumber);

    Room findOneByRoomNumber(String roomNumber);
}
