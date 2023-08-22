package com.example.votingapplication.Controller;

import com.example.votingapplication.Models.Competition;
import com.example.votingapplication.Service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/competitions")
public class CompetitionController {
    private final CompetitionService competitionService;

    @Autowired
    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @PostMapping("/add")
    public Competition createCompetition(@RequestBody Competition competition) {
        return competitionService.createCompetition(competition);
    }

    @GetMapping("/result/{id}")
    public String getCompetitionResults(@PathVariable String id, @RequestBody String userEmail) {
        return competitionService.getCompetitionResults(id, userEmail);
    }
}