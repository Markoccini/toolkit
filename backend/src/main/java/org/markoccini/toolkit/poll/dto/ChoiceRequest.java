package org.markoccini.toolkit.poll.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChoiceRequest {
    @NotBlank(message = "Choice content cannot be empty.")
    private String content;
}
