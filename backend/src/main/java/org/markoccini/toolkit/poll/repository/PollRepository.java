package org.markoccini.toolkit.poll.repository;

import org.markoccini.toolkit.poll.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
}
