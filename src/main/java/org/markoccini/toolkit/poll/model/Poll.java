package org.markoccini.toolkit.poll.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polls")
@NoArgsConstructor
@AllArgsConstructor
public class Poll {

    @Id
    @Getter
    private Long id;

    @Setter
    @Getter
    private String question;

    @Getter
    private final ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));

    @Getter
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

    public void closePoll(boolean new_state) {
        assert  !this.isClosed;
        if (new_state) {
            this.isClosed = true;
        }
    }
}
