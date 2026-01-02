package org.markoccini.toolkit.poll.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.markoccini.toolkit.common.exceptions.BadRequestException;
import org.markoccini.toolkit.common.exceptions.DatabaseException;
import org.markoccini.toolkit.common.exceptions.NotFoundException;
import org.markoccini.toolkit.poll.DataCollectionClasses.PollWithOptionalChoice;
import org.markoccini.toolkit.poll.dto.ChoiceRequest;
import org.markoccini.toolkit.poll.dto.PollRequest;
import org.markoccini.toolkit.poll.dto.PollResponse;
import org.markoccini.toolkit.poll.mapper.ChoiceMapper;
import org.markoccini.toolkit.poll.mapper.PollMapper;
import org.markoccini.toolkit.poll.model.Choice;
import org.markoccini.toolkit.poll.model.Poll;
import org.markoccini.toolkit.poll.repository.ChoiceRepository;
import org.markoccini.toolkit.poll.repository.PollRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PollService {

    private final PollRepository pollRepository;
    private final ChoiceRepository choiceRepository;

    public PollService(
        PollRepository pollRepository,
        ChoiceRepository choiceRepository
    ) {
        this.pollRepository = pollRepository;
        this.choiceRepository = choiceRepository;
    }

    public List<PollResponse> getAllPolls() {
        List<Poll> polls = pollRepository.findAllWithChoices();
        return polls
            .stream()
            .map(PollMapper::PollToPollResponseMapper)
            .collect(Collectors.toList());
    }

    public PollResponse getPollById(long pollId) {
        PollWithOptionalChoice pollWithOptionalChoice =
            loadPollAndChoiceIfSpecified(pollId, null, false);
        Poll poll = pollWithOptionalChoice.getPoll();

        return PollMapper.PollToPollResponseMapper(poll);
    }

    public PollResponse createPoll(PollRequest pollRequest) {
        Poll poll = PollMapper.PollRequestToPollMapper(pollRequest);

        if (pollRequest.getChoiceRequests() == null) {
            pollRequest.setChoiceRequests(new ArrayList<>());
        }

        for (ChoiceRequest choiceRequest : pollRequest.getChoiceRequests()) {
            Choice choice = ChoiceMapper.ChoiceRequestToChoiceMapper(
                choiceRequest
            );
            choiceRepository.save(choice);
            poll.addChoice(choice);
        }
        try {
            return PollMapper.PollToPollResponseMapper(
                pollRepository.save(poll)
            );
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }

    public PollResponse closePoll(Long pollId) {
        PollWithOptionalChoice pollWithOptionalChoice =
            loadPollAndChoiceIfSpecified(pollId, null, true);
        Poll poll = pollWithOptionalChoice.getPoll();

        poll.closePoll();
        try {
            return PollMapper.PollToPollResponseMapper(
                pollRepository.save(poll)
            );
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }

    public long deletePoll(long pollId) {
        loadPollAndChoiceIfSpecified(pollId, null, false);

        try {
            pollRepository.deleteById(pollId);
            return pollId;
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to delete poll", ex);
        }
    }

    public PollResponse addChoice(long pollId, ChoiceRequest choiceRequest) {
        PollWithOptionalChoice pollWithOptionalChoice =
            loadPollAndChoiceIfSpecified(pollId, null, true);
        Poll poll = pollWithOptionalChoice.getPoll();

        poll.addChoice(ChoiceMapper.ChoiceRequestToChoiceMapper(choiceRequest));
        try {
            return PollMapper.PollToPollResponseMapper(
                pollRepository.save(poll)
            );
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }

    public PollResponse removeChoice(long pollId, long choiceId) {
        PollWithOptionalChoice pollWithOptionalChoice =
            loadPollAndChoiceIfSpecified(pollId, choiceId, true);
        Poll poll = pollWithOptionalChoice.getPoll();
        Choice choice = pollWithOptionalChoice.getOptionalChoice();

        if (checkIfChoiceBelongsToPoll(poll, choice)) {
            poll.removeChoice(choice);
        }
        try {
            return PollMapper.PollToPollResponseMapper(
                pollRepository.save(poll)
            );
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }

    public PollResponse voteForChoice(long pollId, long choiceId) {
        PollWithOptionalChoice pollWithOptionalChoice =
            loadPollAndChoiceIfSpecified(pollId, choiceId, true);
        Poll poll = pollWithOptionalChoice.getPoll();
        Choice choice = pollWithOptionalChoice.getOptionalChoice();
        if (checkIfChoiceBelongsToPoll(poll, choice)) {
            choice.incrementVotes();
        }
        try {
            choiceRepository.save(choice);
            return PollMapper.PollToPollResponseMapper(poll);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }

    public PollResponse removeVoteFromChoice(long pollId, long choiceId) {
        PollWithOptionalChoice pollWithOptionalChoice =
            loadPollAndChoiceIfSpecified(pollId, choiceId, true);
        Poll poll = pollWithOptionalChoice.getPoll();
        Choice choice = pollWithOptionalChoice.getOptionalChoice();
        if (checkIfChoiceBelongsToPoll(poll, choice)) {
            choice.decrementVotes();
        }
        try {
            choiceRepository.save(choice);
            return PollMapper.PollToPollResponseMapper(poll);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }

    public PollResponse updatePollAndChoices(long pollId, PollRequest changes) {
        PollWithOptionalChoice pollWithOptionalChoice =
            loadPollAndChoiceIfSpecified(pollId, null, true);
        Poll poll = pollWithOptionalChoice.getPoll();

        if (changes.getQuestion() != null && !changes.getQuestion().isBlank()) {
            poll.setQuestion(changes.getQuestion());
        }

        if (changes.getChoiceRequests() != null) {
            if (
                changes.getChoiceRequests() != null &&
                changes.getChoiceRequests().size() > 4
            ) {
                throw new BadRequestException(
                    "A poll cannot have more than 4 choices."
                );
            }
            List<Choice> existingChoices = new ArrayList<>(poll.getChoices());
            for (Choice choice : existingChoices) {
                poll.removeChoice(choice);
            }

            for (ChoiceRequest choiceRequest : changes.getChoiceRequests()) {
                Choice newChoice = ChoiceMapper.ChoiceRequestToChoiceMapper(
                    choiceRequest
                );
                poll.addChoice(newChoice);
            }
        }

        try {
            return PollMapper.PollToPollResponseMapper(
                pollRepository.save(poll)
            );
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException(
                "Failed to update poll and choices",
                ex
            );
        }
    }

    public PollWithOptionalChoice loadPollAndChoiceIfSpecified(
        long pollId,
        Long choiceId,
        boolean pollClosedCheckNeeded
    ) {
        Poll poll = pollRepository
            .findById(pollId)
            .orElseThrow(() ->
                new NotFoundException(
                    "Poll with id " + pollId + " does not exist."
                )
            );

        if (pollClosedCheckNeeded && poll.isClosed()) {
            throw new BadRequestException(
                "Poll with id " + pollId + " is already closed."
            );
        }
        if (choiceId != null) {
            Choice choice = choiceRepository
                .findById(choiceId)
                .orElseThrow(() ->
                    new NotFoundException(
                        "Choice with id " + choiceId + " does not exist."
                    )
                );
            return new PollWithOptionalChoice(poll, choice);
        } else {
            return new PollWithOptionalChoice(poll, null);
        }
    }

    public boolean checkIfChoiceBelongsToPoll(Poll poll, Choice choice) {
        if (!choice.getPoll().getId().equals(poll.getId())) {
            throw new BadRequestException(
                "Choice with id " +
                    choice.getId() +
                    " does not belong to poll with id " +
                    poll.getId() +
                    "."
            );
        } else {
            return true;
        }
    }
}
