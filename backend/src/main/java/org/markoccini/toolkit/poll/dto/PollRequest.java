package org.markoccini.toolkit.poll.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollRequest {
    @NotBlank(message = "Question cannot be empty")
    private String question;
    private List<ChoiceRequest> choiceRequests;
}
