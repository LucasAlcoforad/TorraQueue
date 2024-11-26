package torra.queue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import torra.queue.entity.Admin;
import torra.queue.entity.Client;
import torra.queue.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByName(String name);
    Optional<Admin> findByEmail(String email);
}
