package com.markoccini.toolkit.poll.dto;

public record VoteRequest(
        Long choiceId,
        Long userId // From Authentication Context
) {}
