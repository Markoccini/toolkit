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
        ArrayList<ChoiceResponse> choiceResponses = new ArrayList<>();
        List<Choice> choices = poll.getChoices();

        if (choices != null && !choices.isEmpty()) {
            choiceResponses = choices.stream()
                    .map(ChoiceMapper::ChoiceToChoiceResponseMapper)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        else {
            choiceResponses = new ArrayList<>();
        }

        return PollResponse.builder()
                .id(poll.getId())
                .question(poll.getQuestion())
                .choiceResponses(choiceResponses)
                .createdAt(poll.getCreatedAt())
                .isClosed(poll.isClosed())
                .build();
    }

}
