package se2project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se2project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    Page<User> findByFirstNameContainingOrLastNameContainingOrEmailContaining (String firstName, String lastName, String email,Pageable pageable);
    Page<User> findAllByOrderByIdDesc(Pageable pageable);
    Page<User> findAllByOrderByFirstNameAsc(Pageable pageable);
    Page<User> findAllByOrderByFirstNameDesc(Pageable pageable);
    Page<User> findAllByOrderByLastNameAsc(Pageable pageable);
    Page<User> findAllByOrderByLastNameDesc(Pageable pageable);
    Page<User> findAllByOrderByEmailAsc(Pageable pageable);
    Page<User> findAllByOrderByEmailDesc(Pageable pageable);
}

