package pl.dundersztyc.fitnessapp.user.adapter.out.persistence;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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

    @Autowired
    private UserMapper userMapper;

    @BeforeAll
    static void beforeAll(@Autowired UserRepository userRepository) {
        userRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @Sql(scripts = "/LoadAccount.sql")
    void shouldLoadUsers() {
        User user = persistenceAdapter.findById(new User.UserId(1L));

        assertThat(user.getFirstName()).isEqualTo("Jan");
        assertThat(user.getLastName()).isEqualTo("Kowalski");
    }

    @Test
    void shouldFindUserByUsername() {
        User user = defaultUser()
                .firstName("firstname123")
                .username("username123")
                .build();

        userRepository.save(userMapper.mapToJpaEntity(user));

        User userByUsername = persistenceAdapter.findByUsername("username123");

        assertThat(userByUsername).isNotNull();
        assertThat(userByUsername.getUsername()).isEqualTo("username123");
        assertThat(userByUsername.getFirstName()).isEqualTo("firstname123");
    }

    @Test
    void shouldSaveUser() {
        User user = defaultUser()
                .firstName("Tomasz")
                .build();

        boolean result = persistenceAdapter.save(user);

        assertThat(result).isTrue();
        assertThat(userRepository.count()).isEqualTo(1);

        UserJpaEntity savedEntity = userRepository.findByUsername(user.getUsername()).orElseThrow(EntityNotFoundException::new);

        assertThat(savedEntity.getFirstName()).isEqualTo("Tomasz");

    }

    @Test
    void saveWithSameUserTwiceIsIncorrect() {
        User user = defaultUser().build();

        boolean firstSave = persistenceAdapter.save(user);
        boolean secondSave = persistenceAdapter.save(user);

        assertThat(firstSave).isTrue();
        assertThat(secondSave).isFalse();
    }


}