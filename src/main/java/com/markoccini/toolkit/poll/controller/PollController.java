package com.markoccini.toolkit.poll.controller;

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
    public ResponseEntity<String> createPoll() {
        return ResponseEntity.ok().body("Enter Values here");
    }


    @PostMapping("/create")
    public ResponseEntity<PollResponse> createNewPoll(@Validated @RequestBody PollRequest pollRequest) {
        PollResponse pollresponse = pollService.createPoll(pollRequest);
        return ResponseEntity.ok().body(pollresponse);
    }
}
