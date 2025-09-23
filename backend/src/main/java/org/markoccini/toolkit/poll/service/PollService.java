package org.markoccini.toolkit.poll.service;

import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.markoccini.toolkit.poll.dto.ChoiceRequest;
import org.markoccini.toolkit.poll.dto.PollRequest;
import org.markoccini.toolkit.poll.dto.PollResponse;
import org.markoccini.toolkit.poll.model.Choice;
import org.markoccini.toolkit.poll.model.Poll;
import org.markoccini.toolkit.poll.repository.ChoiceRepository;
import org.markoccini.toolkit.poll.repository.PollRepository;
import org.markoccini.toolkit.poll.mapper.PollMapper;
import org.markoccini.toolkit.poll.mapper.ChoiceMapper;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<PollResponse> pollResponses = new ArrayList<>();
        for (Poll poll : polls) {
            pollResponses.add(PollMapper.PollToPollResponseMapper(poll));
        }
        return pollResponses;
    }

    public PollResponse getPollById(long id) throws Exception {
        Poll poll = pollRepository.findById(id).orElse(null);
        if (poll != null) {
            return PollMapper.PollToPollResponseMapper(poll);
        } else {
            throw new Exception("Poll with id " + id + " does not exist");
        }
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
        return PollMapper.PollToPollResponseMapper(pollRepository.save(poll));
    }

    public PollResponse closePoll(Long pollId) throws Exception {
        Poll poll = pollRepository.findById(pollId).orElse(null);
        if (poll != null) {
            poll.closePoll();
            return PollMapper.PollToPollResponseMapper(pollRepository.save(poll));
        } else {
            throw new Exception("Could not find poll with id " + pollId);
        }
    }

    public PollResponse addChoice(long pollId, ChoiceRequest choiceRequest) throws Exception {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new Exception("Could not find poll with id " + pollId));
        if (choiceRequest != null && choiceRequest.getContent() != null && !choiceRequest.getContent().isEmpty()) {
            poll.addChoice(ChoiceMapper.ChoiceRequestToChoiceMapper(choiceRequest));
            return PollMapper.PollToPollResponseMapper(pollRepository.save(poll));
        } else {
            throw new BadRequestException("No Choice provided");
        }
    }

    public PollResponse removeChoice(long pollId, long choiceId) throws Exception {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new Exception("Could not find poll with id " + pollId));
        Choice choice = choiceRepository.findById(choiceId)
                .orElseThrow(() -> new Exception("Could not find choice with id " + choiceId));
        if (!choice.getPoll().getId().equals(poll.getId())) {
            throw new BadRequestException("Choice with id " + choiceId + " does not belong to poll with id " + pollId);
        }
        poll.removeChoice(choice);
        return PollMapper.PollToPollResponseMapper(pollRepository.save(poll));
    }

    public PollResponse editPollQuestion(Long pollId, String question) throws Exception {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new Exception("Could not find poll with id " + pollId));
        if (question == null) {
            throw new BadRequestException("No new Question provided");
        }
        poll.setQuestion(question);
        return PollMapper.PollToPollResponseMapper(pollRepository.save(poll));
    }

    public long deletePoll(long id) throws Exception {
        Poll poll = pollRepository.findById(id).orElse(null);
        if (poll != null) {
            pollRepository.deleteById(id);
            return id;
        } else {
            throw new Exception("Could not find poll with id " + id);
        }
    }

    public PollResponse changeChoice(long pollId, long choiceId, String new_content) throws Exception {
        Poll poll = pollRepository.findById(pollId).orElse(null);
        if (poll != null) {
            Choice choice = choiceRepository.findById(choiceId).orElse(null);
            if (choice != null) {
                choice.setContent(new_content);
                choiceRepository.save(choice);
                return PollMapper.PollToPollResponseMapper(poll);
            } else {
                throw new Exception("Could not find choice with id " + choiceId);
            }
        } else {
            throw new Exception("Cannot find Poll with id " + pollId);
        }
    }

    public PollResponse voteForChoice(long pollId, long choiceId) throws Exception {
        Poll poll = pollRepository.findById(pollId).orElse(null);
        if (poll != null) {
            Choice choice = choiceRepository.findById(choiceId).orElse(null);
            if (choice != null) {
                choice.incrementVotes();
                choiceRepository.save(choice);
                return PollMapper.PollToPollResponseMapper(poll);
            } else {
                throw new Exception("Could not find choice with id " + choiceId);
            }
        } else {
            throw new Exception("Cannot find Poll with id " + pollId);
        }
    }

    public PollResponse removeVoteFromChoice(long pollId, long choiceId) throws Exception {
        Poll poll = pollRepository.findById(pollId).orElse(null);
        if (poll != null) {
            Choice choice = choiceRepository.findById(choiceId).orElse(null);
            if (choice != null) {
                choice.decrementVotes();
                choiceRepository.save(choice);
                return PollMapper.PollToPollResponseMapper(poll);
            } else {
                throw new Exception("Could not find choice with id " + choiceId);
            }
        } else {
            throw new Exception("Cannot find Poll with id " + pollId);
        }
    }
}
