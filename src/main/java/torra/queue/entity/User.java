package torra.queue.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idUser;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @CreationTimestamp
    private Instant creationTimestamp;

    public User() {
    }

    public User(UUID idClient, String name, String lastName, String email, Instant creationTimestamp) {
        this.idUser = idClient;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.creationTimestamp = creationTimestamp;
    }


    public UUID getId() {
        return idUser;
    }

    public void setId(UUID idClient) {
        this.idUser = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}
