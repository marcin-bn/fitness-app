package pl.dundersztyc.fitnessapp.user.adapter.out.persistence;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import pl.dundersztyc.fitnessapp.AbstractTestcontainers;
import pl.dundersztyc.fitnessapp.user.domain.User;
import pl.dundersztyc.fitnessapp.user.mapper.UserMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.UserTestData.defaultUser;

@DataJpaTest
@Import({UserPersistenceAdapter.class, UserMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserPersistenceAdapterTest extends AbstractTestcontainers {

    @Autowired
    private UserPersistenceAdapter persistenceAdapter;

    @Autowired
    private UserRepository userRepository;


    @Test
    @Sql(scripts = "/LoadAccount.sql")
    void shouldLoadUsers() {
        User user = persistenceAdapter.findById(new User.UserId(1L));

        assertThat(user.getFirstName()).isEqualTo("Jan");
        assertThat(user.getLastName()).isEqualTo("Kowalski");
    }

    @Test
    void shouldUpdateUser() {
        User user = defaultUser()
                .firstName("Tomasz")
                .build();

        persistenceAdapter.update(user);

        assertThat(userRepository.count()).isEqualTo(1);

        UserJpaEntity savedEntity = userRepository.findById(user.getId().value()).orElseThrow(EntityNotFoundException::new);
        assertThat(savedEntity.getFirstName()).isEqualTo("Tomasz");

    }


}