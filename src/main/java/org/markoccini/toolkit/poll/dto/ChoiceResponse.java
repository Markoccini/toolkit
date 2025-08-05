package org.markoccini.toolkit.poll.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class ChoiceResponse {
    private String content;
    private Long votes;
    private ZonedDateTime createdAt;
    private PollResponse pollResponse;
}
