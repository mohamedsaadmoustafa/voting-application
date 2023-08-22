package com.example.votingapplication.Repository;

import com.example.votingapplication.Models.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VoteRepository extends MongoRepository<Vote, String> {

    List<Vote> findByCompetitionIdAndUserEmail(String competitionId, String userEmail);
}