package com.example.votingapplication.Repository;

import com.example.votingapplication.Models.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoteRepository extends MongoRepository<Vote, String> {
    // Add custom query methods if needed
}