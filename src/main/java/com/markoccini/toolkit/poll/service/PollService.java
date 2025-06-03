package com.markoccini.toolkit.poll.service;

import com.markoccini.toolkit.common.PollClosedException;
import com.markoccini.toolkit.common.PollNotFoundException;
import com.markoccini.toolkit.poll.dto.PollRequest;
import com.markoccini.toolkit.poll.dto.PollResponse;
import com.markoccini.toolkit.poll.model.Choice;
import com.markoccini.toolkit.poll.model.Poll;
import com.markoccini.toolkit.poll.repository.ChoiceRepository;
import com.markoccini.toolkit.poll.repository.PollRepository;
import com.markoccini.toolkit.poll.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PollService {

    private final PollRepository pollRepository;
    private final ChoiceRepository choiceRepository;
    private final VoteRepository voteRepository;


    public PollService(
            PollRepository pollRepository,
            ChoiceRepository choiceRepository,
            VoteRepository voteRepository
    ) {
        this.pollRepository = pollRepository;
        this.choiceRepository = choiceRepository;
        this.voteRepository = voteRepository;
    }

    @Transactional
    public void createPoll(PollRequest pollRequest) { // TODO: replace void by PollResponse
        Poll poll = new Poll();
        poll.setQuestion(pollRequest.question());
        poll.setCreatedAt(LocalDateTime.now());
        poll.setExpiresAt(pollRequest.expiresAt());
        poll.setClosed(pollRequest.expiresAt() != null && pollRequest.expiresAt().isBefore(LocalDateTime.now()));

        pollRequest.choices().forEach(choiceText -> {
            Choice choice = new Choice();
            choice.setAnswer(choiceText);
            poll.addChoice(choice);
        });
        Poll savedPoll = pollRepository.save(poll);
        // TODO: add return
    }

    @Transactional
    public void updatePoll(Long pollId, PollRequest pollRequest) {
        try {
            Poll poll = pollRepository.findById(pollId).orElse(null);
            if (poll == null) {
                throw new PollNotFoundException("Poll not found.");
            }
            if (poll.isClosed()) {
                throw new PollClosedException("Poll is already closed!");
            }
            poll.setQuestion(pollRequest.question());
            poll.setExpiresAt(pollRequest.expiresAt());
            // TODO: save to DB
        }
        catch (Exception e) {
            System.out.println(e.getMessage()); // TODO: Replace by proper handling
        }
    }
}
