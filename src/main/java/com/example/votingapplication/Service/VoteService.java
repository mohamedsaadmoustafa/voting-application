package com.example.votingapplication.Service;

import com.example.votingapplication.Models.Competition;
import com.example.votingapplication.Models.Vote;
import com.example.votingapplication.Repository.CompetitionRepository;
import com.example.votingapplication.Repository.CompetitorRepository;
import com.example.votingapplication.Repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final CompetitionRepository competitionRepository;
    private final CompetitorRepository competitorRepository;

    @Autowired
    public VoteService(
            VoteRepository voteRepository,
            CompetitionRepository competitionRepository,
            CompetitorRepository competitorRepository) {
        this.voteRepository = voteRepository;
        this.competitionRepository = competitionRepository;
        this.competitorRepository = competitorRepository;
    }

    public void submitVote(Vote vote) {
        // Save the vote
        vote.setTimestamp(LocalDateTime.now());
        voteRepository.save(vote);

        // Update the competitor's vote count in the competition
        Optional<Competition> competitionOptional = competitionRepository.findById(vote.getCompetitionId());
        if (competitionOptional.isPresent()) {
            Competition competition = competitionOptional.get();

            // Find the competitor in the competition
            Optional<Competition.Competitor> competitorOptional = competition.getCompetitors().stream()
                    .filter(competitor -> competitor.getId().equals(vote.getSingerId()))
                    .findFirst();

            if (competitorOptional.isPresent()) {
                Competition.Competitor competitor = competitorOptional.get();

                // Increment the vote count for the competitor
                competitor.setVotersCount(competitor.getVotersCount() + 1);

                // Save the updated competitor
                competitorRepository.save(competitor);
            }
        }
    }
}