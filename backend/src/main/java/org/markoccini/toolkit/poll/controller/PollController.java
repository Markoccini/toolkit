package org.markoccini.toolkit.poll.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.markoccini.toolkit.poll.dto.ChoiceRequest;
import org.markoccini.toolkit.poll.dto.PollRequest;
import org.markoccini.toolkit.poll.dto.PollResponse;
import org.markoccini.toolkit.poll.service.PollService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/polls")
@Validated
public class PollController {

    final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @Operation(tags = { "GET" })
    @GetMapping("")
    public ResponseEntity<List<PollResponse>> getPolls() {
        return ResponseEntity.status(HttpStatus.OK).body(
            pollService.getAllPolls()
        );
    }

    @Operation(tags = { "GET" })
    @GetMapping("/{pollId}")
    public ResponseEntity<PollResponse> getPoll(
        @PathVariable("pollId") Long pollId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            pollService.getPollById(pollId)
        );
    }

    @Operation(tags = { "POLL" })
    @PostMapping
    public ResponseEntity<PollResponse> createPoll(
        @Valid @RequestBody PollRequest pollRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            pollService.createPoll(pollRequest)
        );
    }

    @Operation(tags = { "POLL" })
    @PatchMapping("/{pollId}")
    public ResponseEntity<PollResponse> updateFullPoll(
        @PathVariable("pollId") Long pollId,
        @Valid @RequestBody PollRequest changes
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            pollService.updatePollAndChoices(pollId, changes)
        );
    }

    @Operation(tags = { "POLL" })
    @PatchMapping("/{pollId}/close")
    public ResponseEntity<PollResponse> closePoll(@PathVariable Long pollId) {
        return ResponseEntity.status(HttpStatus.OK).body(
            pollService.closePoll(pollId)
        );
    }

    @Operation(tags = { "POLL" })
    @DeleteMapping("/{pollId}")
    public ResponseEntity<Long> deletePoll(@PathVariable Long pollId) {
        return ResponseEntity.status(HttpStatus.OK).body(
            pollService.deletePoll(pollId)
        );
    }

    @Operation(tags = { "CHOICE" })
    @PostMapping("/{pollId}/choices")
    public ResponseEntity<PollResponse> addChoiceToPoll(
        @PathVariable("pollId") Long pollId,
        @Valid @RequestBody ChoiceRequest choiceRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            pollService.addChoice(pollId, choiceRequest)
        );
    }

    @Operation(tags = { "CHOICE" })
    @DeleteMapping("/{pollId}/choices/{choiceId}")
    public ResponseEntity<PollResponse> removeChoiceFromPoll(
        @PathVariable("pollId") Long pollId,
        @PathVariable long choiceId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            pollService.removeChoice(pollId, choiceId)
        );
    }

    @Operation(tags = { "Vote" })
    @PatchMapping("/{pollId}/choices/{choiceId}/vote")
    public ResponseEntity<PollResponse> addVoteToChoice(
        @PathVariable("pollId") Long pollId,
        @PathVariable long choiceId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            pollService.voteForChoice(pollId, choiceId)
        );
    }

    @Operation(tags = { "Vote" })
    @PatchMapping("/{pollId}/choices/{choiceId}/unvote")
    public ResponseEntity<PollResponse> removeVoteFromChoice(
        @PathVariable("pollId") Long pollId,
        @PathVariable long choiceId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            pollService.removeVoteFromChoice(pollId, choiceId)
        );
    }
}
