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
        List<PollResponse> pollResponses = new ArrayList<>();
        for (Poll poll : polls) {
            pollResponses.add(PollToPollResponseMapper(poll));
        }
        return pollResponses;
    }

    public PollResponse getPollById(long id) throws Exception {
        Poll poll = pollRepository.findById(id).orElse(null);
        if (poll != null) {
            return PollToPollResponseMapper(poll);
        }
        else {
            throw new Exception("Poll with id " + id + " does not exist");
        }
    }

    @Transactional
    public PollResponse createPoll(PollRequest pollRequest) {
        Poll poll = PollRequestToPollMapper(pollRequest);

        for (ChoiceRequest choiceRequest : pollRequest.getChoiceRequests()) {
            Choice choice = ChoiceRequestToChoiceMapper(choiceRequest);
            choiceRepository.save(choice);
            poll.addChoice(choice);
        }
        return PollToPollResponseMapper(pollRepository.save(poll));
    }

    public Poll closePoll(Poll poll) {
        poll.closePoll();
        return pollRepository.save(poll);
    }

    public Poll removeChoice(Poll poll, Choice choice) {
        poll.removeChoice(choice);
        return  pollRepository.save(poll);
    }

    public Poll addChoice(Poll poll, Choice choice) {
        poll.addChoice(choice);
        return pollRepository.save(poll);
    }

    @Transactional
    public Poll editQuestion(Poll poll, String question) {
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

    public Poll PollRequestToPollMapper(PollRequest pollRequest) {
        assert pollRequest != null;

        return Poll.builder()
                .question(pollRequest.getQuestion())
                .choices(new ArrayList<Choice>())
                .isClosed(pollRequest.isClosed())
                .build();
    }

    public Choice ChoiceRequestToChoiceMapper(ChoiceRequest choiceRequest) {

        return Choice.builder()
                .content(choiceRequest.getContent())
                .votes(choiceRequest.getVotes())
                .build();
        }

    public ChoiceResponse ChoiceToChoiceResponseMapper(Choice choice) {
        return ChoiceResponse.builder()
                .content(choice.getContent())
                .votes(choice.getVotes())
                .createdAt(choice.getCreatedAt())
                .pollResponse(PollToPollResponseMapper(choice.getPoll()))
                .build();
    }

    public PollResponse PollToPollResponseMapper(Poll poll) {
        ArrayList<Long> choiceIds = new ArrayList<>();

        if (poll.getChoices() != null) {
            choiceIds = poll.getChoices().stream()
                    .map(Choice::getId)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return PollResponse.builder()
                .id(poll.getId())
                .question(poll.getQuestion())
                .choiceIds(choiceIds)
                .createdAt(poll.getCreatedAt())
                .build();
    }
}
