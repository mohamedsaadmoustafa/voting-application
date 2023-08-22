package com.example.votingapplication.Repository;

import com.example.votingapplication.Models.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompetitionRepository<S extends Competition> extends MongoRepository<S, String> {
    Competition findByCompetitors_IdAndIdOrderByCompetitors_VoteCountDesc(String competitorId, String competitionId);
}