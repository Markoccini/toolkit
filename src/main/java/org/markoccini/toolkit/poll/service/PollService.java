package org.markoccini.toolkit.poll.service;

import org.markoccini.toolkit.poll.model.Choice;
import org.markoccini.toolkit.poll.model.Poll;
import org.markoccini.toolkit.poll.repository.ChoiceRepository;
import org.markoccini.toolkit.poll.repository.PollRepository;

import java.util.List;

public class PollService {

    private final PollRepository pollRepository;
    private final ChoiceRepository choiceRepository;

    public PollService(PollRepository pollRepository, ChoiceRepository choiceRepository) {
        this.pollRepository = pollRepository;
        this.choiceRepository = choiceRepository;
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Poll getPollById(long id) {
        return pollRepository.findById(id).orElse(null);
    }

    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
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
}
