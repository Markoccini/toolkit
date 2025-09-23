package org.markoccini.toolkit.poll.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ChoiceResponse {
    private long id;
    private String content;
    private Long votes;
    private Instant createdAt;
}
