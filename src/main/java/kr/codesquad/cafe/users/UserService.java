package kr.codesquad.cafe.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void join(User user) {
        Assert.notNull(user, "유저는 null이어서는 안 됩니다.");
        validateNoDuplicateUserId(user);
        validateNoDuplicateName(user);
        validateNoDuplicateEmail(user);

        repository.save(user);
    }

    private void validateNoDuplicateUserId(User user) {
        repository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 ID입니다.");
                });
    }

    private void validateNoDuplicateName(User user) {
        repository.findByName(user.getName())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 사용 중인 이름입니다.");
                });
    }

    private void validateNoDuplicateEmail(User user) {
        repository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 등록된 이메일입니다.");
                });
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findByUserId(String userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

}
