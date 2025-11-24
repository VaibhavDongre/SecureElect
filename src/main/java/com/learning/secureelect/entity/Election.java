package com.learning.secureelect.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private boolean active; //true means election open
    private boolean completed; //true means results calculated

    //cascade means if any operation happens on election then candidate will automatically be affected
    //means if election is deleted all the candidates in it will also be deleted
    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL)
    private List<Candidate> candidates;
}


