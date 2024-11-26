package torra.queue.entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_client")
@AttributeOverride(name = "idUser", column = @Column(name = "id_client"))
public class Client extends User{
    @Column(name = "position")
    private Integer position;

    @Column(name = "phone", unique = true)
    private Integer phone;

    @Column(name = "is_priority", nullable = false)
    private Boolean isPriority;

    public Client() {
    }

    public Client(UUID id, String name, String lastName, Integer position, Integer phone, String email, Instant creationTimestamp, boolean isPriority) {
        super(id,name,lastName,email,creationTimestamp);
        this.position = position;
        this.phone = phone;
        this.isPriority = isPriority;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }


    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Boolean getPriority() {
        return isPriority;
    }

    public void setPriority(Boolean priority) {
        isPriority = priority;
    }
}
