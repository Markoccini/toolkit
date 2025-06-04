package com.markoccini.toolkit.poll.repository;

import com.markoccini.toolkit.poll.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    Optional<Choice> findByIdAndPollId(Long id, Long pollId);
}
