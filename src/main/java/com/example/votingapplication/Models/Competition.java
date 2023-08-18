package com.example.votingapplication.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
    private List<Component> components;
    private int totalVotersNumber;


    @Getter
    @Setter
    @AllArgsConstructor
    @Document(collection = "Components")
    static class Component{
        @Id
        private String id;
        private String SingerId;
        private int voteNumber;
    }
}