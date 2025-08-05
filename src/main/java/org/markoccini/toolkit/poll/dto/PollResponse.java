package org.markoccini.toolkit.poll.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class PollResponse {
    @NotBlank(message = "ID required")
    private Long id;
    @NotBlank(message = "Question needs to be set")
    private String question;
    @NotBlank(message = "Creation data is required")
    private ZonedDateTime createdAt;
    private List<Long> choiceIds;
}
