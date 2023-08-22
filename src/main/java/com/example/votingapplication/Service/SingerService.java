package com.example.votingapplication.Service;

import com.example.votingapplication.Models.Singer;
import com.example.votingapplication.Repository.SingerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SingerService {
    private final SingerRepository singerRepository;

    @Autowired
    public SingerService(SingerRepository singerRepository) {
        this.singerRepository = singerRepository;
    }

    public Singer addSinger(Singer singer) {
        singerRepository.save(singer);
        return singer;
    }

}