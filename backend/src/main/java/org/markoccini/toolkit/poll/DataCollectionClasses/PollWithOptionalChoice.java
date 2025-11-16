package org.markoccini.toolkit.poll.DataCollectionClasses;

import lombok.Getter;
import lombok.Setter;
import org.markoccini.toolkit.poll.model.Choice;
import org.markoccini.toolkit.poll.model.Poll;

@Getter
@Setter
public class PollWithOptionalChoice {

    Poll poll;
    Choice optionalChoice;

    public PollWithOptionalChoice(Poll poll, Choice optionalChoice) {
        this.poll = poll;
        this.optionalChoice = optionalChoice;
    }
}
