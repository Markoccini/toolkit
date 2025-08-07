package org.markoccini.toolkit.poll.controller;

import org.markoccini.toolkit.poll.dto.PollRequest;
import org.markoccini.toolkit.poll.dto.PollResponse;
import org.markoccini.toolkit.poll.model.Choice;
import org.markoccini.toolkit.poll.model.Poll;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.markoccini.toolkit.poll.service.PollService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/polls")
public class PollController {

    final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("")
    public List<PollResponse> getPolls() {
        return pollService.getAllPolls();
    }

    @GetMapping("/{pollId}")
    public PollResponse getPoll(@PathVariable("pollId") Long pollId) throws Exception {
        return pollService.getPollById(pollId);
    }

    @PostMapping
    public PollResponse createPoll(@RequestBody PollRequest pollRequest) {
        return pollService.createPoll(pollRequest);
    }
}
