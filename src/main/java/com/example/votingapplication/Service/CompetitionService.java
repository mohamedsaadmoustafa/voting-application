package com.example.votingapplication.Service;

import com.example.votingapplication.Models.Competition;
import com.example.votingapplication.Repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompetitionService {
    private final CompetitionRepository<Competition> competitionRepository;

    @Autowired
    public CompetitionService(CompetitionRepository<Competition> competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    public void createCompetition(Competition competition) {
        competitionRepository.save(competition);
    }



    public List<Competition> getCompetitionResults(String competitionId) {
        Competition competition = competitionRepository.findById(competitionId);
        for(Competition competition : competition.compititors){

        }
    }
}