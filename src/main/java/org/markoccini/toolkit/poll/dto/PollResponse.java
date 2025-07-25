package org.markoccini.toolkit.poll.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class PollResponse {
    private Long id;
    private String question;
    private ZonedDateTime createdAt;
    private List<Long> choiceIds;
}
