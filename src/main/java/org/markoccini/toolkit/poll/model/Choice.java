package org.markoccini.toolkit.poll.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "choices", schema = "polls")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String content;

    @Builder.Default
    private Long votes = 0L;

    private final ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id")
    @Setter
    private Poll poll;

    public Choice(String content, Poll poll) {
        this.content = content;
        this.poll = poll;
    }

    public void incrementVotes() {
        this.votes++;
    }

    public void decrementVotes() {
        if (this.votes > 0) {
            this.votes--;
        }
    }
}
