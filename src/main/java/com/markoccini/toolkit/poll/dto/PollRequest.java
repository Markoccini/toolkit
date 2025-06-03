package com.markoccini.toolkit.poll.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PollRequest(
        String question,
        List<String> choices,
        LocalDateTime expiresAt
) {}
