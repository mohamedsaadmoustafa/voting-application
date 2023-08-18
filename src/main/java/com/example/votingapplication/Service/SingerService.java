package com.example.votingapplication.Service;

import com.example.votingapplication.Models.Singer;
import com.example.votingapplication.Repository.SingerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingerService {
    private final SingerRepository singerRepository;

    @Autowired
    public SingerService(SingerRepository singerRepository) {
        this.singerRepository = singerRepository;
    }

    public void addSinger(Singer singer) {
        singerRepository.save(singer);
    }

}