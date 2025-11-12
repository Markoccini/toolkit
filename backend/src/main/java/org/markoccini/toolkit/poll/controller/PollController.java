package org.markoccini.toolkit.poll.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.markoccini.toolkit.poll.dto.ChoiceRequest;
import org.markoccini.toolkit.poll.dto.PollRequest;
import org.markoccini.toolkit.poll.dto.PollResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.markoccini.toolkit.poll.service.PollService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/polls")
public class PollController {

    final PollService pollService;

    public PollController(
            PollService pollService
    ) {
        this.pollService = pollService;
    }

    @Operation(tags = {"GET"})
    @GetMapping("")
    public ResponseEntity<List<PollResponse>> getPolls() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.getAllPolls());
    }

    @Operation(tags = {"GET"})
    @GetMapping("/{pollId}")
    public ResponseEntity<PollResponse> getPoll(
            @PathVariable("pollId") Long pollId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.getPollById(pollId));
    }

    @Operation(tags = {"POLL"})
    @PostMapping
    public ResponseEntity<PollResponse> createPoll(
            @RequestBody PollRequest pollRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                        .body(pollService.createPoll(pollRequest));
    }

    @Operation(tags = {"POLL"})
    @PatchMapping("/{pollId}")
    public ResponseEntity<PollResponse> updatePoll(
            @PathVariable("pollId") Long pollId,
            @RequestBody PollRequest pollRequest
    )  {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.editPollQuestion(pollId, pollRequest.getQuestion()));
    }

    @Operation(tags = {"POLL"})
    @PatchMapping("/{pollId}/close")
    public ResponseEntity<PollResponse> closePoll(
            @PathVariable Long pollId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.closePoll(pollId));
    }

    @Operation(tags = {"POLL"})
    @DeleteMapping("/{pollId}")
    public ResponseEntity<Long> deletePoll(
            @PathVariable Long pollId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.deletePoll(pollId));
    }

    @Operation(tags = {"CHOICE"})
    @PostMapping("/{pollId}/choices")
    public ResponseEntity<PollResponse> addChoiceToPoll(
            @PathVariable("pollId") Long pollId,
            @RequestBody ChoiceRequest choiceRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.addChoice(pollId, choiceRequest));
    }

    @Operation(tags = {"CHOICE"})
    @PatchMapping("/{pollId}/choices/{choiceId}")
    public ResponseEntity<PollResponse> editChoice(
            @PathVariable long pollId,
            @PathVariable long choiceId,
            String new_content
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.changeChoice(pollId, choiceId, new_content));
    }

    @Operation(tags = {"CHOICE"})
    @DeleteMapping("/{pollId}/choices/{choiceId}")
    public ResponseEntity<PollResponse> removeChoiceFromPoll(
            @PathVariable("pollId") Long pollId,
            @PathVariable long choiceId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.removeChoice(pollId, choiceId));
    }

    @Operation(tags = {"Vote"})
    @PatchMapping("/{pollId}/choices/{choiceId}/vote")
    public ResponseEntity<PollResponse> addVote(
            @PathVariable("pollId") Long pollId,
            @PathVariable long choiceId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.voteForChoice(pollId, choiceId));
    }

    @Operation(tags = {"Vote"})
    @PatchMapping("/{pollId}/choices/{choiceId}/unvote")
    public ResponseEntity<PollResponse> removeVote(
            @PathVariable("pollId") Long pollId,
            @PathVariable long choiceId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pollService.removeVoteFromChoice(pollId, choiceId));
    }
}
