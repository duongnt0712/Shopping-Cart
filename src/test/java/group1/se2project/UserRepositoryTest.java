package group1.se2project;

import se2project.model.Role;
import se2project.model.User;
import se2project.repository.RoleRepository;
import se2project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateUser() {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(2).get());
        User user = new User();
        user.setFirstName("Vu Anh");
        user.setLastName("Nguyen");
        user.setEmail("vuanh@gmail.com");
        user.setPassword("123456");
        user.setRoles(roles);

        User save = userRepository.save(user);

        User test = entityManager.find(User.class, save.getId());

        assertThat(user.getEmail()).isEqualTo(test.getEmail());

    }
}