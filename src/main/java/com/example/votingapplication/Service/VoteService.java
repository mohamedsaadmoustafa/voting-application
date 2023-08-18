package com.example.votingapplication.Service;

import com.example.votingapplication.Models.Vote;
import com.example.votingapplication.Repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {
    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public void submitVote(Vote vote) {
        // Add validation logic if needed
        voteRepository.save(vote);
    }

    public List<Vote> getVotingResults() {
        return voteRepository.findAll();
    }
}