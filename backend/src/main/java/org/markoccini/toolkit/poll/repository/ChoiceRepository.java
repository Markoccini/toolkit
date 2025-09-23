package org.markoccini.toolkit.poll.repository;

import org.markoccini.toolkit.poll.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
