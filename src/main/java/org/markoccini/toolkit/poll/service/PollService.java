package org.markoccini.toolkit.poll.service;

import jakarta.transaction.Transactional;
import org.markoccini.toolkit.poll.dto.ChoiceRequest;
import org.markoccini.toolkit.poll.dto.ChoiceResponse;
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
        }
        else {
            throw new Exception("Poll with id " + id + " does not exist");
        }
    }

    @Transactional
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

    @Transactional
    public PollResponse closePoll(Long pollId) throws Exception {
        Poll poll =  pollRepository.findById(pollId).orElse(null);
        if (poll != null) {
            poll.closePoll();
            return PollMapper.PollToPollResponseMapper(pollRepository.save(poll));
        }
        else {
            throw new Exception("Could not find poll with id " + pollId);
        }
    }

    public Poll addChoiceToPoll(Poll poll, Choice choice) {
        poll.addChoice(choice);
        return pollRepository.save(poll);
    }

    public Poll removeChoiceFromPoll(Poll poll, Choice choice) {
        poll.removeChoice(choice);
        return  pollRepository.save(poll);
    }

    @Transactional
    public Poll editPollQuestion(Poll poll, String question) {
        poll.setQuestion(question);
        return pollRepository.save(poll);
    }

    public void deletePoll(long id) {
        pollRepository.deleteById(id);
    }

    public Choice changeChoice(Choice choice, String new_content) {
        choice.setContent(new_content);
        return choiceRepository.save(choice);
    }

    public Choice voteForChoice(Choice choice) {
        choice.incrementVotes();
        return choiceRepository.save(choice);
    }

    public Choice unvoteForChoice(Choice choice) {
        choice.decrementVotes();
        return choiceRepository.save(choice);
    }
}
