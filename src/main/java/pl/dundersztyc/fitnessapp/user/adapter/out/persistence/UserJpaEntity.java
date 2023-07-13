package pl.dundersztyc.fitnessapp.user.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dundersztyc.fitnessapp.user.domain.Role;

import java.util.Set;

@Entity
@Table(name = "_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    @ElementCollection
    @CollectionTable(name = "authority", joinColumns = @JoinColumn(name = "id"))
    private Set<String> authorities;

}
