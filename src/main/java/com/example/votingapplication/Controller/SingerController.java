package com.example.votingapplication.Controller;

import com.example.votingapplication.Models.Competition;
import com.example.votingapplication.Models.Singer;
import com.example.votingapplication.Service.CompetitionService;
import com.example.votingapplication.Service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class SingerController {
    private final SingerService singerService;

    @Autowired
    public SingerController(SingerService singerService) {
        this.singerService = singerService;
    }

    @PostMapping
    public void addSinger(@RequestBody Singer singer) {
        singerService.addSinger(singer);
    }
}