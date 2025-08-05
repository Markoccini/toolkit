package org.markoccini.toolkit.poll.dto;

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
    private String question;
    private List<ChoiceRequest> choiceRequests;
    private boolean isClosed;
}
