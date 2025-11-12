package org.markoccini.toolkit.poll.service;

import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.markoccini.toolkit.poll.dto.ChoiceRequest;
import org.markoccini.toolkit.poll.dto.PollRequest;
import org.markoccini.toolkit.poll.dto.PollResponse;
import org.markoccini.toolkit.common.exceptions.BadRequestException;
import org.markoccini.toolkit.common.exceptions.DatabaseException;
import org.markoccini.toolkit.common.exceptions.NotFoundException;
import org.markoccini.toolkit.common.exceptions.ServerErrorException;
import org.markoccini.toolkit.poll.model.Choice;
import org.markoccini.toolkit.poll.model.Poll;
import org.markoccini.toolkit.poll.repository.ChoiceRepository;
import org.markoccini.toolkit.poll.repository.PollRepository;
import org.markoccini.toolkit.poll.mapper.PollMapper;
import org.markoccini.toolkit.poll.mapper.ChoiceMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PollService {

    private final PollRepository pollRepository;
    private final ChoiceRepository choiceRepository;

    public PollService(PollRepository pollRepository, ChoiceRepository choiceRepository) {
        this.pollRepository = pollRepository;
        this.choiceRepository = choiceRepository;
    }

    public List<PollResponse> getAllPolls() {
        List<Poll> polls = pollRepository.findAll();
        return polls.stream()
                .map(PollMapper::PollToPollResponseMapper)
                .collect(Collectors.toList());
    }

    public PollResponse getPollById(long pollId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new NotFoundException("Poll with id " + pollId + " does not exist."));
        return PollMapper.PollToPollResponseMapper(poll);
    }

    public PollResponse createPoll(PollRequest pollRequest) {
        Poll poll = PollMapper.PollRequestToPollMapper(pollRequest);

        if (pollRequest.getChoiceRequests() == null) {
            pollRequest.setChoiceRequests(new ArrayList<>());
        }

        for (ChoiceRequest choiceRequest : pollRequest.getChoiceRequests()) {
            Choice choice = ChoiceMapper.ChoiceRequestToChoiceMapper(choiceRequest);
            choiceRepository.save(choice);
            poll.addChoice(choice);
        }
        try {
            return PollMapper.PollToPollResponseMapper(pollRepository.save(poll));
        }
        catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }

    public PollResponse closePoll(Long pollId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new NotFoundException("Poll with id " + pollId + " does not exist."));
        if (poll.isClosed()) {
            throw new BadRequestException("Poll with id " + pollId + "is already closed.");
        }
        poll.closePoll();
        try {
            return PollMapper.PollToPollResponseMapper(pollRepository.save(poll));
        }
        catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }

    public PollResponse addChoice(long pollId, ChoiceRequest choiceRequest) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new NotFoundException("Poll with id " + pollId + " does not exist."));
        if (choiceRequest != null && choiceRequest.getContent() != null && !choiceRequest.getContent().isEmpty()) {
            poll.addChoice(ChoiceMapper.ChoiceRequestToChoiceMapper(choiceRequest));
            try {
                return PollMapper.PollToPollResponseMapper(pollRepository.save(poll));
            }
            catch (DataIntegrityViolationException ex) {
                throw new DatabaseException("Failed to save poll", ex);
            }
        } else {
            throw new BadRequestException("No Choice provided");
        }
    }

    public PollResponse removeChoice(long pollId, long choiceId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new NotFoundException("Poll with id " + pollId + " does not exist."));
        Choice choice = choiceRepository.findById(choiceId)
                .orElseThrow(() -> new NotFoundException("Choice with id " + choiceId + " does not exist."));
        if (!choice.getPoll().getId().equals(poll.getId())) {
            throw new BadRequestException("Choice with id " + choiceId + " does not belong to poll with id " + pollId);
        }
        poll.removeChoice(choice);
        try {
            return PollMapper.PollToPollResponseMapper(pollRepository.save(poll));
        }
        catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }

    public PollResponse editPollQuestion(Long pollId, String question) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new NotFoundException("Poll with id " + pollId + " does not exist."));
        if (question == null) {
            throw new BadRequestException("No new Question provided");
        }
        poll.setQuestion(question);
        try {
            return PollMapper.PollToPollResponseMapper(pollRepository.save(poll));
        }
        catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }

    public long deletePoll(long pollId) {
        pollRepository.findById(pollId)
                .orElseThrow(() -> new NotFoundException("Poll with id " + pollId + " does not exist."));

        try {
            pollRepository.deleteById(pollId);
            return pollId;
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to delete poll", ex);
        }
    }

    public PollResponse changeChoice(long pollId, long choiceId, String new_content) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new NotFoundException("Poll with id " + pollId + " does not exist."));
        Choice choice = choiceRepository.findById(choiceId)
                .orElseThrow(() -> new NotFoundException("Choice with id " + choiceId + " does not exist."));
        choice.setContent(new_content);
        try {
            choiceRepository.save(choice);
            return PollMapper.PollToPollResponseMapper(poll);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }

    public PollResponse voteForChoice(long pollId, long choiceId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new NotFoundException("Poll with id " + pollId + " does not exist."));
        Choice choice = choiceRepository.findById(choiceId)
                .orElseThrow(() -> new NotFoundException("Choice with id " + choiceId + " does not exist."));
        choice.incrementVotes();
        try {
            choiceRepository.save(choice);
            return PollMapper.PollToPollResponseMapper(poll);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }

    public PollResponse removeVoteFromChoice(long pollId, long choiceId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new NotFoundException("Poll with id " + pollId + " does not exist."));
        Choice choice = choiceRepository.findById(choiceId)
                .orElseThrow(() -> new NotFoundException("Choice with id " + choiceId + " does not exist."));
        choice.decrementVotes();
        try {
            choiceRepository.save(choice);
            return PollMapper.PollToPollResponseMapper(poll);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save poll", ex);
        }
    }
}
