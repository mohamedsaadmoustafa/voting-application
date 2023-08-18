package com.example.votingapplication.Controller;

import com.example.votingapplication.Models.Competition;
import com.example.votingapplication.Service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class CompetitionController {
    private final CompetitionService competitionService;

    @Autowired
    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @PostMapping
    public void createCompetition(@RequestBody Competition competition) {
        competitionService.createCompetition(competition);
    }

    @GetMapping
    public List<Competition> getCompetitionResults() {
        return competitionService.getCompetitionResults();
    }
}