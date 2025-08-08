package org.markoccini.toolkit.poll.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polls", schema= "polls")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String question;

    private final Instant createdAt = Instant.now();

    @Builder.Default
    private boolean isClosed = false;

    @OneToMany(
            mappedBy = "poll",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Choice> choices = new ArrayList<>();

    public Poll (String question, List<Choice> choices) {
        this.question = question;
        this.choices = choices;
    }

    public void addChoice(Choice choice) {
        choices.add(choice);
        choice.setPoll(this);
    }

    public void removeChoice(Choice choice) {
        choices.remove(choice);
    }

    public void closePoll() {
        assert  !this.isClosed;
    }
}
