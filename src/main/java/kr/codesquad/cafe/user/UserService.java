package kr.codesquad.cafe.user;

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
        validateNotNull(user);
        validateNoDuplicateUserId(user);
        validateNoDuplicateName(user);
        validateNoDuplicateEmail(user);

        repository.save(user);
    }

    public void update(User user, String oldPassword) {
        validateNotNull(user);
        validateUserAlreadyExists(user);
        validateOldPassword(user, oldPassword);
        validateNoDuplicateName(user);
        validateNoDuplicateEmail(user);

        repository.save(user);
    }

    private void validateOldPassword(User user, String oldPassword) {
        if (findByUserId(user.getUserId()).passwordIs(oldPassword)) {
            return;
        }

        throw new IllegalArgumentException("잘못된 패스워드입니다.");
    }

    private void validateNotNull(User user) {
        Assert.notNull(user, "유저는 null이어서는 안 됩니다.");
    }

    private void validateUserAlreadyExists(User user) {
        if (repository.findByUserId(user.getUserId()).isEmpty()) {
            throw new IllegalStateException("수정할 대상이 존재하지 않습니다.");
        }
    }

    private void validateNoDuplicateUserId(User user) {
        repository.findByUserId(user.getUserId())
                .ifPresent(existingUser -> {
                    throw new IllegalStateException("이미 존재하는 ID입니다.");
                });
    }

    private void validateNoDuplicateName(User user) {
        repository.findByName(user.getName()).stream()
                .filter(existingUser -> !(existingUser.userIdIs(user.getUserId())))
                .findAny()
                .ifPresent(existingUser -> {
                    throw new IllegalStateException("다른 사용자가 사용 중인 이름입니다.");
                });
    }

    private void validateNoDuplicateEmail(User user) {
        repository.findByEmail(user.getEmail()).stream()
                .filter(existingUser -> !(existingUser.userIdIs(user.getUserId())))
                .findAny()
                .ifPresent(existingUser -> {
                    throw new IllegalStateException("다른 사용자가 등록한 이메일입니다.");
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
