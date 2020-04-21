package it.polito.ai.lab2.repositories;

import it.polito.ai.lab2.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,String> {
}
