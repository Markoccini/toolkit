package com.markoccini.toolkit.poll.repository;

import com.markoccini.toolkit.poll.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChoiceRepository extends JpaRepository<Poll, Long> {

    Optional<Poll> findByIdAndPollId(Long id, Long pollId);
}
