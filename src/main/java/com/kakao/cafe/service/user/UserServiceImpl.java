package com.kakao.cafe.service.user;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserForm;
import com.kakao.cafe.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUser(UserForm userForm) {
        String userId = userForm.getUserId();
        String password = userForm.getPassword();
        String email = userForm.getEmail();
        String name = userForm.getName();

        User user = new User(userId, password, email, name);

        repository.save(user);
    }

    @Override
    public Optional<User> findSingleUser(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> findSingleUser(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }
}
