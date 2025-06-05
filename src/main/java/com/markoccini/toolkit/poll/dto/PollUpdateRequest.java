package com.markoccini.toolkit.poll.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record PollUpdateRequest(
        Optional<String> question,
        Optional<List<String>> choices,
        Optional<LocalDateTime> expiresAt,
        Optional<Boolean> toggle
) {}
