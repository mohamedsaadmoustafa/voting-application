package com.example.votingapplication.Repository;

import com.example.votingapplication.Models.Singer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SingerRepository extends MongoRepository<Singer, String> {
}