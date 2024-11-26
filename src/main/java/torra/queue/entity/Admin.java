package torra.queue.entity;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_admin")
@AttributeOverride(name = "idUser", column = @Column(name = "id_admin"))
public class Admin extends User{

    @Column(name = "password", nullable = false)
    private String password;

    public Admin() {
    }

    public Admin(UUID id, String name, String lastName, String email, Instant creationTimestamp, String password) {
        super(id, name, lastName, email, creationTimestamp);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoginCorrect(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password,this.password);
    }
}
