package com.example.votingapplication.Service;

import com.example.votingapplication.Helper.MailHelper;
import com.example.votingapplication.Models.Competition;
import com.example.votingapplication.Models.Singer;
import com.example.votingapplication.Repository.CompetitionRepository;
import com.example.votingapplication.Repository.SingerRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class CompetitionService {
    private final CompetitionRepository<Competition> competitionRepository;
    private final SingerRepository singerRepository;
    private final VoteService voteService;
    private final MongoTemplate mongoTemplate;
    private final MailHelper mailHelper;

    @Autowired
    public CompetitionService(
            CompetitionRepository<Competition> competitionRepository,
            SingerRepository singerRepository,
            VoteService voteService,
            MongoTemplate mongoTemplate,
            MailHelper mailHelper
    ) {
        this.competitionRepository = competitionRepository;
        this.singerRepository = singerRepository;
        this.voteService = voteService;
        this.mongoTemplate = mongoTemplate;
        this.mailHelper = mailHelper;
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

    public String getWinnerByCompetitionId(String competitionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("competitionId").is(competitionId));

        List<Competition> competitions = mongoTemplate.find(query, Competition.class);

        Optional<Competition> competition = competitions.stream().findFirst();
        if (competition.isPresent()) {
            Competition.Competitor winnerCompetitor = competition.get().getCompetitors().stream()
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


    public String getCompetitionResults(String competitionId, String userEmail) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        if (competition != null) {
            String winner = getWinnerByCompetitionId(competitionId);
            if (winner != null) {
                return winner;
            }
        }
        return null;
    }


    @Scheduled(fixedRate = 60000) // 60000 milliseconds = 1 minute
    public void checkCompetitionEndTime() throws MessagingException {
        LocalDateTime now = LocalDateTime.now();
        List<Competition> competitions = mongoTemplate.findAll(Competition.class);

        for (Competition competition : competitions) {
            LocalDateTime endDateTime = competition.getEndDateTime();
            if (endDateTime.isBefore(now)) {
                if (!competition.getIsFinished()) {
                    competition.setIsFinished(true);
                    List<String> voterMails = voteService.getAllVoterEmailsByCompetitionId(competition.getId());
                    String message = getWinnerByCompetitionId(competition.getId()) + " won!";
                    String subject = "Voting End";
                    for (String voterMail : voterMails) {
                        mailHelper.sendEmail(voterMail, subject, message);
                    }
                }
            }
        }
    }
}