package torra.queue.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import torra.queue.entity.Client;
import torra.queue.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(Integer phone);
}
