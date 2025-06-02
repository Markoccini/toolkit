package com.markoccini.toolkit.poll.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "polls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Choice> choices = new HashSet<>();  // Fixes with Choice Object

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    private boolean closed = false;

    public void addChoice(Choice choice) {
        choices.add(choice);
        choice.setPoll(this);   // Comes with Getter and Setters in Choice Object via Lombok
    }
}
