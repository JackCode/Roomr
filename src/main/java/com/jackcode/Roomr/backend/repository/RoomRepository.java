package com.jackcode.Roomr.backend.repository;

import com.jackcode.Roomr.backend.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {

}
