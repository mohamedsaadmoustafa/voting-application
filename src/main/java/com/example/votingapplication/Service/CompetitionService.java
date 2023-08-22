package com.example.votingapplication.Service;

import com.example.votingapplication.Models.Competition;
import com.example.votingapplication.Models.Singer;
import com.example.votingapplication.Models.Vote;
import com.example.votingapplication.Repository.CompetitionRepository;
import com.example.votingapplication.Repository.SingerRepository;
import com.example.votingapplication.Repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;


@Service
public class CompetitionService {
    private final CompetitionRepository<Competition> competitionRepository;
    private final SingerRepository singerRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public CompetitionService(
            CompetitionRepository<Competition> competitionRepository,
            SingerRepository singerRepository,
            VoteRepository voteRepository
    ) {
        this.competitionRepository = competitionRepository;
        this.singerRepository = singerRepository;
        this.voteRepository = voteRepository;
    }

    public Competition createCompetition(Competition competition) {
        if (competition.getCompetitors() == null || competition.getCompetitors().isEmpty()) {
            throw new IllegalArgumentException("Competitors are required.");
        }
        if (competition.getStartDateTime() == null) {
            throw new IllegalArgumentException("Start date time is required.");
        }
        if (competition.getEndDateTime() == null) {
            throw new IllegalArgumentException("End date time is required.");
        }
        competitionRepository.save(competition);
        return competition;
    }

    public String getCompetitionResults(String competitionId, String userEmail) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        if (competition != null) {
            List<Vote> votes = voteRepository.findByCompetitionIdAndUserEmail(competitionId, userEmail);
            if (votes.isEmpty()) {
                throw new IllegalArgumentException("This user did not voted in this competition.");
            }

            Competition.Competitor winnerCompetitor = competition.getCompetitors().stream()
                    .max(Comparator.comparingInt(Competition.Competitor::getVoteCount))
                    .orElse(null);
            if (winnerCompetitor != null) {
                Singer singer = singerRepository.findById(winnerCompetitor.getSingerId()).orElse(null);
                if (singer != null) {
                    return singer.getName();
                }
            }
        }
        return null;
    }
}