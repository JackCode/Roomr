package com.jackcode.Roomr.backend.service;

import com.jackcode.Roomr.backend.model.Bathroom;
import com.jackcode.Roomr.backend.repository.BathroomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BathroomService {
    private BathroomRepository bathroomRepository;

    public BathroomService(BathroomRepository bathroomRepository) {
        this.bathroomRepository = bathroomRepository;
    }

    public List<Bathroom> findAll() {
        return bathroomRepository.findAll();
    }
}
