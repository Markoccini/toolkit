package org.markoccini.toolkit.poll.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity(name = "choices")
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String content;

    private Long votes;

    @Getter
    private final ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));

    @ManyToOne
    @JoinColumn(name = "poll_id")
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private Poll poll;

    public Choice() {
    }

    public Choice(String content, Poll poll) {
        this.content = content;
        this.poll = poll;
    }

    public void incrementVotes() {
        if (this.votes < 3) {
            this.votes++;
        }
    }

    public void decrementVotes() {
        if (this.votes > 0) {
            this.votes--;
        }
    }
}
