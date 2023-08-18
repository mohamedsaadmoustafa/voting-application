package com.example.votingapplication.Controller;

import com.example.votingapplication.Models.Competition;
import com.example.votingapplication.Models.Vote;
import com.example.votingapplication.Service.CompetitionService;
import com.example.votingapplication.Service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class VoteController {
    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public void submitVote(@RequestBody Vote vote) {

        voteService.submitVote(vote);


    }
}