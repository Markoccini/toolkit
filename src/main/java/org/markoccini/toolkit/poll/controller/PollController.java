package org.markoccini.toolkit.poll.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.markoccini.toolkit.poll.dto.ChoiceRequest;
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

    @Operation(tags = {"POLL"})
    @PostMapping
    public PollResponse createPoll(@RequestBody PollRequest pollRequest) {
        return pollService.createPoll(pollRequest);
    }

    @Operation(tags = {"POLL"})
    @PatchMapping("/{pollId}")
    public PollResponse updatePoll(
            @PathVariable("pollId") Long pollId,
            @RequestBody String new_question
    ) throws Exception {
        return pollService.editPollQuestion(pollId, new_question);
    }

    @Operation(tags = {"POLL"})
    @PatchMapping("/{pollId}/close")
    public PollResponse closePoll(@PathVariable Long pollId) throws Exception {
        return pollService.closePoll(pollId);
    }

    @Operation(tags = {"POLL"})
    @DeleteMapping("/{pollId}")
    public long deletePoll(@PathVariable Long pollId) throws Exception {
        return pollService.deletePoll(pollId);
    }

    @Operation(tags = {"CHOICE"})
    @PostMapping("/{pollId}/choices")
    public PollResponse addChoiceToPoll(
            @PathVariable("pollId") Long pollId,
            @RequestBody ChoiceRequest choiceRequest
    ) throws Exception {
        return pollService.addChoice(pollId, choiceRequest);
    }

    @Operation(tags = {"CHOICE"})
    @PatchMapping("/{pollId}/choices/{choiceId}")
    public PollResponse editChoice(
            @PathVariable long pollId,
            @PathVariable long choiceId,
            String new_content
    ) throws Exception {
        return pollService.changeChoice(pollId, choiceId, new_content);
    }

    @Operation(tags = {"CHOICE"})
    @DeleteMapping("/{pollId}/choices/{choiceId}")
    public PollResponse removeChoiceFromPoll(
            @PathVariable("pollId") Long pollId,
            @PathVariable long choiceId
    ) throws Exception {
        return pollService.removeChoice(pollId, choiceId);
    }
}
