package com.kakao.cafe.service;

import com.kakao.cafe.controller.UserForm;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(UserForm form) {
        validateDuplicateUser(form);
        User user = new User(form.getEmail(), form.getUserId(), form.getPassword());
        return userRepository.save(user);
    }

    private void validateDuplicateUser(UserForm form) {
        userRepository.findByEmail(form.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원 입니다.");
                });
    }

    public List<UserForm> findAllUserForm() {
        List<User> users = userRepository.findAll();
        List<UserForm> userFormList = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            userFormList.add(UserForm.from(users.get(i)));
        }
        return userFormList;
    }

    public UserForm findOneByEmail(String email) {
        return UserForm.from(userRepository.findByEmail(email).get());
    }

    public UserForm findOneByUserId(String userId) {
        return UserForm.from(userRepository.findByUserId(userId).get());
    }
}
