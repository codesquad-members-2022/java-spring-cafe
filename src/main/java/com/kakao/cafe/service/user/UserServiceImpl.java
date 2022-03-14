package com.kakao.cafe.service.user;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserDto;
import com.kakao.cafe.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUser(UserDto userDto) {
        String userId = userDto.getUserId();
        String password = userDto.getPassword();
        String email = userDto.getEmail();
        String name = userDto.getName();

        User user = new User(userId, password, email, name);

        isDuplicatedUser(user);
        repository.save(user);
    }

    @Override
    public Optional<User> findSingleUser(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public void isDuplicatedUser(User user) {
        if (repository.findByUserId(user.getUserId()).isPresent()) {
            throw new IllegalStateException("사용자 ID가 중복됩니다.");
        }
    }
}
