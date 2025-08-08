package org.markoccini.toolkit.poll.mapper;

import org.markoccini.toolkit.poll.dto.PollRequest;
import org.markoccini.toolkit.poll.dto.PollResponse;
import org.markoccini.toolkit.poll.model.Choice;
import org.markoccini.toolkit.poll.model.Poll;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PollMapper {

    public static Poll PollRequestToPollMapper(PollRequest pollRequest) {
        assert pollRequest != null;

        return Poll.builder()
                .question(pollRequest.getQuestion())
                .choices(new ArrayList<Choice>())
                .build();
    }

    public static PollResponse PollToPollResponseMapper(Poll poll) {
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
