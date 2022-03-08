package kr.codesquad.cafe.users;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUserId(String userId);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    List<User> findAll();

}
