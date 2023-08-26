package com.example.votingapplication.Service;

import com.example.votingapplication.Models.Competition;
import com.example.votingapplication.Models.Vote;
import com.example.votingapplication.Repository.CompetitionRepository;
import com.example.votingapplication.Repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final CompetitionRepository competitionRepository;
    private MongoTemplate mongoTemplate;

    public VoteService(
            VoteRepository voteRepository,
            CompetitionRepository competitionRepository,
            MongoTemplate mongoTemplate
            ) {
        this.voteRepository = voteRepository;
        this.competitionRepository = competitionRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<String> getAllVoterEmailsByCompetitionId(String competitionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("competitionId").is(competitionId));

        List<Vote> votes = mongoTemplate.find(query, Vote.class);

        List<String> voterEmails = new ArrayList<>();
        for (Vote vote : votes) {
            voterEmails.add(vote.getUserEmail());
        }
        return voterEmails;
    }

    public Competition submitVote(Vote vote) {
        if (vote.getUserEmail() == null || vote.getUserEmail().isEmpty()) {
            throw new IllegalArgumentException("User email is required.");
        }
        if (vote.getSingerId() == null || vote.getSingerId().isEmpty()) {
            throw new IllegalArgumentException("Singer ID is required.");
        }
        if (vote.getCompetitionId() == null || vote.getCompetitionId().isEmpty()) {
            throw new IllegalArgumentException("Competition ID is required.");
        }

        vote.setTimestamp(LocalDateTime.now());

        // Users can vote once: check if user email is already voted using competition id
        List<Vote> votes = voteRepository.findByCompetitionIdAndUserEmail(vote.getCompetitionId(), vote.getUserEmail());
        if (!votes.isEmpty()) {
            throw new IllegalArgumentException("Vote for the same competition and user already exists.");
        }

        // Update the competitor's vote count in the competition
        Optional<Competition> competitionOptional = competitionRepository.findById(vote.getCompetitionId());
        if (competitionOptional.isPresent()) {
            Competition competition = competitionOptional.get();

            // check if competition still available
            //System.out.println(vote.getTimestamp().isBefore(competition.getStartDateTime()) + "   " + vote.getTimestamp().isAfter(competition.getEndDateTime()));
            if(vote.getTimestamp().isBefore(competition.getStartDateTime()) || vote.getTimestamp().isAfter(competition.getEndDateTime())) {
                throw new IllegalArgumentException("Competition not available.");
            }

            Optional<Competition.Competitor> competitorOptional = competition.getCompetitors().stream()
                    .filter(competitor -> competitor.getSingerId().equals(vote.getSingerId()))
                    .findFirst();

            if (competitorOptional.isPresent()) {
                Competition.Competitor competitor = competitorOptional.get();
                competitor.setVoteCount(competitor.getVoteCount() + 1);
                log.info("A new vote saved to competition " + competition.getId());
            } else {
                log.info("Singer not found in this competition");
            }
            // add totalVotes
            competitionRepository.save(competition);
            voteRepository.save(vote);
            return competition;
        }
        return null;
    }
}