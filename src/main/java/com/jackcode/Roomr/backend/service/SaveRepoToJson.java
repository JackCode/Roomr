package com.jackcode.Roomr.backend.service;

import com.jackcode.Roomr.backend.repository.RoomRepository;

public class SaveRepoToJson {
    private RoomRepository roomRepository;
    private String file;

    public SaveRepoToJson(RoomRepository roomRepository, String file) {
        this.roomRepository = roomRepository;
        this.file = file;
    }

    public void saveRepoToJson() {

    }
}
