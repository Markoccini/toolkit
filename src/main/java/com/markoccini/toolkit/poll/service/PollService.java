package com.markoccini.toolkit.poll.service;

public class PollService {
        try {
            if (poll == null) {
                throw new PollNotFoundException("Poll not found.");
            }
            if (poll.isClosed()) {
                throw new PollClosedException("Poll is already closed!");
            }
        catch (Exception e) {
            System.out.println(e.getMessage()); // TODO: Replace by proper handling
        }
    }
}
