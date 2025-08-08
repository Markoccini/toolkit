package org.markoccini.toolkit.poll.mapper;

import org.markoccini.toolkit.poll.dto.ChoiceRequest;
import org.markoccini.toolkit.poll.dto.ChoiceResponse;
import org.markoccini.toolkit.poll.model.Choice;

import static org.markoccini.toolkit.poll.mapper.PollMapper.PollToPollResponseMapper;

public class ChoiceMapper {

    public static Choice ChoiceRequestToChoiceMapper(ChoiceRequest choiceRequest) {

        return Choice.builder()
                .content(choiceRequest.getContent())
                .build();
    }

    public static ChoiceResponse ChoiceToChoiceResponseMapper(Choice choice) {
        return ChoiceResponse.builder()
                .content(choice.getContent())
                .votes(choice.getVotes())
                .createdAt(choice.getCreatedAt())
                .build();
    }
}
