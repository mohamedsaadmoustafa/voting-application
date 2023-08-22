package com.example.votingapplication.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "competitions")
public class Competition {
    @Id
    private String id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<Competitor> competitors;
    private int totalVoteCount;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Document(collection = "competitors")
    public static class Competitor {
        @Id
        private String id;
        private String singerId;
        private int voteCount;
    }
}