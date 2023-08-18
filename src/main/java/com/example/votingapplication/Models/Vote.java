package com.example.votingapplication.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "votes")
public class Vote {
    @Id
    private String id;
    private String userEmail;    // voter email
    private String singerId;  // Vote for singer id
    private String competitionId;  // competition id
    private LocalDateTime timestamp;   // timestamp of the vote
}