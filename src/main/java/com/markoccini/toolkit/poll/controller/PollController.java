package com.markoccini.toolkit.poll.controller;

import com.markoccini.toolkit.poll.dto.PollUpdateRequest;
import com.markoccini.toolkit.poll.service.PollService;
import com.markoccini.toolkit.poll.dto.PollRequest;
import com.markoccini.toolkit.poll.dto.PollResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/polls")
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping()
    public ResponseEntity<String> HelloWorld() {
        return ResponseEntity.ok().body("Hello World!");
    }


    @GetMapping("/create")
    public ResponseEntity<String> createPollGet() {
        return ResponseEntity.ok().body("Erstellen einer Abstimmung: ");
    }

    @PostMapping("/create")
    public ResponseEntity<PollResponse> createPollPost(@Validated @RequestBody PollRequest pollRequest) {
        PollResponse pollresponse = pollService.createPoll(pollRequest);
        return ResponseEntity.ok().body(pollresponse);
    }

    @GetMapping("/<id>/edit")
    public ResponseEntity<String> updatePollGet(@RequestParam("id") Long id) {
        return ResponseEntity.ok().body("Wählen Sie die zu verändernden Eigenschaften: ");
    }

    @PostMapping("<id>/edit")
    public ResponseEntity<PollResponse> updatePollPost(
            @Validated
            @PathVariable Long pollId,
            @RequestBody PollUpdateRequest pollUpdateRequest
    ) {
        PollResponse pollResponse = pollService.updatePoll(pollId, pollUpdateRequest);
        return ResponseEntity.ok().body(pollResponse);
    }
}
