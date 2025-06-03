package com.markoccini.toolkit.poll.repository;

import com.markoccini.toolkit.poll.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByIdAndPollId(Long id, Long pollId);
    long countByOptionId(Long optionId);
}
