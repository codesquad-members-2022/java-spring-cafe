package kr.codesquad.cafe.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User join(User user) {
        validateFields(user);
        validateNoDuplicateUserId(user);
        validateNoDuplicateName(user);
        validateNoDuplicateEmail(user);

        repository.save(user);

        return user;
    }

    private void validateFields(User user) {
        Assert.notNull(user, "유저는 null이어서는 안 됩니다.");
        Assert.hasLength(user.getUserId(), "유저 ID는 공백이어선 안 됩니다.");
        Assert.hasLength(user.getName(), "유저 이름은 공백이어선 안 됩니다.");
        Assert.hasLength(user.getEmail(), "유저 이메일은 공백이어선 안 됩니다.");
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

    public Optional<User> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

}
