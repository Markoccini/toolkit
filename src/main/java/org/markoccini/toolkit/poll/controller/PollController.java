package org.markoccini.toolkit.poll.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.markoccini.toolkit.poll.dto.PollRequest;
import org.markoccini.toolkit.poll.dto.PollResponse;
import org.springframework.web.bind.annotation.*;

import org.markoccini.toolkit.poll.service.PollService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/polls")
public class PollController {

    final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;

    }

    @Operation(tags = {"GET"})
    @GetMapping("")
    public List<PollResponse> getPolls() {
        return pollService.getAllPolls();
    }

    @Operation(tags = {"GET"})
    @GetMapping("/{pollId}")
    public PollResponse getPoll(@PathVariable("pollId") Long pollId) throws Exception {
        return pollService.getPollById(pollId);
    }

    @Operation(tags = {"POST"})
    @PostMapping
    public PollResponse createPoll(@RequestBody PollRequest pollRequest) {
        return pollService.createPoll(pollRequest);
    }
}
