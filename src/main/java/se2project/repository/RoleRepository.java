package se2project.repository;

import se2project.model.Role;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Override
    Role getById(Integer integer);

}
