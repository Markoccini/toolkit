package org.markoccini.toolkit.poll.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChoiceRequest {
    private String content;
    private Long votes;
    private Long pollId;
}
