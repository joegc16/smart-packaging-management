package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @NotNull
    protected String password;
    @NotNull
    @Column(unique = true)
    protected String username;
    @Email
    @NotNull
    protected String email;
    @NotNull
    @ManyToOne
    protected UserRole role;

    public User(){}

    public User(String name, String password, String username, String email, UserRole role) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
