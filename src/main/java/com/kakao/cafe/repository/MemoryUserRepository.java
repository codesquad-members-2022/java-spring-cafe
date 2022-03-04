package com.kakao.cafe.repository;

import com.kakao.cafe.dto.UserInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private List<UserInformation> userInformationStore = new ArrayList<>();

    @Override
    public UserInformation save(UserInformation userInformation) {
        userInformationStore.add(userInformation);

        return userInformation;
    }

    @Override
    public Optional<UserInformation> findByUserId(String userId) {
        return userInformationStore.stream()
                .filter(userInformation -> userInformation.getUserId()
                        .equals(userId)).findAny();
    }

    @Override
    public List<UserInformation> findAll() {
        return new ArrayList<>(userInformationStore);
    }

    @Override
    public void clear() {
        userInformationStore.clear();
    }
}
