package com.markoccini.toolkit.poll.service;

import com.markoccini.toolkit.common.PollClosedException;
import com.markoccini.toolkit.common.PollNotFoundException;
import com.markoccini.toolkit.poll.dto.ChoiceResponse;
import com.markoccini.toolkit.poll.dto.PollRequest;
import com.markoccini.toolkit.poll.dto.PollResponse;
import com.markoccini.toolkit.poll.dto.PollUpdateRequest;
import com.markoccini.toolkit.poll.model.Choice;
import com.markoccini.toolkit.poll.model.Poll;
import com.markoccini.toolkit.poll.repository.ChoiceRepository;
import com.markoccini.toolkit.poll.repository.PollRepository;
import com.markoccini.toolkit.poll.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public PollResponse createPoll(PollRequest pollRequest) {
        Poll poll = new Poll();
        poll.setQuestion(pollRequest.question());
        poll.setCreatedAt(LocalDateTime.now());
        poll.setExpiresAt(pollRequest.expiresAt());
        poll.setClosed(pollRequest.expiresAt() != null && pollRequest.expiresAt().isBefore(LocalDateTime.now()));

        pollRequest.choices().forEach(choiceText -> {
            Choice choice = new Choice();
            choice.setContent(choiceText);
            poll.addChoice(choice);
        });
        Poll savedPoll = pollRepository.save(poll);
        return PollToPollResponse(savedPoll);
    }


    @Transactional
    public PollResponse updatePoll(Long pollId, PollUpdateRequest pollUpdateRequest) throws PollClosedException, PollNotFoundException {
        Poll poll = getPollFromDB(pollId);
        if (pollUpdateRequest.toggle().isPresent() && pollUpdateRequest.toggle().get()) {
            poll.setClosed(true);
        }
        else {
            if (pollUpdateRequest.question().isPresent()) {
                poll.setQuestion(pollUpdateRequest.question().get());
            }
            if (pollUpdateRequest.expiresAt().isPresent()) {
                poll.setExpiresAt(pollUpdateRequest.expiresAt().get());
            }
        }
        Poll savedPoll = pollRepository.save(poll);
        return PollToPollResponse(savedPoll);
    }


    public Poll getPollFromDB(Long pollId) throws PollNotFoundException, PollClosedException {
        try {
            Poll poll = pollRepository.findById(pollId).orElse(null);
            if (poll == null) {
                throw new PollNotFoundException(String.format("Poll with id %d not found.", pollId));
            }
            if (poll.isClosed()) {
                throw new PollClosedException(String.format("Poll with id %d is already closed.", pollId));
            }
            return poll;
        }
        catch (PollNotFoundException | PollClosedException e) {
            System.out.println(e.getMessage()); // TODO: Replace by proper handling
            throw e;
        }
    }


//    public PollResponse PollExceptionWrapper() {
//
//    }

    public PollResponse PollToPollResponse(Poll poll) {
        Map<Long, Long> voteCounts = new HashMap<>();
        poll.getChoices().forEach(choice -> voteCounts.put(
                choice.getId(),
                voteRepository.countByChoiceId(choice.getId()))
        );

        List<ChoiceResponse> choices = new ArrayList<>();
        for (Choice choice : poll.getChoices()) {
            ChoiceResponse choiceResponse = new ChoiceResponse(choice.getId(), choice.getContent());
            choices.add(choiceResponse);
        }

        return new PollResponse(
                poll.getId(),
                poll.getQuestion(),
                choices,
                poll.getCreatedAt(),
                poll.getExpiresAt(),
                poll.isClosed(),
                voteCounts
        );
    }
}
