package org.markoccini.toolkit.poll.service;

import org.markoccini.toolkit.poll.dto.PollResponse;
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

}
