package com.example.votingapplication.Controller;

import com.example.votingapplication.Models.Singer;
import com.example.votingapplication.Service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/singers")
public class SingerController {
    private final SingerService singerService;

    @Autowired
    public SingerController(SingerService singerService) {
        this.singerService = singerService;
    }

    @PostMapping("/add")
    public Singer addSinger(@RequestBody Singer singer) {
        return singerService.addSinger(singer);
    }
}