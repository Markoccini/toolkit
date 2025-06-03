package com.markoccini.toolkit.poll.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record PollResponse(Long id,
                           String question,
                           List<ChoiceResponse> choices,
                           LocalDateTime createdAt,
                           LocalDateTime expiresAt,
                           boolean closed,
                           Map<Long, Long> voteCounts
) {}
