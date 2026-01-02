package org.markoccini.toolkit.poll.repository;

import java.util.List;
import org.markoccini.toolkit.poll.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    @Query("SELECT DISTINCT p FROM Poll p LEFT JOIN FETCH p.choices")
    List<Poll> findAllWithChoices();
}
